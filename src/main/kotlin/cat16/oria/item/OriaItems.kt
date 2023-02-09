package cat16.oria.item

import cat16.oria.Oria
import cat16.oria.entity.OriaEntities
import cat16.oria.entity.rat.RatEntity
import cat16.oria.item.component.FuseItem
import cat16.oria.item.material.OriumShard
import cat16.oria.item.tool.*
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object OriaItems {

    val items: MutableList<Item> = mutableListOf()

    val ORIUM_SHARD = register(OriumShard())
    val SPATIAL_ORB = register(SpatialOrbItem())
    val SOUL_ORB = register(SoulOrbItem())
    val STAFF = register(StaffItem())
    val SKILL_BOOK = register(SkillBookItem())
    val RAT_TAIL = register(SimpleItem("rat_tail"))
    val COOKED_RAT_TAIL = register(SimpleItem("cooked_rat_tail", Item.Settings().food(OriaFoodComponents.COOKED_RAT_TAIL)))
    val SHORT_FUSE = register(FuseItem("timed") { entity -> entity.ticks >= 3 * 20})
    val POTION_BOMB = register(PotionBombItem())

    val RAT_SPAWN_EGG = register(OriaSpawnEggItem(RatEntity.Companion, OriaEntities.RAT, 0x2222222, 0xffaaaa))

    fun init() {
        items.forEach { Registry.register(Registries.ITEM, Oria.id((it as OriaItem).oriaName), it) }
    }

    private fun <I> register(item: I): I where I : Item, I : OriaItem {
        items.add(item)
        return item
    }
}
