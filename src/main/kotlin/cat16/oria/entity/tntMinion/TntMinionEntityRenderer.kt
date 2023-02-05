package cat16.oria.entity.tntMinion

import net.minecraft.client.render.entity.EntityRendererFactory
import software.bernie.geckolib.renderer.GeoEntityRenderer

class TntMinionEntityRenderer(ctx: EntityRendererFactory.Context) : GeoEntityRenderer<TntMinionEntity>(ctx,
    TntMinionEntityModel())
