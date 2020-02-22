package cat16.oria.item

import cat16.oria.Oria
import cat16.oria.entity.OriaEntities
import cat16.oria.entity.rat.RatEntity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.registry.Registry

object OriaItems {

    val items: MutableList<Item> = mutableListOf()

    val ORIUM_SHARD = register(OriumShard())
    val SPATIAL_ORB = register(SpatialOrbItem())
    val SOUL_ORB = register(SoulOrbItem())
    val STAFF = register(StaffItem())
    val SKILL_BOOK = register(SkillBookItem())
    val RAT_TAIL = register(SimpleItem("rat_tail"))

    val RAT_SPAWN_EGG = register(OriaSpawnEgg(RatEntity.Companion, OriaEntities.RAT, 0x2222222, 0xffaaaa))

    fun init() {
        items.forEach { Registry.register(Registry.ITEM, Oria.id((it as OriaItem).oriaName), it) }
    }

    private fun <I> register(item: I): I where I : Item, I : OriaItem {
        items.add(item)
        return item
    }
}
