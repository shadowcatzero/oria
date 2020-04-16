package cat16.oria.jMixin;

import cat16.oria.item.tool.SpatialOrbItem;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
class MobEntityMixin {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.getItem() instanceof SpatialOrbItem) {
            cir.setReturnValue(false);
        }
    }
}
