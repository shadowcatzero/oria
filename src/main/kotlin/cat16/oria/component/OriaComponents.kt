package cat16.oria.component

import cat16.oria.Oria
import cat16.oria.component.api.EntityMagicComponent
import cat16.oria.component.impl.EntityMagicComponentImpl
import nerdhub.cardinal.components.api.ComponentRegistry

import nerdhub.cardinal.components.api.ComponentType
import nerdhub.cardinal.components.api.component.Component
import nerdhub.cardinal.components.api.event.EntityComponentCallback
import nerdhub.cardinal.components.api.util.EntityComponents
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy
import net.minecraft.entity.player.PlayerEntity
import kotlin.reflect.KClass

object OriaComponents {
    val MAGIC: ComponentType<EntityMagicComponent> = register("magic", EntityMagicComponent::class)
        .attach(EntityComponentCallback.event(PlayerEntity::class.java)) { player ->
            EntityMagicComponentImpl(
                player
            )
        }

    fun init() {
        EntityComponents.setRespawnCopyStrategy(MAGIC, RespawnCopyStrategy.INVENTORY)
    }

    private fun <T : Component> register(name: String, cls: KClass<T>): ComponentType<T> {
        return ComponentRegistry.INSTANCE.registerIfAbsent(
            Oria.id(name),
            cls.java
        )
    }
}