package cat16.oria.block

import cat16.oria.Oria
import net.minecraft.block.Block
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

@Suppress("unused")
object OriaBlocks {

    val blockItemPairs: MutableList<Pair<Block, Item?>> = mutableListOf()

    val ORIUM_ORE = register(OriaOreBlock("orium_ore"))
    val INGIS_FLOWER = register(
        MagicFlowerBlock(
            "ignis_flower",
            StatusEffects.FIRE_RESISTANCE,
            10
        )
    )
    val ORANGE_FLOWER = register(
        MagicFlowerBlock(
            "orange_flower",
            StatusEffects.HASTE,
            10
        )
    )
    val PURPLE_FLOWER = register(
        MagicFlowerBlock(
            "purple_flower",
            StatusEffects.REGENERATION,
            10
        )
    )
    val TURQUOISE_FLOWER = register(
        MagicFlowerBlock(
            "turquoise_flower",
            StatusEffects.NIGHT_VISION,
            10
        )
    )
    val RAINBOW_FLOWER = register(RainbowFlowerBlock())

    fun init() {
        blockItemPairs.forEach { pair ->
            val block = pair.first
            val item = pair.second

            val id = Oria.id((block as OriaBlock).oriaName)
            Registry.register(Registries.BLOCK, id, block)
            item?.let { Registry.register(Registries.ITEM, id, it) }
        }
    }

    private fun <B> register(block: B): B where B : Block, B : OriaBlock {
        blockItemPairs.add(Pair(block, BlockItem(block, block.itemSettings ?: Item.Settings())))
        return block
    }

    private fun <B> registerBlock(block: B, name: String): B where B : Block, B : OriaBlock {
        blockItemPairs.add(Pair(block, null))
        return block
    }

    fun clientInit() {
        blockItemPairs.forEach { pair -> (pair.first as OriaBlock).clientInit() }
    }
}