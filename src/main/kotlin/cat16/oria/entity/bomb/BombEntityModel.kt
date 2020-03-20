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

        shell = ModelPart(this)
 		shell.setPivot(0.0F, 3.0F, 0.0F);

        val top = ModelPart(this, 16, 0)
        top.addCuboid(-2.0F, 2.0F, -2.0F, 4f, 1f, 4f, 0.0F, false)
        val bottom = ModelPart(this, 0, 0)
        bottom.addCuboid(-2.0F, -3.0F, -2.0F, 4f, 1f, 4f, 0.0F, false)
        val front = ModelPart(this, 0, 5)
        front.addCuboid(-2.0F, -2.0F, -3.0F, 4f, 4f, 1f, 0.0F, false)
        val back = ModelPart(this, 0, 18)
        back.addCuboid(-2.0F, -2.0F, 2.0F, 4f, 4f, 1f, 0.0F, false)
        val left = ModelPart(this, 0, 10)
        left.addCuboid(-3.0F, -2.0F, -2.0F, 1f, 4f, 4f, 0.0F, false)
        val right = ModelPart(this, 0, 23)
        right.addCuboid(2.0F, -2.0F, -2.0F, 1f, 4f, 4f, 0.0F, false)

        shell.addChild(top)
        shell.addChild(bottom)
        shell.addChild(front)
        shell.addChild(back)
        shell.addChild(left)
        shell.addChild(right)
    }

    override fun setAngles(
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        customAngle: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        shell.pitch = headPitch
    }

    override fun getParts(): MutableIterable<ModelPart> {
        return ImmutableList.of(
            shell
        )
    }
}