package cat16.oria.component.impl

import cat16.oria.component.api.MagicComponent
import cat16.oria.entity.player.ManaManager
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound

class EntityMagicComponent(val kEntity: Entity) : MagicComponent, AutoSyncedComponent {
    override val manaManager: ManaManager = ManaManager()

    override fun readFromNbt(tag: NbtCompound) {
        manaManager.deserialize(tag)
    }

    override fun writeToNbt(tag: NbtCompound) {
        manaManager.serialize(tag)
    }
}