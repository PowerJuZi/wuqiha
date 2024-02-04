package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import java.awt.Color
import kotlin.math.abs
import kotlin.math.pow

@ElementInfo(name = "Target")
class targethud : Element(779.4,322.4,1.15F) {

        private var Health: Float = 0F
        private var EndingTarget: Entity? = null
        private val BarAnimationSpeed = FloatValue("BarAnimationSpeed", 2F, 1F, 9F)

        override fun drawElement(): Border {
                val target = (LiquidBounce.moduleManager[KillAura::class.java] as KillAura).target as Entity
                if (target is EntityPlayer) {
                        if (target != EndingTarget || Health < 0 || Health > target.maxHealth ||
                                abs(Health - target.health) < 0.01) {
                                Health = target.health
                        }

                        val width = (38 + Fonts.font40.getStringWidth(target.name.toString()))
                                .coerceAtLeast(119)
                                .toFloat()
                        RenderUtils.drawRect(-1f,0f,120f,45f,Color(230 ,230 ,250,
                                140).rgb)
                        RenderUtils.drawImage(ResourceLocation("liquidbounce/png/steve.png"),3,4,30,30)
                        RenderUtils.drawBorderedRect(3F, 37F, 115F, 42F, 4.2F, Color(16, 16, 16, 255).rgb, Color(10, 10, 10, 100).rgb)
                        RenderUtils.drawBorderedRect(3F, 37F, 115F, 42F, 1.2F, Color(255, 255, 255, 180).rgb, Color(255, 180, 255, 0).rgb)
                        if (Health > target.health)
                                RenderUtils.drawRect(3F, 37F, (Health / target.maxHealth) * width - 4F,
                                        42F, Color(250, 0, 0, 120).rgb)
                        RenderUtils.drawRect(3.2F, 37F, (target.health / target.maxHealth) * width - 4F,
                                42F, Color(220, 0, 0, 220).rgb)
                        if (Health < target.health)
                                RenderUtils.drawRect((Health / target.maxHealth) * width, 37F,
                                        (target.health / target.maxHealth) * width, 42F, Color(44, 201, 144).rgb)
                        RenderUtils.drawBorderedRect(3F, 37F, 115F, 42F, 1.2F, Color(255, 255, 255, 180).rgb, Color(255, 180, 255, 0).rgb)


                        Health += ((target.health - Health) / 2.0F.pow(10.0F - BarAnimationSpeed.get())) * RenderUtils.deltaTime

                        mc.fontRendererObj.drawStringWithShadow("" + target.name, 36F, 22F, 0xFFFFFF)
                }
                EndingTarget = target
                return Border(0F, 0F, 120F, 36F)
        }
}
