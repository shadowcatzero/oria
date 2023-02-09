package cat16.oria.entity.bomb

import cat16.oria.entity.OriaEntities
import cat16.oria.entity.OriaEntityInfo
import cat16.oria.item.component.FuseItem
import cat16.oria.network.EntityPacket
import cat16.oria.network.OriaPackets
import net.minecraft.entity.*
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.particle.ParticleTypes
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.util.TypeFilter
import net.minecraft.util.math.MathHelper
import net.minecraft.world.GameRules
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class PotionBombEntity(type: EntityType<PotionBombEntity>, world: World) :
    Entity(type, world), GeoEntity {

    private var rollDistance = 0f;
    var fuse: String? = null
    var ticks: Int = 0

    private val cache = GeckoLibUtil.createInstanceCache(this)

    constructor(world: World) : this(OriaEntities.BOMB, world)

    init {
        // TODO: inanimate = true
    }

    companion object : OriaEntityInfo {
        override val oriaName: String = "potion_bomb"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.4f, 0.4f)
        override val spawnGroup: SpawnGroup = SpawnGroup.MISC
    }

    fun launchFrom(
        user: Entity,
        pitch: Float,
        yaw: Float,
        roll: Float,
        speed: Double
    ) {
        // I have no idea how this works
        val magicNum = 0.017453292f
        val f = -MathHelper.sin(yaw * magicNum) * MathHelper.cos(pitch * magicNum)
        val g = -MathHelper.sin((pitch + roll) * magicNum)
        val h = MathHelper.cos(yaw * magicNum) * MathHelper.cos(pitch * magicNum)
        this.setVelocity(f.toDouble(), g.toDouble(), h.toDouble())
        val vec3d = user.velocity
        velocity = velocity.add(vec3d.x, if (user.isOnGround) 0.0 else vec3d.y, vec3d.z)
        velocity.multiply(speed)
        this.yaw = yaw
        this.pitch = pitch
    }

    override fun tick() {

        tickMovement()

        if(onGround) rollDistance += velocity.length().toFloat()
        if(velocity.length() != 0.0) yaw = (MathHelper.atan2(velocity.x, velocity.z) * 57.2957763671875).toFloat()

        if(fuse != null) ticks++

        if (FuseItem.getCondition(fuse).invoke(this)) {
            this.remove(RemovalReason.KILLED)
            if (!world.isClient) {
                this.explode()
            }
        } else {
            updateWaterState()
            if (world.isClient) {
                world.addParticle(ParticleTypes.SMOKE, this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0)
            }
        }
    }

    private fun tickMovement() {

        if (!hasNoGravity()) {
            velocity = velocity.add(0.0, -0.07, 0.0)
        }

        if (onGround) {
            velocity = velocity.multiply(0.95, 1.0, 0.95)
        }

        /*
        var xVel = velocity.x
        var yVel = velocity.y
        var zVel = velocity.z
        if (xVel < 0.003) {
            xVel = 0.0
        }

        if (yVel < 0.003) {
            yVel = 0.0
        }

        if (zVel < 0.003) {
            zVel = 0.0
        }
        setVelocity(xVel, yVel, zVel)
         */

        tickCramming()

        move(MovementType.SELF, velocity)
    }

    private fun tickCramming() {
        if (world.isClient()) {
            world.getEntitiesByType(
                TypeFilter.instanceOf(
                    PlayerEntity::class.java
                ),
                boundingBox, EntityPredicates.canBePushedBy(this)
            ).forEach(Consumer { entity: PlayerEntity? ->
                pushAway(
                    entity!!
                )
            })
        } else {
            val list = world.getOtherEntities(this, boundingBox, EntityPredicates.canBePushedBy(this))
            if (list.isNotEmpty()) {
                val i = world.gameRules.getInt(GameRules.MAX_ENTITY_CRAMMING)
                var j: Int
                if (i > 0 && list.size > i - 1 && random.nextInt(4) == 0) {
                    j = 0
                    for (k in list.indices) {
                        if (!list[k].hasVehicle()) {
                            ++j
                        }
                    }
                    if (j > i - 1) {
                        damage(DamageSource.CRAMMING, 6.0f)
                    }
                }
                j = 0
                while (j < list.size) {
                    pushAway(list[j])
                    ++j
                }
            }
        }
    }

    private fun pushAway(entity: Entity) {
        entity.pushAwayFrom(this)
    }

    override fun isPushable() = true

    override fun damage(source: DamageSource?, amount: Float): Boolean {
        if(!this.isRemoved) {
            this.remove(RemovalReason.KILLED)
            if (!world.isClient) {
                this.explode()
            }
        }
        return true
    }

    private fun explode() {
        world.createExplosion(
            this, this.x, this.y, this.z,
            3f, World.ExplosionSourceType.TNT
        )
    }

    override fun collidesWith(other: Entity?): Boolean {
        return true
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        fuse?.let { nbt.putString("fuse", it) }
        nbt.putInt("ticks", this.ticks)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        this.fuse = nbt.getString("fuse")
        this.ticks = nbt.getInt("ticks")
    }

    override fun createSpawnPacket(): Packet<ClientPlayPacketListener> {
        return EntityPacket.createSpawnPacket(this, OriaPackets.ENTITY_SPAWN_PACKET)
    }

    override fun initDataTracker() {

    }

    override fun registerControllers(p0: AnimatableManager.ControllerRegistrar?) {
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

}
