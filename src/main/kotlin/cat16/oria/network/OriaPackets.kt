package cat16.oria.network

import cat16.oria.Oria.id
import cat16.oria.item.OriaItems
import cat16.oria.item.tool.SpatialOrbItem
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
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
    @JvmField
    val ENTITY_SPAWN_PACKET = packet("entity_spawn")

    fun clientInit() {
        register(BREAK_SOUL_ORB_PACKET_ID) { client, handler, data: PacketByteBuf, sender ->
            val x = data.readDouble()
            val y = data.readDouble()
            val z = data.readDouble()
            val pos = Vec3d(x, y, z)
            client.world?.playSound(
                pos.x,
                pos.y,
                pos.z,
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.HOSTILE,
                1f,
                1f,
                true
            )
            client.world?.playSound(
                pos.x,
                pos.y,
                pos.z,
                SpatialOrbItem.RELEASE_SOUND,
                SoundCategory.HOSTILE,
                1f,
                1f,
                true
            )
            for (i in 0..9) {
                client.world?.addParticle(
                    ItemStackParticleEffect(ParticleTypes.ITEM, ItemStack(OriaItems.SPATIAL_ORB)),
                    pos.x, pos.y, pos.z, randomVel(0.2), randomVel(0.2), randomVel(0.2)
                )
            }
        }
        register(REMOVE_CURSOR_STACK_PACKET_ID) { client, _, _, _ ->
            client.player?.inventory.cursorStack = ItemStack.EMPTY
        }
        EntityPacket.client_RegisterEntityPacket(ENTITY_SPAWN_PACKET)
    }

    private fun randomVel(scale: Double): Double {
        return (Math.random() - 0.5) * 2 * scale
    }

    private fun packet(name: String): Identifier {
        return id(name)
    }

    private fun register(id: Identifier, consumer: (MinecraftClient, ClientPlayNetworkHandler, PacketByteBuf, PacketSender) -> Unit) {
        ClientPlayNetworking.registerReceiver(id, consumer)
    }
}