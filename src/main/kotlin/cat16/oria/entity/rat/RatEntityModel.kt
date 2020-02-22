package cat16.oria.entity.rat

//Made with Blockbench
//Paste this code into your mod.
import com.google.common.collect.ImmutableList
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.CompositeEntityModel
import net.minecraft.util.math.MathHelper

class RatEntityModel<T> : CompositeEntityModel<T>() where T : RatEntity {
    private val body: ModelPart
    private val tail: ModelPart
    private val head: ModelPart

    init {

        textureWidth = 64
        textureHeight = 64

        // head

        head = ModelPart(this, 0, 16)
        head.setPivot(0.0F, 22.0F, -6.0F)
        head.addCuboid(-2.0F, -2.0F, -5.0F, 4f, 4f, 5f)

        val leftEar = ModelPart(this, 0, 0)
        leftEar.addCuboid(-5.0F, -5.0F, -1.0F, 4f, 4f, 0f)
        head.addChild(leftEar)

        val rightEar = ModelPart(this, 0, 4)
        rightEar.addCuboid(1.0F, -5.0F, -1.0F, 4f, 4f, 0f)
        head.addChild(rightEar)

        val nose = ModelPart(this, 0, 16)
        nose.addCuboid(-0.5F, -0.5F, -6.0F, 1f, 1f, 1f)
        head.addChild(nose)

        // body

        body = ModelPart(this, 0, 0)
        body.setPivot(0.0F, 24.0F, 0.0F)
        body.addCuboid(-4.0F, -8.0F, 0.0F, 8f, 8f, 8f)

        val bodyEnd = ModelPart(this, 0, 32)
        bodyEnd.addCuboid(-3.0F, -6.0F, -6.0F, 6f, 6f, 6f)
        body.addChild(bodyEnd)

        // tail

        tail = ModelPart(this, 4, 16)
        tail.setPivot(-1F, 20F, 7F)
        tail.addCuboid(0F, 0F, 0F, 2f, 2f, 14f)
    }

    override fun setAngles(
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        customAngle: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        head.yaw = headYaw * 0.017453292f
        head.pitch = headPitch * 0.017453292f
        tail.yaw = MathHelper.cos(limbAngle) * 0.5f * limbDistance
        tail.pitch = -(Math.PI/24).toFloat()
    }

    override fun getParts(): MutableIterable<ModelPart> {
        return ImmutableList.of(
            head, body, tail
        )
    }
}