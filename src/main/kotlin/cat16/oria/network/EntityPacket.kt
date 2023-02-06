package cat16.oria.network

import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper


object EntityPacket {
    /** Meant for overriding [net.minecraft.entity.LivingEntity.createSpawnPacket]  */
    fun createSpawnPacket(e: Entity, packetID: Identifier?): Packet<ClientPlayPacketListener> {
        val pbb = PacketByteBuf(Unpooled.buffer())
        pbb.writeVarInt(Registries.ENTITY_TYPE.getRawId(e.type))
        pbb.writeUuid(e.uuid)
        pbb.writeVarInt(e.id)
        pbb.writeDouble(e.x)
        pbb.writeDouble(e.y)
        pbb.writeDouble(e.z)
        pbb.writeByte(MathHelper.floor(e.pitch * 256.0f / 360.0f))
        pbb.writeByte(MathHelper.floor(e.yaw * 256.0f / 360.0f))
        return ServerPlayNetworking.createS2CPacket(packetID, pbb)
    }

    @Environment(EnvType.CLIENT)
    fun client_RegisterEntityPacket(packetID: Identifier) {
        //Biow0rks.logger.debug("Registered Spawn Packet %s", packetID)
        //stolen code lol but that reminds me I should add a logger
        ClientPlayNetworking.registerGlobalReceiver(
            packetID
        ) { client, _, pbb: PacketByteBuf, _ ->
            val et: EntityType<*> = Registries.ENTITY_TYPE.get(pbb.readVarInt())
            val uuid = pbb.readUuid()
            val entityId = pbb.readVarInt()
            val x = pbb.readDouble()
            val y = pbb.readDouble()
            val z = pbb.readDouble()
            val pitch = pbb.readByte() * 360 / 256.0f
            val yaw = pbb.readByte() * 360 / 256.0f
            client.execute {
                val world = MinecraftClient.getInstance().world
                val e: Entity = et.create(world)!!
                e.updateTrackedPosition(x, y, z)
                e.setPos(x, y, z)
                e.pitch = pitch
                e.yaw = yaw
                e.id = entityId
                e.uuid = uuid
                world!!.addEntity(entityId, e)
            }
        }
    }
}
