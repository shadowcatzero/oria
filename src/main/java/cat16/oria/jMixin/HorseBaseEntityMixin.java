package cat16.oria.jMixin;

import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.inventory.BasicInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseBaseEntity.class)
public class HorseBaseEntityMixin {
    @Shadow
    protected BasicInventory items = null;

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        cat16.oria.mixin.OriaHorseBaseEntity.INSTANCE.onTick((HorseBaseEntity)(Object)this, items);
    }
}