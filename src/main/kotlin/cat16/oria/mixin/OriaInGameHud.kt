package cat16.oria.mixin

import cat16.oria.Oria
import cat16.oria.component.OriaComponents
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper

object OriaInGameHud {

    val MANA_BAR_BASE_LOCATION = Oria.id("textures/gui/mana_bar_base.png")
    val MANA_BAR_FILL_LOCATION = Oria.id("textures/gui/mana_bar_fill.png")

    val HOTBAR_WIDTH = 182

    interface Interface {
        val client: MinecraftClient
        val scaledWidth: Int
        val scaledHeight: Int
    }

    fun onRender(hud: Interface) {
        RenderSystem.disableDepthTest()
        RenderSystem.disableAlphaTest()

        val manaManager = OriaComponents.MAGIC.get(hud.client.player!!).manaManager

        val padding = 5;

        val width = hud.scaledWidth / 2 - HOTBAR_WIDTH / 2 - padding * 2
        val height = width / 16
        val x = padding
        val y = hud.scaledHeight - height - padding

        val manaWidth = (manaManager.mana / manaManager.maxMana) * width

        hud.client.textureManager.bindTexture(MANA_BAR_BASE_LOCATION)
        DrawableHelper.blit(x, y, 0F, 0F, width, height, width, height)
        hud.client.textureManager.bindTexture(MANA_BAR_FILL_LOCATION)
        DrawableHelper.blit(x, y, 0F, 0F, manaWidth.toInt(), height, width, height)

        RenderSystem.enableAlphaTest()
        RenderSystem.enableDepthTest()
    }
}
