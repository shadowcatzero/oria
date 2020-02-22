package cat16.oria.jMixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {
    @Accessor
    Map<UUID, Entity> getEntitiesByUuid();
}
