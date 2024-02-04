package net.ccbluex.liquidbounce.ui.client.hud.element.elements;


import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.minecraft.client.gui.FontRenderer


import java.awt.Color
import java.awt.Font
import java.awt.font.FontRenderContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.hypot
import kotlin.math.roundToInt

@ElementInfo(name = "Logo")
class Logo(x:Double = 4.5, y: Double = 4.5) : Element(x = x, y = y){


        override fun drawElement(): Border? {

              RenderUtils.drawRect(0f,0f,168f,16f,Color(230 ,230 ,250,
                  140).rgb)
            Fonts.font35.drawString("Oxygen Client | Oxygen.cc | Editor Version",3f,6f,Color.DARK_GRAY.rgb)
            RenderUtils.drawShadow(0f,0f,168f,16f)
                return Border(1F, 0F, x.toFloat(), y.toFloat())
        }





}








