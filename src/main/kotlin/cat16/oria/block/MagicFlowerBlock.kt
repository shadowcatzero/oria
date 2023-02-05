package cat16.oria.block

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.FlowerBlock
import net.minecraft.block.Material
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.Item
import net.minecraft.sound.BlockSoundGroup

open class MagicFlowerBlock(
    override val oriaName: String,
    statusEffect: StatusEffect,
    duration: Int,
    settings: Settings
) : FlowerBlock(statusEffect, duration, settings), OriaBlock {

    override val itemSettings: Item.Settings? = null

    constructor(oriaName: String, statusEffect: StatusEffect, duration: Int) : this(
        oriaName,
        statusEffect,
        duration,
        FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)
    )

    override fun clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout())
    }
}