package cat16.oria.block

import net.minecraft.block.BlockState
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.particle.DustParticleEffect
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import org.joml.Vector3f
import java.util.*

class RainbowFlowerBlock : MagicFlowerBlock("rainbow_flower", StatusEffects.REGENERATION, 10) {
    override fun randomDisplayTick(state: BlockState?, world: World?, pos: BlockPos?, random: Random?) {
        val x = pos!!.x.toDouble() + 0.5 + (0.2 + random!!.nextFloat() * 0.3f) * if (random.nextBoolean()) 1 else -1
        val y = pos.y.toDouble() + 0.7 + random.nextFloat() * 0.2f
        val z = pos.z.toDouble() + 0.5 + (0.2 + random.nextFloat() * 0.3f) * if (random.nextBoolean()) 1 else -1
        if (random.nextInt(2) == 0) {
            world!!.addParticle(
                DustParticleEffect(Vector3f(255f, 255f, 255f), 1f), x, y, z,
                random.nextDouble() * 0.005,
                -5.0,
                random.nextDouble() * 0.005
            )
        }
    }
}