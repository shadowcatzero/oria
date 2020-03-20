package cat16.oria.entity.bomb

import cat16.oria.entity.OriaEntities
import cat16.oria.entity.OriaEntityInfo
import cat16.oria.item.component.FuseItem
import cat16.oria.network.EntityPacket
import cat16.oria.network.OriaPackets
import net.minecraft.entity.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Packet
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion

open class BombEntity(type: EntityType<BombEntity>, world: World) :
    Entity(type, world) {

    var rollDistance = 0f;

    constructor(world: World) : this(OriaEntities.BOMB, world)

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

    init {
        inanimate = true
    }

    companion object : OriaEntityInfo {
        override val oriaName: String = "bomb"
        override val dimensions: EntityDimensions = EntityDimensions.fixed(0.4f, 0.4f)
        override val category: EntityCategory = EntityCategory.MISC
    }

    var fuse: String? = null

    var ticks: Int = 0

    override fun tick() {

        if (!hasNoGravity()) {
            velocity = velocity.add(0.0, -0.07, 0.0)
        }

        if (onGround) {
            velocity = velocity.multiply(0.95, 1.0, 0.95)
        }

        move(MovementType.SELF, velocity)

        if(onGround) rollDistance += velocity.length().toFloat()
        if(velocity.length() != 0.0) yaw = (MathHelper.atan2(velocity.x, velocity.z) * 57.2957763671875).toFloat()

        ticks++

        if (FuseItem.getCondition(fuse).invoke(this)) {
            this.remove()
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

    private fun explode() {
        world.createExplosion(
            this, this.x, this.y, this.z,
            3f, Explosion.DestructionType.BREAK
        )
    }

    override fun collides(): Boolean {
        return true
    }

    override fun writeCustomDataToTag(tag: CompoundTag) {
        tag.putString("fuse", this.fuse)
        tag.putInt("ticks", this.ticks)
    }

    override fun readCustomDataFromTag(tag: CompoundTag) {
        this.fuse = tag.getString("fuse")
        this.ticks = tag.getInt("ticks")
    }

    override fun createSpawnPacket(): Packet<*>? {
        return EntityPacket.createSpawnPacket(this, OriaPackets.ENTITY_SPAWN_PACKET)
    }

    override fun initDataTracker() {

    }

}
