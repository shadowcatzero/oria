package cat16.oria.item.tool

import cat16.oria.item.OriaItem
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.registry.Registries
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.world.World

class SoulOrbItem(settings: Settings?) : Item(settings), OriaItem {

    constructor() : this(Settings().maxCount(1).maxDamage(100))

    override val oriaName = "soul_orb"

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): Boolean {
        val orb = user.getStackInHand(hand)
        return if (!hasSoul(orb)) {
            val tag = orb.orCreateNbt
            tag.putInt("typeId", Registries.ENTITY_TYPE.getRawId(entity.type))
            entity.remove(Entity.RemovalReason.DISCARDED)
            user.playSound(FILL_SOUND, 1.0f, 1.0f)
            stack.cooldown = 5
            true
        } else {
            false
        }
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        if (context.player == null) return ActionResult.FAIL
        val orb = context.player!!.getStackInHand(context.hand)
        return if (hasSoul(orb)) {
            assert(orb.tag != null)
            orb.tag!!.remove("typeId")
            context.player!!.playSound(RELEASE_SOUND, 1.0f, 1.0f)
            ActionResult.SUCCESS
        } else {
            ActionResult.PASS
        }
    }

    override fun hasEnchantmentGlint(stack: ItemStack): Boolean {
        return hasSoul(stack)
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        if (hasSoul(stack)) {
            val type: EntityType<*>? = getEntityType(stack)
            val typeText = type!!.name.deepCopy().formatted(Formatting.DARK_PURPLE)
            tooltip.add(TranslatableText(tooltipKey("filled"), typeText).formatted(Formatting.GRAY))
        } else {
            tooltip.add(TranslatableText(tooltipKey("empty")).formatted(Formatting.GRAY))
        }
    }

    //override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
    //    tick(stack, entity.world, entity.pos, Vec3d(entity.x, entity.getBodyY(1.0), entity.z))
    //}

    //fun tick(stack: ItemStack?, world: World?, location: Vec3d?): Boolean {
    //    return tick(stack, world, location, location)
    //}

    //fun tick(stack: ItemStack?, world: World?, spawnLocation: Vec3d?, breakLocation: Vec3d?): Boolean { // TODO: write
    //    return false
    //}

    private fun getEntityType(stack: ItemStack): EntityType<out LivingEntity>? {
        return if (hasSoul(stack)) {
            assert(stack.tag != null)
            @Suppress("UNCHECKED_CAST")
            Registry.ENTITY_TYPE[stack.tag!!.getInt("typeId")] as EntityType<out LivingEntity>
        } else {
            null
        }
    }

    private fun hasSoul(stack: ItemStack): Boolean {
        val tag = stack.orCreateTag
        return tag.contains("typeId")
    }

    companion object {
        val FILL_SOUND: SoundEvent = SoundEvents.BLOCK_BEACON_DEACTIVATE
        val RELEASE_SOUND: SoundEvent = SoundEvents.BLOCK_BEACON_POWER_SELECT
        private fun tooltipKey(name: String): String {
            return "item.oria.spatial_orb.tooltip_$name"
        }
    }

    init {
        addPropertyGetter(Identifier("filled")) { stack: ItemStack, world: World?, entity: LivingEntity? -> if (hasSoul(stack)) 1f else 0f }
    }
}