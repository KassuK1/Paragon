package com.paragon.client.ui.configuration.simple.element

import com.paragon.api.module.Module
import com.paragon.api.setting.Bind
import com.paragon.api.setting.Setting
import com.paragon.api.util.render.ColourUtil
import com.paragon.api.util.render.RenderUtil
import com.paragon.api.util.render.font.FontUtil
import com.paragon.client.systems.module.impl.client.Colours
import com.paragon.client.ui.configuration.shared.Panel
import com.paragon.client.ui.configuration.shared.RawElement
import com.paragon.client.ui.configuration.simple.element.setting.SettingElement
import com.paragon.client.ui.configuration.simple.element.setting.impl.*
import com.paragon.client.ui.util.Click
import org.lwjgl.opengl.GL11.glScalef
import java.awt.Color

/**
 * @author Surge
 * @since 31/07/2022
 */
class ModuleElement(val module: Module, val panel: Panel, x: Float, y: Float, width: Float, height: Float) : RawElement(x, y, width, height) {

    val settings: ArrayList<SettingElement<*>> = ArrayList()
    private var expanded = false

    init {
        module.settings.forEach {
            when (it.value) {
                is Boolean -> settings.add(BooleanElement(it as Setting<Boolean>, x + 2, y, width - 4, height))
                is Enum<*> -> settings.add(EnumElement(it as Setting<Enum<*>>, x + 2, y, width - 4, height))
                is Number -> settings.add(SliderElement(it as Setting<Number>, x + 2, y, width - 4, height))
                is Color -> settings.add(ColourElement(it as Setting<Color>, x + 2, y, width - 4, height))
                is Bind -> settings.add(BindElement(it as Setting<Bind>, x + 2, y, width - 4, height))
                is String -> settings.add(StringElement(it as Setting<String>, x + 2, y, width - 4, height))
            }
        }
    }

    override fun draw(mouseX: Float, mouseY: Float, mouseDelta: Int) {
        val hovered = isHovered(mouseX, mouseY)

        if (hovered && !module.isEnabled) {
            RenderUtil.drawRect(x, y, width, height, Color(200, 200, 200, 150).rgb)
        }

        if (module.isEnabled) {
            RenderUtil.drawRect(x, y, width, height, ColourUtil.integrateAlpha(Colours.mainColour.value, if (hovered) 190f else 150f).rgb)
        }

        glScalef(0.85f, 0.85f, 0.85f).let {
            val factor = 1 / 0.85f

            FontUtil.drawStringWithShadow(module.name, (x + 4) * factor, (y + 4) * factor, -1)

            glScalef(factor, factor, factor)
        }

        if (expanded) {
            var y = y + height + 1.5f

            settings.forEach {
                if (it.setting.isVisible()) {
                    it.x = x + 2
                    it.y = y

                    it.draw(mouseX, mouseY, mouseDelta)

                    y += it.getAbsoluteHeight() + 0.5f
                }
            }
        }
    }

    override fun mouseClicked(mouseX: Float, mouseY: Float, click: Click) {
        super.mouseClicked(mouseX, mouseY, click)

        if (isHovered(mouseX, mouseY)) {
            if (click == Click.LEFT) {
                module.toggle()
            } else if (click == Click.RIGHT) {
                expanded = !expanded
            }
        }

        if (expanded) {
            settings.forEach {
                if (it.setting.isVisible()) {
                    it.mouseClicked(mouseX, mouseY, click)
                }
            }
        }
    }

    override fun mouseReleased(mouseX: Float, mouseY: Float, click: Click) {
        super.mouseReleased(mouseX, mouseY, click)

        if (expanded) {
            settings.forEach {
                if (it.setting.isVisible()) {
                    it.mouseReleased(mouseX, mouseY, click)
                }
            }
        }
    }

    override fun keyTyped(character: Char, keyCode: Int) {
        super.keyTyped(character, keyCode)

        if (expanded) {
            settings.forEach {
                if (it.setting.isVisible()) {
                    it.keyTyped(character, keyCode)
                }
            }
        }
    }

    fun getSettingHeight(): Float {
        var height = 1.5f

        settings.forEach {
            if (it.setting.isVisible()) {
                height += it.getAbsoluteHeight() + 0.5f
            }
        }

        return height
    }

    fun getAbsoluteHeight(): Float {
        return height + if (expanded) getSettingHeight() else 0f
    }

}