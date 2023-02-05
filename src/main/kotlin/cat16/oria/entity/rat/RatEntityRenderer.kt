package cat16.oria.entity.rat

import net.minecraft.client.render.entity.EntityRendererFactory
import software.bernie.geckolib.renderer.GeoEntityRenderer

class RatEntityRenderer(ctx: EntityRendererFactory.Context) : GeoEntityRenderer<RatEntity>(ctx,
    RatEntityModel())
