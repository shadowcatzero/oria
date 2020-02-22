package cat16.oria.world

import cat16.oria.block.OriaBlocks
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback
import net.minecraft.block.BlockState
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ConfiguredDecorator
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import java.util.function.Consumer

object OriaBiomeHandler {
    fun init() {
        Registry.BIOME.forEach(Consumer { biome: Biome ->
            handleBiome(
                biome
            )
        })
        RegistryEntryAddedCallback.event(Registry.BIOME).register(RegistryEntryAddedCallback { i: Int, identifier: Identifier?, biome: Biome ->
            handleBiome(
                biome
            )
        })
    }

    private fun handleBiome(biome: Biome) {
        if (biome.category != Biome.Category.NETHER && biome.category != Biome.Category.THEEND) {
            spawnOre(
                OriaBlocks.ORIUM_ORE.defaultState,
                biome,
                6,
                3,
                0,
                255
            )
        }
    }

    private fun spawnOre(blockState: BlockState, biome: Biome, veinSize: Int, veinsPerChunk: Int, minY: Int, maxY: Int) {
        biome.addFeature(
                GenerationStep.Feature.UNDERGROUND_ORES,
                ConfiguredFeature(
                        Feature.ORE,
                        OreFeatureConfig(
                                OreFeatureConfig.Target.NATURAL_STONE,
                                blockState,
                                veinSize
                        )
                ).createDecoratedFeature(
                        ConfiguredDecorator(
                                Decorator.COUNT_RANGE,
                                RangeDecoratorConfig(
                                        veinsPerChunk,
                                        0,
                                        minY,
                                        maxY
                                )
                        )
                )
        )
    }
}