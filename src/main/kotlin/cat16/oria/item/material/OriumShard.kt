package cat16.oria.item.material

import cat16.oria.item.OriaItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class OriumShard(settings: Settings?) : Item(settings), OriaItem {

    constructor() : this(Settings())

    override val oriaName = "orium_shard"

    override fun hasEnchantmentGlint(stack: ItemStack): Boolean {
        return true
    }
}