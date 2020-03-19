package cat16.oria.item

import net.minecraft.item.Item

open class SimpleItem(settings: Settings?, override val oriaName: String) : Item(settings), OriaItem {
    constructor(oriaName: String) : this(Settings(), oriaName)
}
