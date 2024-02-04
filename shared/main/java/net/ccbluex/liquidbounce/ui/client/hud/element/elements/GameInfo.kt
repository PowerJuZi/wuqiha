package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import net.ccbluex.liquidbounce.features.module.modules.hyt.AutoL.Companion.DeathNum
import net.ccbluex.liquidbounce.features.module.modules.hyt.Kills
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*


@ElementInfo(name = "GameInfo")
class GameInfo(x:Double = 4.0, y: Double = 25.0) : Element(x = x, y = y){




        override fun drawElement(): Border? {


                var date: Date = Date()
                var formatter: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")



                updateElement()
            RenderUtils.drawImage(ResourceLocation("liquidbounce/png/gameinfo.png"), 0, 118, 68,Color(230 ,230 ,250,
                140).rgb)


            RenderUtils.drawShadow(0f,0f,118f,68f)

                Fonts.font35.drawString("Statistics",38,4, Color.DARK_GRAY.rgb)
            RenderUtils.drawRect(5f,11.35f,112f,11.9f,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString("Kills:",20,15,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString(Kills.kill.toString(),49,15,Color.DARK_GRAY.rgb)
                RenderUtils.drawImage(ResourceLocation("liquidbounce/png/sword.png"),4,12,10,10)
                Fonts.font35.drawString("Death:",20,25,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString(DeathNum.toString(),49,25,Color.DARK_GRAY.rgb)
            RenderUtils.drawImage(ResourceLocation("liquidbounce/png/touxin.png"),4,22,10,10)
                Fonts.font35.drawString("Time:",20,35,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString(formatter.format(date).toString(),49,35,Color.DARK_GRAY.rgb)
            RenderUtils.drawImage(ResourceLocation("liquidbounce/png/time.png"),4,32,10,10)

                Fonts.font35.drawString("Player:",20,45,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString(mc.thePlayer!!.name.toString(),49,45,Color.DARK_GRAY.rgb)
            RenderUtils.drawImage(ResourceLocation("liquidbounce/png/head.png"),4,42,10,10)
                Fonts.font35.drawString("FPS:",20,55,Color.DARK_GRAY.rgb)
                Fonts.font35.drawString(Minecraft.getDebugFPS().toString(),49,55,Color.DARK_GRAY.rgb)
            RenderUtils.drawImage(ResourceLocation("liquidbounce/png/set.png"),4,52,10,10)




                return Border(1F, 0F, x.toFloat(), y.toFloat())
        }






}








