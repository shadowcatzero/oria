package cat16.oria.entity.seaAngel

import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.GeoAnimatable
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.AnimationState
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil

class SeaAngelEntity(type: EntityType<SeaAngelEntity>, world: World) :
    PathAwareEntity(type, world), GeoEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    companion object : OriaEntityInfo {
        override val oriaName: String = "sea_angel"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.3f, 0.2f)
        override val spawnGroup: SpawnGroup = SpawnGroup.MONSTER
    }

    private fun <E : GeoAnimatable> predicate(event: AnimationState<E>) : PlayState {
        return PlayState.CONTINUE
    }

    override fun registerControllers(registrar: AnimatableManager.ControllerRegistrar) {
        registrar.add(AnimationController(this, "controller", 0, this::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache;
    }

}