package cat16.oria.item.tool

import cat16.oria.client.gui.SkillBookGui
import cat16.oria.client.screen.SkillBookScreen
import cat16.oria.item.OriaItem
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class SkillBookItem(settings: Settings?) : Item(settings), OriaItem {

    override val oriaName: String = "skill_book"

    constructor() : this(Settings().maxCount(1))

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        if(world!!.isClient) {
            MinecraftClient.getInstance().setScreen(SkillBookScreen(SkillBookGui()))
        }
        return super.use(world, user, hand)
    }
}