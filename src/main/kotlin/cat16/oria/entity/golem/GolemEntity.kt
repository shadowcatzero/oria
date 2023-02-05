package cat16.oria.entity.golem

import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

open class GolemEntity(type: EntityType<GolemEntity>, world: World) : PathAwareEntity(type, world) {

    companion object: OriaEntityInfo {
        override val oriaName: String = "golem"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.3f, 0.4f)
        override val spawnGroup: SpawnGroup = SpawnGroup.CREATURE
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

}