package net.ccbluex.liquidbounce.ui.client.hud.element.elements;


import net.ccbluex.liquidbounce.features.module.modules.hyt.Kills
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts

import net.ccbluex.liquidbounce.utils.render.RenderUtils

import java.awt.Color
import java.util.*

@ElementInfo(name = "KillNotifcations")
class KillNotifcations(x:Double = 101.33, y: Double = 213.22) : Element(x = x, y = y, scale = 1.15F){


    override fun drawElement(): Border? {

        if (Kills.Iskill){

            RenderUtils.drawRect(
            0f, 0f, 110f, 15f, Color(
                230, 230, 250,
                140
            ).rgb)

                    RenderUtils.drawShadow(0f, 0f, 110f, 15f)
            Fonts.font35.drawString("You Just Killed a Enemy !",3.5f,6f,Color.DARK_GRAY.rgb)

            RenderUtils.drawFilledCircle(0,0,10f,Color(
                230, 230, 250,
                140
            ))
        }
updateElement()






        return Border(1F, 0F, x.toFloat(), y.toFloat())
}



}



