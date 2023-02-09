package cat16.oria.entity

import cat16.oria.Oria
import cat16.oria.entity.bomb.PotionBombEntity
import cat16.oria.entity.bomb.PotionBombEntityRenderer
import cat16.oria.entity.rat.RatEntity
import cat16.oria.entity.rat.RatEntityRenderer
import cat16.oria.entity.tntMinion.TntMinionEntity
import cat16.oria.entity.tntMinion.TntMinionEntityRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.world.World

object OriaEntities {
    private val toRegister: MutableList<() -> Unit> = mutableListOf()



    val RAT = register(::RatEntity, RatEntity.Companion, ::RatEntityRenderer)
    val TNT_MINION = register(::TntMinionEntity, TntMinionEntity.Companion, ::TntMinionEntityRenderer)
    val BOMB = register(::PotionBombEntity, PotionBombEntity.Companion, ::PotionBombEntityRenderer)



    fun init() {
    }

    fun clientInit() {
        EntityRendererRegistry.register(RAT) { ctx -> RatEntityRenderer(ctx) }
        toRegister.forEach{r -> r()}
    }

    private fun <E : Entity> register(factory: (EntityType<E>, World) -> E, info: OriaEntityInfo, renderer: EntityRendererFactory<E>) : EntityType<E> {
        val entityType = Registry.register(Registries.ENTITY_TYPE, Oria.id(info.oriaName), FabricEntityTypeBuilder.create(info.spawnGroup, factory).dimensions(info.dimensions).build())
        toRegister.add { EntityRendererRegistry.register(entityType, renderer) }
        return entityType
    }
}
