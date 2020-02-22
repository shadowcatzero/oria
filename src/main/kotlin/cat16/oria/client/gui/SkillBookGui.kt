package cat16.oria.client.gui

import io.github.cottonmc.cotton.gui.client.BackgroundPainter
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WButton
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.WSprite
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier

class SkillBookGui : LightweightGuiDescription() {

    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(256, 240)
        val icon = WSprite(Identifier("minecraft:textures/item/redstone.png"))
        root.add(icon, 0, 2, 1, 1)
        val button = WButton(LiteralText("TestB"))
        root.add(button, 0, 3, 4, 1)
        val label = WLabel(LiteralText("Test"), 0xFFFFFF)
        root.add(label, 0, 4, 2, 1)
        root.validate(this)
    }

    override fun addPainters() {
        getRootPanel().backgroundPainter = BackgroundPainter.VANILLA //This is done automatically though
    }
}