package cat16.oria.jMixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
class HorseBaseEntityMixin {
    @Shadow
    protected Inventory items = null;

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        cat16.oria.mixin.OriaHorseBaseEntity.INSTANCE.onTick((AbstractHorseEntity)(Object)this, items);
    }
}