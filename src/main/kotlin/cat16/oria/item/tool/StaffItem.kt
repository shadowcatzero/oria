package cat16.oria.item.tool

import cat16.oria.item.OriaItem
import net.minecraft.item.Item

class StaffItem(settings: Settings?) : Item(settings), OriaItem {

    override val oriaName = "staff"

    constructor(): this(Settings().maxCount(1))
}
