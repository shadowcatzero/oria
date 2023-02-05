package cat16.oria.component

import cat16.oria.Oria
import cat16.oria.component.api.MagicComponent
import cat16.oria.component.impl.EntityMagicComponent
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy

import net.minecraft.entity.player.PlayerEntity

object OriaComponents : EntityComponentInitializer {

    val MAGIC = ComponentRegistryV3.INSTANCE.getOrCreate(Oria.id("magic"), MagicComponent::class.java)

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.beginRegistration(PlayerEntity::class.java, MAGIC)
            .respawnStrategy(RespawnCopyStrategy.INVENTORY)
            .end(::EntityMagicComponent)
    }
}