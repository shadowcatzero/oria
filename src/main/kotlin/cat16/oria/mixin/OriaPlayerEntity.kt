package cat16.oria.mixin

import cat16.oria.component.OriaComponents
import net.minecraft.entity.player.PlayerEntity

object OriaPlayerEntity {
    fun tick(player: PlayerEntity) {
        if(player.isSneaking) {
            val component = OriaComponents.MAGIC.get(player)
            val manager = component.manaManager
            if(manager.mana < manager.maxMana) {
                manager.mana += 0.01F
            }
        }
    }
}