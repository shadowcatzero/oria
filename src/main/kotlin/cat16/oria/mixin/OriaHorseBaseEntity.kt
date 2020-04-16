package cat16.oria.mixin

import cat16.oria.item.tool.SpatialOrbItem
import net.minecraft.entity.passive.HorseBaseEntity
import net.minecraft.inventory.BasicInventory
import net.minecraft.util.math.Vec3d

object OriaHorseBaseEntity {
   fun onTick(horse: HorseBaseEntity, inv: BasicInventory) {
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