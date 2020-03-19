package cat16.oria.entity.bomb

import com.google.common.collect.ImmutableList
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.CompositeEntityModel

class BombEntityModel<T> : CompositeEntityModel<T>() where T : BombEntity {
    private val shell: ModelPart
    //private val fuse: ModelPart

    init {
        textureWidth = 64
        textureHeight = 64

        shell = ModelPart(this, 0, 16)
        shell.setPivot(0.0F, 22.0F, -6.0F)
        shell.addCuboid(-2.0F, -2.0F, -5.0F, 4f, 4f, 5f)
    }

    override fun setAngles(
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        customAngle: Float,
        headYaw: Float,
        headPitch: Float
    ) {

    }

    override fun getParts(): MutableIterable<ModelPart> {
        return ImmutableList.of(
            shell
        )
    }
}