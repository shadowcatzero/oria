package cat16.oria.entity

import cat16.oria.util.OriaObjectInfo
import net.minecraft.entity.EntityCategory
import net.minecraft.entity.EntityDimensions

interface OriaEntityInfo : OriaObjectInfo {
    val dimensions: EntityDimensions
    val category: EntityCategory
    override val typeName: String
        get() = "entity"
}