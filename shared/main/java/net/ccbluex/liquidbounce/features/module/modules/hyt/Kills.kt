package net.ccbluex.liquidbounce.features.module.modules.hyt
import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase

@ModuleInfo(name = "Kills", category = ModuleCategory.PLAYER, description = "大傻逼版")
class Kills : Module() {



        // Target
        var target: EntityLivingBase? = null
        companion object {
        var kill = 0
        var DeathNum = 0
                var Iskill =false
                var ticks = 0
        }
@EventTarget
    fun onAttack(event: AttackEvent) {
            target = event.targetEntity as EntityLivingBase?
            }

@EventTarget
    fun onUpdate(event: UpdateEvent) {

            if(Minecraft.getMinecraft().player.isDead){

            DeathNum ++
            }

            if (target!!.health <= 0.1) {
                    ticks ++
            kill += 1
                    if (ticks == 45){

                            Iskill = true
                            ticks == 0
                    }

            target = null
            }
            }

            fun kills() : Int {
            return kill
            }
            override val tag: String?
            get() = "Kill $kill"
            }
