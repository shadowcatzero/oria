package cat16.oria.item

import net.minecraft.item.Item

open class SimpleItem(override val oriaName: String, settings: Settings?) : Item(settings), OriaItem {
    constructor(oriaName: String) : this(oriaName, Settings())
}
