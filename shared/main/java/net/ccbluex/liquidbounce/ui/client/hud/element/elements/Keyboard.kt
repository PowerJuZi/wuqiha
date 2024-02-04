package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.Side
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.FontValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard
import java.awt.Color

@ElementInfo(name = "KeyStrokes")
class Keyboard : Element(3.4,139.4,1.25F, Side.default()) {
    private val keys=ArrayList<KeyStroke>()

    private val backGroundRedValue = 230
    private val backGroundGreenValue = 230
    private val backGroundBlueValue = 250
    private val backGroundAlphaValue = 120
    private val textRedValue = 0
    private val textGreenValue = 0
    private val textBlueValue = 0
    private val textAlphaValue = 160
    private val highLightPercent = FloatValue("HighLightPercent",0.5F,0F,1F)
    private val animSpeedValue = IntegerValue("AnimationSpeed", 300, 0, 700)
    private val outline = BoolValue("Outline", false)
    private val outlineBoldValue = IntegerValue("OutlineBold", 1,0,5)
    private val outlineRainbow = BoolValue("OutLineRainbow", false)
    private val fontValue = FontValue("Font", Fonts.font35)

    init {
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindForward,16,0,15,15).initKeyName())
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindLeft,0,16,15,15).initKeyName())
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindBack,16,16,15,15).initKeyName())
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindRight,32,16,15,15).initKeyName())
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindAttack,0,32,23,15).initKeyName("L"))
        keys.add(KeyStroke(Minecraft.getMinecraft().gameSettings.keyBindUseItem,24,32,23,15).initKeyName("R"))
    }



    override fun drawElement(): Border? {
        val backGroundColor=Color(backGroundRedValue,backGroundGreenValue,backGroundBlueValue,backGroundAlphaValue)
        val textColor=if(outlineRainbow.get()){
            ColorUtils.rainbow(textAlphaValue)
        }else{
            Color(textRedValue,textGreenValue,textBlueValue,textAlphaValue)
        }

        for(keyStroke in keys){
            keyStroke.render(animSpeedValue.get(), backGroundColor, textColor, highLightPercent.get(), outline.get(), outlineBoldValue.get(),fontValue.get()as FontRenderer)
        }

        return Border(0F,0F,47F,47F)
    }
}

class KeyStroke(val key:KeyBinding,val posX:Int,val posY:Int, val width:Int, val height:Int){
    var keyName="KEY"

    private var lastClick=false
    private val animations=ArrayList<Long>()

    fun render(speed: Int, bgColor: Color, textColor: Color, highLightPct: Float, outline: Boolean, outlineBold: Int, font: FontRenderer){
        val highLightColor=Color(255-((255-bgColor.red)*highLightPct).toInt(),255-((255-bgColor.blue)*highLightPct).toInt(),255-((255-bgColor.green)*highLightPct).toInt())
        val clickAlpha=255-(255-bgColor.alpha)*highLightPct
        val centerX=posX+(width/2)
        val centerY=posY+(height/2)
        val nowTime=System.currentTimeMillis()

        val rectColor=if(lastClick&&animations.isEmpty()){ ColorUtils.reAlpha(highLightColor,clickAlpha.toInt()) }else{ bgColor }
        RenderUtils.drawRect(posX.toFloat(),posY.toFloat(),(posX+width).toFloat(),(posY+height).toFloat()
            ,rectColor)

        val removeAble=ArrayList<Long>()
        for(time in animations){
            val pct=(nowTime-time)/(speed.toFloat())
            if(pct>1){
                removeAble.add(time)
                continue
            }
            RenderUtils.drawLimitedCircle(posX.toFloat(),posY.toFloat(),(posX+width).toFloat(),(posY+height).toFloat(),
                centerX,centerY,(width*0.7F)*pct
                ,Color(255-((255-highLightColor.red)*pct).toInt(),255-((255-highLightColor.green)*pct).toInt(),255-((255-highLightColor.blue)*pct).toInt(),255-((255-clickAlpha)*pct).toInt()))
        }
        for(time in removeAble){
            animations.remove(time)
        }
        if(!lastClick && key.isKeyDown){
            animations.add(nowTime)
        }
        lastClick=key.isKeyDown

        font.drawString(keyName,centerX-(font.getStringWidth(keyName)/2),centerY-(font.FONT_HEIGHT/2)
            ,textColor.rgb)
        if(outline){
            RenderUtils.drawRect(posX.toFloat(),posY.toFloat(),(posX+outlineBold).toFloat(),(posY+height).toFloat(),textColor.rgb)
            RenderUtils.drawRect((posX+width-outlineBold).toFloat(),posY.toFloat(),(posX+width).toFloat(),(posY+height).toFloat(),textColor.rgb)
            RenderUtils.drawRect((posX+outlineBold).toFloat(),posY.toFloat(),(posX+width-outlineBold).toFloat(),(posY+outlineBold).toFloat(),textColor.rgb)
            RenderUtils.drawRect((posX+outlineBold).toFloat(),(posY+height-outlineBold).toFloat(),(posX+width-outlineBold).toFloat(),(posY+height).toFloat(),textColor.rgb)
        }
    }

    fun initKeyName():KeyStroke{
        keyName=Keyboard.getKeyName(key.keyCode)
        return this
    }

    fun initKeyName(name:String):KeyStroke{
        keyName=name
        return this
    }
}