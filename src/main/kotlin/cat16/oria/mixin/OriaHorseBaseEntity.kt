package cat16.oria.mixin

import cat16.oria.item.tool.SpatialOrbItem
import net.minecraft.entity.passive.AbstractHorseEntity
import net.minecraft.inventory.Inventory
import net.minecraft.util.math.Vec3d

object OriaHorseBaseEntity {
   fun onTick(horse: AbstractHorseEntity, inv: Inventory) {
      for (i in 0 until inv.size()) {
         val stack = inv.getStack(i)
         val item = stack.item
         if (item is SpatialOrbItem) {
            val breakPos = Vec3d(horse.x, horse.getBodyY(1.0), horse.z)
            item.tick(stack, horse.world, horse.pos, breakPos)
         }
      }
   }
}