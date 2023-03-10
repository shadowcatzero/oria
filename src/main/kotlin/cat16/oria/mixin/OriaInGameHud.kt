package cat16.oria.mixin

import cat16.oria.Oria
import cat16.oria.component.OriaComponents
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack

object OriaInGameHud {

    private val MANA_BAR_BASE_LOCATION = Oria.id("textures/gui/mana_bar_base.png")
    private val MANA_BAR_FILL_LOCATION = Oria.id("textures/gui/mana_bar_fill.png")

    private const val HOTBAR_WIDTH = 182

    interface Interface {
        val client: MinecraftClient
        val scaledWidth: Int
        val scaledHeight: Int
    }

    fun onRender(hud: Interface, matrices: MatrixStack) {
        RenderSystem.disableDepthTest()

        val manaManager = OriaComponents.MAGIC.get(hud.client.player!!).manaManager

        val padding = 5;

        val width = hud.scaledWidth / 2 - HOTBAR_WIDTH / 2 - padding * 2
        val height = width / 16
        val x = padding
        val y = hud.scaledHeight - height - padding

        val manaWidth = (manaManager.mana / manaManager.maxMana) * width

        hud.client.textureManager.bindTexture(MANA_BAR_BASE_LOCATION)
        DrawableHelper.drawTexture(matrices, x, y, 0F, 0F, width, height, width, height)
        hud.client.textureManager.bindTexture(MANA_BAR_FILL_LOCATION)
        DrawableHelper.drawTexture(matrices, x, y, 0F, 0F, manaWidth.toInt(), height, width, height)

        RenderSystem.enableDepthTest()
    }
}
