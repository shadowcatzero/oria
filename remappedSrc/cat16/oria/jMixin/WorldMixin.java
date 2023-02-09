package cat16.oria.jMixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(World.class)
class WorldMixin {
    //@Inject(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/BlockEntityTickInvoker;tick()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    //public void onTick(CallbackInfo ci, Profiler profiler, Iterator<?> it, BlockEntity blockEntity) {
    //    cat16.oria.mixin.OriaWorld.INSTANCE.onTick(blockEntity);
    //}
}
