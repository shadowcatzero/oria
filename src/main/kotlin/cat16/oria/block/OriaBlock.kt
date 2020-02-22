package cat16.oria.block

import cat16.oria.util.OriaObjectInfo
import net.minecraft.item.Item
import net.minecraft.text.TranslatableText

interface OriaBlock : OriaObjectInfo {
    override val typeName: String
        get() = "block"

    val itemSettings: Item.Settings?

    fun tooltip(name: String, vararg args: Any) = TranslatableText(key("tooltip.$name"), *args)
}
