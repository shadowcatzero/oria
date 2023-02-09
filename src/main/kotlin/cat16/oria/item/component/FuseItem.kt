package cat16.oria.item.component

import cat16.oria.entity.bomb.PotionBombEntity
import cat16.oria.item.OriaItem
import net.minecraft.item.Item
import java.util.*

class FuseItem(name: String, val explodeCondition: (PotionBombEntity) -> Boolean) : Item(Settings()), OriaItem {

    val fuseName = name
    override val oriaName: String = "${name}_fuse"

    init {
        FUSES[name] = this
    }

    companion object {
        private val FUSES = mutableMapOf<String, FuseItem>()
        fun getFuses() = Collections.unmodifiableMap(FUSES)
        fun getFuse(name: String?) = FUSES[name]
        fun getCondition(name: String?) = getFuse(name)?.explodeCondition ?: {false}
    }
}