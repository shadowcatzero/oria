package cat16.oria

import cat16.oria.block.OriaBlocks
import cat16.oria.Oria.id
import cat16.oria.item.OriaItems
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.util.function.Consumer

object OriaItemGroups {
    val ORIA_ITEMS = build(
        "oria_items",
        ItemStack(Items.BROWN_MUSHROOM),
        Consumer { itemStacks: MutableList<ItemStack?> ->
            OriaItems.items.forEach { item: Item -> itemStacks.add(ItemStack(item)) }
            OriaBlocks.blockItemPairs.forEach { pair ->
                pair.second?.let {
                    itemStacks.add(
                        ItemStack(it)
                    )
                }
            }
        })

    fun init() {
        // init fields
    }

    private fun build(name: String?, stack: ItemStack?, stacks: Consumer<MutableList<ItemStack?>>?): ItemGroup {
        return FabricItemGroupBuilder.create(
            id(name!!)
        ).icon { stack }.appendItems(stacks).build()
    }
}