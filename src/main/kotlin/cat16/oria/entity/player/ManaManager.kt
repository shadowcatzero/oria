package cat16.oria.entity.player

import net.minecraft.nbt.NbtCompound

class ManaManager {

    var mana: Float = 20f
    var maxMana: Float = 20f

    fun serialize(tag: NbtCompound) {
        tag.putFloat("oriaMana", mana)
        tag.putFloat("oriaMaxMana", maxMana)
    }

    fun deserialize(tag: NbtCompound) {
        if(tag.contains("oriaMana")) {
            this.mana = tag.getFloat("oriaMana")
            this.maxMana = tag.getFloat("oriaMaxMana")
        }
    }
}