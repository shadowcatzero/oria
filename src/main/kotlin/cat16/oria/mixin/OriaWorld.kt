package cat16.oria.mixin

import cat16.oria.item.SpatialOrbItem
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventory
import net.minecraft.util.math.Vec3d

object OriaWorld {
    fun onTick(blockEntity: BlockEntity) {
        if (blockEntity is Inventory) {
            val inv = blockEntity as Inventory
            for (i in 0 until inv.invSize) {
                val stack = inv.getInvStack(i)
                val item = stack.item
                if (item is SpatialOrbItem) {
                    item.tick(
                        stack,
                        blockEntity.world!!,
                        Vec3d.method_24954(blockEntity.pos).add(0.5, 1.0, 0.5)
                    )
                }
            }
        }
    }
}