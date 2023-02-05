package cat16.oria.entity.tntMinion

import cat16.oria.entity.OriaEntities
import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.util.GeckoLibUtil

class TntMinionEntity(type: EntityType<TntMinionEntity>, world: World) : TameableEntity(type, world), GeoEntity {

    private val cache = GeckoLibUtil.createInstanceCache(this)

    companion object: OriaEntityInfo {
        override val oriaName: String = "tnt_minion"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.3f, 0.4f)
        override val spawnGroup: SpawnGroup = SpawnGroup.MISC
    }

    override fun createChild(world: ServerWorld?, mate: PassiveEntity?): PassiveEntity? {
        val child = OriaEntities.TNT_MINION.create(world)
        val uUID = this.ownerUuid
        if (uUID != null) {
            child!!.ownerUuid = uUID
            child.isTamed = true
        }

        return child
    }

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, EscapeDangerGoal(this, 0.5))
        goalSelector.add(2, WanderAroundFarGoal(this, 0.3))
        goalSelector.add(
            3, LookAtEntityGoal(
                this,
                PlayerEntity::class.java, 6.0f
            )
        )
        goalSelector.add(4, LookAroundGoal(this))
    }

    override fun registerControllers(p0: AnimatableManager.ControllerRegistrar?) {
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

}