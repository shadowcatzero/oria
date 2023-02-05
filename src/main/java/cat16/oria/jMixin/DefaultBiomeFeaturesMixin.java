package cat16.oria.jMixin;

import cat16.oria.entity.OriaEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "addFarmAnimals(Lnet/minecraft/world/biome/SpawnSettings$Builder;)V", at = @At("HEAD"), cancellable = true)
    private static void addFarmAnimals(SpawnSettings.Builder builder, CallbackInfo info) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(OriaEntities.INSTANCE.getRAT(), 50, 2, 5));
    }
}
