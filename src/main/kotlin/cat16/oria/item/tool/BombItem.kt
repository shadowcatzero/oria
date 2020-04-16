package cat16.oria.item.tool

import cat16.oria.entity.bomb.BombEntity
import cat16.oria.item.OriaItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class BombItem(settings: Settings) : Item(settings), OriaItem {
    constructor() : this(Settings())

    override val oriaName: String = "bomb"

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        world.playSound(
            null as PlayerEntity?,
            user.x,
            user.y,
            user.z,
            SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
            SoundCategory.PLAYERS,
            0.5f,
            1f / (RANDOM.nextFloat() * 0.4f + 0.8f)
        )

        if (!world.isClient) {
            val bombEntity = BombEntity(world)
            bombEntity.updatePosition(user.x, user.eyeY - 0.1, user.z)
            bombEntity.launchFrom(user, user.pitch, user.yaw, user.roll.toFloat(), 0.3)
            bombEntity.fuse = stack.tag?.getString("fuse")
            world.spawnEntity(bombEntity)
        }

        if (!user.abilities.creativeMode) {
            stack.decrement(1)
        }

        return TypedActionResult.success(stack)
    }
}