package cat16.oria.block

import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
import net.minecraft.item.Item

class OriaOreBlock(override val oriaName: String, settings: Settings) : OreBlock(settings), OriaBlock {

    override val itemSettings: Item.Settings? = null

    constructor(oriaName: String): this(oriaName, FabricBlockSettings.of(Material.STONE).build())
}