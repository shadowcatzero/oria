package cat16.oria.item

import net.minecraft.item.Item

class StaffItem(settings: Settings?) : Item(settings), OriaItem {

    override val oriaName = "staff"

    constructor(): this(Settings().maxCount(1))
}
