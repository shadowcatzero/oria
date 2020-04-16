package cat16.oria.jMixin;

import cat16.oria.entity.OriaEntities;
import cat16.oria.entity.rat.RatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin {

    @Final
    @Shadow
    @Mutable
    private static Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY;

    static {
        DEFAULT_ATTRIBUTE_REGISTRY = new HashMap<>(DEFAULT_ATTRIBUTE_REGISTRY);
        DEFAULT_ATTRIBUTE_REGISTRY.put(OriaEntities.INSTANCE.getRAT(), RatEntity.Companion.createRatAttributes().build());
    }

}
