package cat16.oria.entity.bomb

import cat16.oria.Oria
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.util.Identifier

class BombEntityRenderer(entityRenderDispatcher: EntityRenderDispatcher) : EntityRenderer<BombEntity>(entityRenderDispatcher) {
    override fun getTexture(entity: BombEntity?): Identifier {
        return Oria.id("textures/entity/bomb/bomb.png")
    }
}