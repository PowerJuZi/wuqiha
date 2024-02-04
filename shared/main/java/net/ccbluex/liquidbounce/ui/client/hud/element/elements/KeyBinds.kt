package net.ccbluex.liquidbounce.ui.client.hud.element.elements



import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts

import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.renderer.GlStateManager

import org.lwjgl.opengl.GL11
import java.awt.Color


@ElementInfo(name = "KeyBinds")
class KeyBinds(x:Double = 4.14, y:Double =117.72) : Element(x = x, y = y, scale = 0.82f) {

    private var anmitY = 0F
    override fun drawElement(): Border? {

        var y2 = 0
        anmitY = RenderUtils.getAnimationState(anmitY,(getmoduley()).toFloat(), 350.0F)


    RenderUtils.drawShadow(0f,y2-2f,144f,anmitY)
        RenderUtils.drawRect(0f,y2-2f,144f,anmitY,Color(230 ,230 ,250,
            140).rgb)



        //draw Title
        var fwidth = 10F

        //draw Module Bind
        for (module in LiquidBounce.moduleManager.modules) {
            if (module.keyBind == 0) continue


            Fonts.font35.drawString(module.name, fwidth, y2+4f, if (module.state) Color(0, 0, 0).rgb else Color(90,90,90).rgb, false)
            Fonts.font35.drawString(
                if (module.state) "[True]" else "[False]",
                (144 - Fonts.font35.getStringWidth(if (module.state) "[Toggle]" else "[Toggle]") -10).toFloat(),
                y2+4.5F,
                if (module.state) Color(0, 0, 0).rgb else Color(90,90,90).rgb,
                false
            )
            y2 += 12
        }

        return Border(0f, 0f, 114f, (17 + getmoduley()).toFloat())
    }

    fun getmoduley(): Int {
        var y = 0
        for (module in LiquidBounce.moduleManager.modules) {
            if (module.keyBind == 0) continue

            y += 13
        }
        return y
    }
}