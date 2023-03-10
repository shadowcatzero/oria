package cat16.oria.item.tool

import cat16.oria.Oria
import cat16.oria.component.OriaComponents
import cat16.oria.item.OriaItem
import cat16.oria.network.OriaPackets
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.client.item.TooltipContext
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.enchantment.UnbreakingEnchantment
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.passive.AbstractHorseEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.registry.Registries
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*
import kotlin.math.ceil

class SpatialOrbItem(settings: Settings?) : Item(settings), OriaItem {

    constructor() : this(Settings().maxCount(1).maxDamage(100))

    override val oriaName = "spatial_orb"

    override fun useOnEntity(stack: ItemStack, player: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        val orb = player.getStackInHand(hand)
        return if (!hasEntity(orb) && player.isSneaking) {
            val entityHealth = entity.health
            val manaManager = player.getComponent(OriaComponents.MAGIC).manaManager
            if (manaManager.mana >= entityHealth || player.isCreative) {
                if (!player.isCreative) manaManager.mana -= entityHealth
                val tag = orb.orCreateNbt
                tag.put("entity", entity.writeNbt(NbtCompound()))
                tag.putInt("typeId", Registries.ENTITY_TYPE.getRawId(entity.type))
                entity.remove(Entity.RemovalReason.DISCARDED)
                player.playSound(FILL_SOUND, 1.0f, 1.0f)
            } else {
                player.sendMessage(Oria.text("magic", "not_enough_mana"), true)
            }
            ActionResult.SUCCESS
        } else ActionResult.FAIL
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        if (context.player == null) return ActionResult.FAIL
        val orb = context.stack
        return if (hasEntity(orb)) {
            val world = context.world
            if (!world.isClient) {
                var pos = context.blockPos
                if (!world.getBlockState(pos).getCollisionShape(world, pos).isEmpty) {
                    pos = pos.offset(context.side)
                }
                if (!spawnEntity(Vec3d.of(pos).add(Vec3d(0.5, 0.0, 0.5)), orb, world)) {
                    return ActionResult.FAIL
                }
            }
            orb.nbt!!.remove("entity")
            orb.nbt!!.remove("typeId")
            context.player!!.playSound(RELEASE_SOUND, 1.0f, 1.0f)
            ActionResult.SUCCESS
        } else {
            ActionResult.PASS
        }
    }

    override fun hasGlint(stack: ItemStack): Boolean {
        return hasEntity(stack)
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        if (hasEntity(stack)) {
            val entity = getEntity(stack, world)!!
            val type = entity.type.name.copy().formatted(Formatting.DARK_PURPLE)
            tooltip.add(tooltip("filled", type).formatted(Formatting.GRAY))
            if (entity.customName != null) {
                val customName = entity.customName!!.copy().formatted(Formatting.AQUA)
                tooltip.add(tooltip("name", customName).formatted(Formatting.GRAY))
            }
            if (
                entity is TameableEntity && entity.isTamed
                || entity is AbstractHorseEntity && entity.isTame
            ) {
                tooltip.add(tooltip("tamed").formatted(Formatting.GREEN))
            }
        } else {
            tooltip.add(tooltip("empty").formatted(Formatting.GRAY))
        }
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        tick(stack, entity.world, entity.pos, Vec3d(entity.x, entity.getBodyY(1.0), entity.z))
    }

    fun tick(stack: ItemStack, world: World, location: Vec3d): Boolean {
        return tick(stack, world, location, location)
    }

    fun tick(stack: ItemStack, world: World, spawnLocation: Vec3d, breakLocation: Vec3d): Boolean {
        if (!world.isClient() && hasEntity(stack)) {
            val e = getEntity(stack, world)!!
            if (!(e is TameableEntity && e.isTamed)) {
                val attackInstance = e.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                if (attackInstance != null) {
                    var damage = ceil(attackInstance.value).toInt()
                    if (damage > 0) {
                        if (world.getRandom().nextDouble() > 0.99) {
                            val unbreakingLevel = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack)
                            for (j in 0 until damage) {
                                if (UnbreakingEnchantment.shouldPreventDamage(stack, unbreakingLevel, world.random)) {
                                    damage--
                                }
                            }
                            stack.damage = stack.damage + damage
                            if (stack.damage >= stack.maxDamage) {
                                stack.decrement(1)
                                stack.damage = 0
                                assert(world.server != null)
                                val distance = 20
                                val buf = PacketByteBuf(Unpooled.buffer())
                                buf.writeDouble(breakLocation.x)
                                buf.writeDouble(breakLocation.y)
                                buf.writeDouble(breakLocation.z)
                                world.server!!.playerManager.sendToAround(
                                    null,
                                    breakLocation.x, breakLocation.y, breakLocation.z,
                                    distance.toDouble(),
                                    world.registryKey,
                                    ServerPlayNetworking.createS2CPacket(
                                        OriaPackets.BREAK_SOUL_ORB_PACKET_ID,
                                        buf
                                    )
                                )
                                spawnEntity(spawnLocation, stack, world)
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    private fun getEntityType(stack: ItemStack): EntityType<out LivingEntity>? {
        return if (hasEntity(stack)) {
            assert(stack.nbt != null)
            @Suppress("UNCHECKED_CAST")
            Registries.ENTITY_TYPE[stack.nbt!!.getInt("typeId")] as EntityType<out LivingEntity>
        } else {
            null
        }
    }

    private fun getEntity(stack: ItemStack, world: World?): LivingEntity? {
        if (!hasEntity(stack)) return null
        val type = getEntityType(stack)
        val entity = type!!.create(world) ?: return null
        assert(stack.nbt != null)
        entity.readNbt(stack.nbt!!.getCompound("entity"))
        return entity
    }

    private fun spawnEntity(pos: Vec3d, stack: ItemStack, world: World): Boolean {
        val entity = getEntity(stack, world) ?: return false
        entity.updatePositionAndAngles(pos.x, pos.y, pos.z, 0f, 0f)
        val serverWorld = world as ServerWorld
        if (serverWorld.getEntity(entity.uuid) != null) {
            entity.uuid = UUID.randomUUID()
        }
        world.spawnEntity(entity)
        return true
    }

    private fun hasEntity(stack: ItemStack): Boolean {
        val tag = stack.orCreateNbt
        return tag.contains("entity") && tag.contains("typeId")
    }

    companion object {
        val FILL_SOUND: SoundEvent = SoundEvents.BLOCK_BEACON_DEACTIVATE
        val RELEASE_SOUND: SoundEvent = SoundEvents.BLOCK_BEACON_POWER_SELECT
    }

    init {
        ModelPredicateProviderRegistry.register(Identifier("filled")) { stack, _, _, _ -> if (hasEntity(stack)) 1f else 0f }
    }
}