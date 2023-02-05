package cat16.oria.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.ExperienceDroppingBlock
import net.minecraft.block.Material
import net.minecraft.item.Item

class OriaOreBlock(override val oriaName: String, settings: Settings) : ExperienceDroppingBlock(settings), OriaBlock {

    override val itemSettings: Item.Settings? = null

    constructor(oriaName: String): this(oriaName, FabricBlockSettings.of(Material.STONE))
}