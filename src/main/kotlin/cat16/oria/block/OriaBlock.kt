package cat16.oria.block

import cat16.oria.util.OriaObjectInfo
import net.minecraft.item.Item
import net.minecraft.text.Text

interface OriaBlock : OriaObjectInfo {
    override val typeName: String
        get() = "block"

    val itemSettings: Item.Settings?

    fun tooltip(name: String, vararg args: Any) = Text.translatable(key("tooltip.$name"), *args)
    
    fun clientInit() {}
}
