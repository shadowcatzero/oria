package cat16.oria.network

import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketContext
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry


object EntityPacket {
    /** Meant for overriding [net.minecraft.entity.LivingEntity.createSpawnPacket]  */
    fun createSpawnPacket(e: Entity, packetID: Identifier?): Packet<*> {
        val pbb = PacketByteBuf(Unpooled.buffer())
        pbb.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()))
        pbb.writeUuid(e.getUuid())
        pbb.writeVarInt(e.getEntityId())
        pbb.writeDouble(e.getX())
        pbb.writeDouble(e.getY())
        pbb.writeDouble(e.getZ())
        pbb.writeByte(MathHelper.floor(e.pitch * 256.0f / 360.0f))
        pbb.writeByte(MathHelper.floor(e.yaw * 256.0f / 360.0f))
        return ServerSidePacketRegistry.INSTANCE.toPacket(packetID, pbb)
    }

    @Environment(EnvType.CLIENT)
    fun client_RegisterEntityPacket(packetID: Identifier) {
        //Biow0rks.logger.debug("Registered Spawn Packet %s", packetID)
        //stolen code lol but that reminds me I should add a logger
        ClientSidePacketRegistry.INSTANCE.register(
            packetID
        ) { context: PacketContext, pbb: PacketByteBuf ->
            val et: EntityType<*> = Registry.ENTITY_TYPE.get(pbb.readVarInt())
            val uuid = pbb.readUuid()
            val entityId = pbb.readVarInt()
            val x = pbb.readDouble()
            val y = pbb.readDouble()
            val z = pbb.readDouble()
            val pitch = pbb.readByte() * 360 / 256.0f
            val yaw = pbb.readByte() * 360 / 256.0f
            context.taskQueue.execute {
                val world = MinecraftClient.getInstance().world
                val e: Entity = et.create(world)!!
                e.updateTrackedPosition(x, y, z)
                e.setPos(x, y, z)
                e.pitch = pitch
                e.yaw = yaw
                e.setEntityId(entityId)
                e.setUuid(uuid)
                world!!.addEntity(entityId, e)
            }
        }
    }
}
