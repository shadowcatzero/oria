package cat16.oria.entity.tntMinion

import com.google.common.collect.ImmutableList
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.CompositeEntityModel
import net.minecraft.util.math.MathHelper

class TntMinionEntityModel<T> : CompositeEntityModel<T>() where T : TntMinionEntity {

    private val body: ModelPart
    private val leftLeg: ModelPart
    private val rightLeg: ModelPart

    init {
        textureWidth = 32
        textureHeight = 32

        val bodyY = 19.75f

        body = ModelPart(this, 0, 0)
        body.setPivot(0.0f, bodyY, 0.0f)
        body.addCuboid(-2.5f, -2.75f, -2.5f, 5f, 5f, 5f)

        leftLeg = ModelPart(this, 4, 10)
        leftLeg.setPivot(1.25f, bodyY+1.25f, 0.0f)
        leftLeg.addCuboid(-0.5f, 0f, -1.0f, 1f, 3f, 1f)

        rightLeg = ModelPart(this, 0, 10)
        rightLeg.setPivot(-1.25f, bodyY+1.25f, 0.0f)
        rightLeg.addCuboid(-0.5f, 0f, -1.0f, 1f, 3f, 1f)
    }

    override fun setAngles(
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        customAngle: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        leftLeg.pitch = MathHelper.cos(limbAngle * 2f) * 1.4f * limbDistance
        rightLeg.pitch = MathHelper.cos(limbAngle * 2f + 3.1415927f) * 1.4f * limbDistance
    }

    override fun getParts(): MutableIterable<ModelPart> {
        return ImmutableList.of(
            body, leftLeg, rightLeg
        )
    }
}