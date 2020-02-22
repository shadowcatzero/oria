package cat16.oria.entity

import cat16.oria.Oria
import cat16.oria.entity.rat.RatEntity
import cat16.oria.entity.rat.RatRenderer
import cat16.oria.entity.tntMinion.TntMinionEntity
import cat16.oria.entity.tntMinion.TntMinionRenderer
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityCategory
import net.minecraft.entity.EntityType
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraft.world.biome.Biome

object OriaEntities {

    val types: MutableList<Pair<EntityType<*>, (EntityRenderDispatcher) -> EntityRenderer<*>>> = mutableListOf()



    val RAT = register(::RatEntity, RatEntity.Companion, ::RatRenderer)
    val TNT_MINION = register(::TntMinionEntity, TntMinionEntity.Companion, ::TntMinionRenderer)



    fun init() {
        Registry.BIOME.forEach { biome ->
            run {
                biome.getEntitySpawnList(EntityCategory.CREATURE).add(Biome.SpawnEntry(RAT, 50, 1, 2))
            }
        }
    }

    fun clientInit() {
        types.forEach{type -> EntityRendererRegistry.INSTANCE.register(type.first) {dispatcher, _ -> type.second(dispatcher) } }
    }

    private fun <E> register(factory: (EntityType<E>, World) -> E, info: OriaEntityInfo, renderer: (EntityRenderDispatcher) -> EntityRenderer<*>) : EntityType<E> where E : Entity {
        val entityType = Registry.register(Registry.ENTITY_TYPE, Oria.id(info.oriaName), FabricEntityTypeBuilder.create(info.category, factory).size(info.dimensions).build())
        types.add(Pair(entityType, renderer))
        return entityType
    }
}
