package cat16.oria.entity.rat

import cat16.oria.entity.OriaEntities
import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.GeoAnimatable
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.AnimationState
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil


class RatEntity(type: EntityType<RatEntity>, world: World) : AnimalEntity(type, world), GeoEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    companion object : OriaEntityInfo {
        override val oriaName: String = "rat"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.3f, 0.2f)
        override val spawnGroup: SpawnGroup = SpawnGroup.CREATURE

        fun createRatAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
        }
    }

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, MeleeAttackGoal(this, 0.7, true))
        goalSelector.add(2, EscapeDangerGoal(this, 0.7))
        goalSelector.add(3, WanderAroundFarGoal(this, 0.4))
        goalSelector.add(4, LookAtEntityGoal(this, PlayerEntity::class.java, 6.0f))
        goalSelector.add(5, LookAroundGoal(this))

        targetSelector.add(1, RevengeGoal(this, *arrayOfNulls(0)).setGroupRevenge())
    }

    override fun canAvoidTraps(): Boolean = true

    override fun createChild(world: ServerWorld?, mate: PassiveEntity?): PassiveEntity? = OriaEntities.RAT.create(world)

    override fun getDeathSound(): SoundEvent = SoundEvents.ENTITY_BAT_DEATH

    override fun getAmbientSound(): SoundEvent = SoundEvents.ENTITY_BAT_AMBIENT

    override fun getHurtSound(source: DamageSource?): SoundEvent = SoundEvents.ENTITY_BAT_HURT

    private fun <E : GeoAnimatable> predicate(event: AnimationState<E>) : PlayState {
        return PlayState.CONTINUE
    }
    override fun registerControllers(registrar: AnimatableManager.ControllerRegistrar) {
        registrar.add(AnimationController(this, "controller", 0, this::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }
}
