package cat16.oria.entity.rat

import cat16.oria.Oria
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class RatEntityRenderer(entityRenderDispatcher: EntityRenderDispatcher) : MobEntityRenderer<RatEntity, RatEntityModel<RatEntity>>(entityRenderDispatcher,
    RatEntityModel(), 0.2f) {
    override fun getTexture(entity: RatEntity?): Identifier {
        return Oria.id("textures/entity/rat/rat.png")
    }

    override fun scale(entity: RatEntity?, matrixStack: MatrixStack?, f: Float) {
        matrixStack?.scale(0.5f, 0.5f, 0.5f)
    }
}
