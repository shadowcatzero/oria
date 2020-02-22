package cat16.oria.item

import cat16.oria.util.OriaObjectInfo
import net.minecraft.text.TranslatableText

interface OriaItem : OriaObjectInfo {
    override val typeName: String
        get() = "item"

    fun tooltip(name: String, vararg args: Any) = TranslatableText(key("tooltip.$name"), *args)
}
