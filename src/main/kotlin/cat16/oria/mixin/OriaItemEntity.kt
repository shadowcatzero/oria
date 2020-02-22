package cat16.oria.mixin

import cat16.oria.item.SpatialOrbItem
import net.minecraft.entity.ItemEntity
import net.minecraft.util.math.Vec3d

object OriaItemEntity {
    fun onTick(entity: ItemEntity) {
        val stack = entity.stack
        if (stack.item is SpatialOrbItem) {
            val orb = stack.item as SpatialOrbItem
            val pos: Vec3d = entity.pos.add(Vec3d(0.0, 0.1, 0.0))
            orb.tick(stack, entity.world, pos)
        }
    }
}