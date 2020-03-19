package cat16.oria.entity.tntMinion

import cat16.oria.entity.OriaEntities
import cat16.oria.entity.OriaEntityInfo
import net.minecraft.entity.EntityCategory
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

open class TntMinionEntity(type: EntityType<TntMinionEntity>, world: World) : TameableEntity(type, world) {

    companion object: OriaEntityInfo {
        override val oriaName: String = "tnt_minion"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.3f, 0.4f)
        override val category: EntityCategory = EntityCategory.MISC
    }

    override fun createChild(mate: PassiveEntity?): PassiveEntity? {

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

}