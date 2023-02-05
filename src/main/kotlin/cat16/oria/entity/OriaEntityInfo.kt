package cat16.oria.entity

import cat16.oria.util.OriaObjectInfo
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.SpawnGroup

interface OriaEntityInfo : OriaObjectInfo {
    val dimensions: EntityDimensions
    val spawnGroup: SpawnGroup
    override val typeName: String
        get() = "entity"
}