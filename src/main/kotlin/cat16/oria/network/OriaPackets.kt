package cat16.oria.network

import cat16.oria.Oria.id
import cat16.oria.item.OriaItems
import cat16.oria.item.SpatialOrbItem
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

object OriaPackets {

    @JvmField
    val BREAK_SOUL_ORB_PACKET_ID = packet("soul_orb_break")
    @JvmField
    val REMOVE_CURSOR_STACK_PACKET_ID = packet("cursor_stack_remove")

    fun clientInit() {
        ClientSidePacketRegistry.INSTANCE.register(
            OriaPackets.BREAK_SOUL_ORB_PACKET_ID
        ) { context: PacketContext, data: PacketByteBuf ->
            val x = data.readDouble()
            val y = data.readDouble()
            val z = data.readDouble()
            val pos = Vec3d(x, y, z)
            val player = context.player
            player.world.playSound(pos.x, pos.y, pos.z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 1f, 1f, true)
            player.world.playSound(pos.x, pos.y, pos.z, SpatialOrbItem.RELEASE_SOUND, SoundCategory.HOSTILE, 1f, 1f, true)
            for (i in 0..9) {
                player.world.addParticle(
                    ItemStackParticleEffect(ParticleTypes.ITEM, ItemStack(OriaItems.SPATIAL_ORB)),
                    pos.x, pos.y, pos.z, randomVel(0.2), randomVel(0.2), randomVel(0.2)
                )
            }
        }
        ClientSidePacketRegistry.INSTANCE.register(
            OriaPackets.REMOVE_CURSOR_STACK_PACKET_ID
        ) { context: PacketContext, data: PacketByteBuf? -> context.player.inventory.cursorStack = ItemStack.EMPTY }
    }

    private fun randomVel(scale: Double): Double {
        return (Math.random() - 0.5) * 2 * scale
    }

    private fun packet(name: String): Identifier {
        return id(name)
    }
}