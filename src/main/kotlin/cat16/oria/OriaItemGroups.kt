package cat16.oria

import cat16.oria.Oria.id
import cat16.oria.block.OriaBlocks
import cat16.oria.item.OriaItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.util.function.Consumer


object OriaItemGroups {
    val ORIA_ITEMS = FabricItemGroup.builder(id("oria_items")).icon { ItemStack(Items.BROWN_MUSHROOM) }.build()

    fun init() {
        ItemGroupEvents.modifyEntriesEvent(ORIA_ITEMS).register(ModifyEntries { content ->
            OriaItems.items.forEach { item: Item -> content.add(ItemStack(item)) }
            OriaBlocks.blockItemPairs.forEach { pair ->
                pair.second?.let {
                    content.add(
                        ItemStack(it)
                    )
                }
            }
        })
    }
}