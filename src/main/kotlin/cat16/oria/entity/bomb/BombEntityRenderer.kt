package cat16.oria.entity.bomb

import cat16.oria.Oria
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.Identifier

class BombEntityRenderer(entityRenderDispatcher: EntityRenderDispatcher) : EntityRenderer<BombEntity>(entityRenderDispatcher) {
    override fun getTexture(entity: BombEntity?): Identifier {
        return Oria.id("textures/entity/bomb/bomb.png")
    }

    val model = BombEntityModel<BombEntity>()

    override fun render(
        entity: BombEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        matrices.push()
        val vertexConsumer: VertexConsumer =
            vertexConsumers.getBuffer(model.getLayer(getTexture(entity)))
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.yaw))
        model.setAngles(entity, 0f, 0f, 0f, entity.yaw, entity.rollDistance * 2 + entity.pitch)
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f)
        matrices.pop()
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }
}