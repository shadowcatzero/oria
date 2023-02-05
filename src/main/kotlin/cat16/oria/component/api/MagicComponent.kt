package cat16.oria.component.api

import cat16.oria.entity.player.ManaManager
import dev.onyxstudios.cca.api.v3.component.ComponentV3

interface MagicComponent : ComponentV3 {
    val manaManager: ManaManager
}