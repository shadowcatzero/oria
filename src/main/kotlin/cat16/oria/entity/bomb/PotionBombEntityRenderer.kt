package cat16.oria.entity.bomb

import net.minecraft.client.render.entity.EntityRendererFactory
import software.bernie.geckolib.renderer.GeoEntityRenderer

class PotionBombEntityRenderer(ctx: EntityRendererFactory.Context) : GeoEntityRenderer<PotionBombEntity>(ctx, PotionBombEntityModel())
