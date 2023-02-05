package cat16.oria.entity

import cat16.oria.Oria
import net.minecraft.util.Identifier
import software.bernie.geckolib.core.animatable.GeoAnimatable
import software.bernie.geckolib.model.GeoModel

abstract class OriaEntityModel<T : GeoAnimatable> : GeoModel<T>() {
    abstract val id: String
    override fun getModelResource(animatable: T): Identifier {
        return Oria.id("geo/${id}.geo.json")
    }

    override fun getTextureResource(animatable: T): Identifier {
        return Oria.id("textures/entity/${id}.png")
    }

    override fun getAnimationResource(animatable: T): Identifier {
        return Oria.id("animations/${id}.animation.json")
    }
}