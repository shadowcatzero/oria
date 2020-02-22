package cat16.oria

import cat16.oria.block.OriaBlocks
import cat16.oria.component.OriaComponents
import cat16.oria.entity.OriaEntities
import cat16.oria.item.OriaItems
import cat16.oria.network.OriaPackets
import cat16.oria.world.OriaBiomeHandler
import net.fabricmc.api.ModInitializer
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

object Oria : ModInitializer {
    private const val MOD_ID = "oria"

    override fun onInitialize() {
        OriaItems.init()
        OriaBlocks.init()
        OriaEntities.init()
        OriaItemGroups.init()
        OriaBiomeHandler.init()
        OriaPackets.clientInit()
        OriaComponents.init()
    }

    fun id(name: String): Identifier {
        return Identifier(MOD_ID, name)
    }

    fun key(type: String, name: String): String {
        return "$type.$MOD_ID.$name"
    }

    fun text(type: String, name: String, vararg args: Any): TranslatableText {
        return TranslatableText(key(type, name), args)
    }
}