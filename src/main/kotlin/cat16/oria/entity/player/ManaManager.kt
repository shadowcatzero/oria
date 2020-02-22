package cat16.oria.entity.player

import net.minecraft.nbt.CompoundTag

class ManaManager {

    var mana: Float = 20f
    var maxMana: Float = 20f

    fun serialize(tag: CompoundTag) {
        tag.putFloat("oriaMana", mana)
        tag.putFloat("oriaMaxMana", maxMana)
    }

    fun deserialize(tag: CompoundTag) {
        if(tag.contains("oriaMana")) {
            this.mana = tag.getFloat("oriaMana")
            this.maxMana = tag.getFloat("oriaMaxMana")
        }
    }
}