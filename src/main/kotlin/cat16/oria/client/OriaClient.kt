package cat16.oria.client

import cat16.oria.entity.OriaEntities
import cat16.oria.network.OriaPackets
import cat16.oria.item.OriaItems
import cat16.oria.item.SpatialOrbItem
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.item.ItemStack
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.PacketByteBuf
import net.minecraft.util.math.Vec3d

class OriaClient : ClientModInitializer {
    override fun onInitializeClient() {
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

        OriaEntities.init()
    }

    private fun randomVel(scale: Double): Double {
        return (Math.random() - 0.5) * 2 * scale
    }
}