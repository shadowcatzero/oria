package cat16.oria.mixin

import cat16.oria.item.tool.SpatialOrbItem
import cat16.oria.network.OriaPackets
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.Vec3d

object OriaServerPlayerEntity {
    fun onTick(player: ServerPlayerEntity) {
        if (handleStack(player, player.currentScreenHandler.cursorStack)) {
            player.currentScreenHandler.cursorStack = ItemStack.EMPTY
            player.networkHandler.sendPacket(
                ServerPlayNetworking.createS2CPacket(
                    OriaPackets.REMOVE_CURSOR_STACK_PACKET_ID, PacketByteBuf(
                        Unpooled.buffer()
                    )
                )
            )
        }
        val inv: Inventory = player.enderChestInventory
        for (i in 0 until inv.size()) {
            handleStack(player, inv.getStack(i))
        }
    }
    private fun handleStack(player: ServerPlayerEntity, stack: ItemStack): Boolean {
        val item = stack.item
        if (item is SpatialOrbItem) {
            val breakPos = Vec3d(player.x, player.getBodyY(1.0), player.z)
            return item.tick(stack, player.world, player.pos, breakPos)
        }
        return false
    }
}