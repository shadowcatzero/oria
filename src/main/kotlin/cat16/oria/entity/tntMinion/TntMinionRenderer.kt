package cat16.oria.entity.tntMinion

import cat16.oria.Oria
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.util.Identifier

class TntMinionRenderer(entityRenderDispatcher: EntityRenderDispatcher) : MobEntityRenderer<TntMinionEntity, TntMinionEntityModel<TntMinionEntity>>(entityRenderDispatcher,
    TntMinionEntityModel(), 0.2f) {
    override fun getTexture(entity: TntMinionEntity?): Identifier {
        return Oria.id("textures/entity/tnt_minion/tnt_minion.png")
    }
}