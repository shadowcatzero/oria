package cat16.oria.item

import cat16.oria.util.OriaObjectInfo
import net.minecraft.text.Text

interface OriaItem : OriaObjectInfo {
    override val typeName: String
        get() = "item"

    fun tooltip(name: String, vararg args: Any) = Text.translatable(key("tooltip.$name"), *args)
}
