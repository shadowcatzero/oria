package cat16.oria.component.impl

import cat16.oria.component.api.EntityMagicComponent
import cat16.oria.component.OriaComponents
import cat16.oria.entity.player.ManaManager
import net.minecraft.entity.Entity
import net.minecraft.nbt.CompoundTag

class EntityMagicComponentImpl(val kEntity: Entity) : EntityMagicComponent {
    override val manaManager: ManaManager = ManaManager()

    override fun getEntity() = kEntity

    override fun getComponentType() = OriaComponents.MAGIC

    override fun fromTag(tag: CompoundTag) {
        manaManager.deserialize(tag)
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        manaManager.serialize(tag)
        return tag
    }
}