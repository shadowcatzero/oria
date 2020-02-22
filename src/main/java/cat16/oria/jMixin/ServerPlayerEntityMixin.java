package cat16.oria.jMixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
class ServerPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        cat16.oria.mixin.OriaServerPlayerEntity.INSTANCE.onTick((ServerPlayerEntity)(Object)this);
    }


}