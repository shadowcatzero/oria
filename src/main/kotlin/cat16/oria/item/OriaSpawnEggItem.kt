package cat16.oria.item

import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.MobEntity
import net.minecraft.item.SpawnEggItem

class OriaSpawnEggItem(entity: OriaEntityInfo, type: EntityType<out MobEntity>, color1: Int, color2: Int) : SpawnEggItem(
    type,
    color1,
    color2,
    Settings()
), OriaItem {
    override val oriaName: String = "${entity.oriaName}_spawn_egg"
}
