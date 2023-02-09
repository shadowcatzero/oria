package cat16.oria.jMixin;

import cat16.oria.mixin.OriaInGameHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
class InGameHudMixin implements OriaInGameHud.Interface {

    @Shadow @Final private MinecraftClient client;

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    //@Inject(method = "render", at = @At("RETURN"))
    //public void onRender(MatrixStack matrices, CallbackInfo ci) {
    //    cat16.oria.mixin.OriaInGameHud.INSTANCE.onRender(this, matrices);
    //}

    @NotNull
    @Override
    public MinecraftClient getClient() {
        return this.client;
    }

    @Override
    public int getScaledWidth() {
        return this.scaledWidth;
    }

    @Override
    public int getScaledHeight() {
        return this.scaledHeight;
    }
}
