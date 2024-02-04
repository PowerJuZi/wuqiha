/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.enums.EnumFacingType
import net.ccbluex.liquidbounce.api.enums.WEnumHand
import net.ccbluex.liquidbounce.api.minecraft.item.IItem
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import me.aquavit.liquidsense.utils.Debug.*
import net.ccbluex.liquidbounce.features.module.modules.render.OldHitting
import net.ccbluex.liquidbounce.utils.createUseItemPacket
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue

@ModuleInfo(name = "NoSlow", description = "Cancels slowness effects caused by soulsand and using items.",
        category = ModuleCategory.MOVEMENT)
class NoSlow : Module() {

    private val blockForwardMultiplier = FloatValue("BlockForwardMultiplier", 1.0F, 0.2F, 1.0F)
    private val blockStrafeMultiplier = FloatValue("BlockStrafeMultiplier", 1.0F, 0.2F, 1.0F)

    private val consumeForwardMultiplier = FloatValue("ConsumeForwardMultiplier", 1.0F, 0.2F, 1.0F)
    private val consumeStrafeMultiplier = FloatValue("ConsumeStrafeMultiplier", 1.0F, 0.2F, 1.0F)

    private val bowForwardMultiplier = FloatValue("BowForwardMultiplier", 1.0F, 0.2F, 1.0F)
    private val bowStrafeMultiplier = FloatValue("BowStrafeMultiplier", 1.0F, 0.2F, 1.0F)

    private val packet = BoolValue("Packet", true)
    private val Debug = BoolValue("Debug", false)
    // Soulsand
    val soulsandValue = BoolValue("Soulsand", true)

    val timer = MSTimer()

    val killAura = LiquidBounce.moduleManager[KillAura::class.java] as KillAura


    fun isBlock(): Boolean {
        return thePlayerisBlocking || killAura.blockingStatus
    }

    fun fuckKotline(value: Int): Boolean{
        return value == 1
    }

    fun getValue(): BoolValue? {
        return packet
    }

    @EventTarget
    fun onPacket(event: PacketEvent){
        if(Debug.get()){
            if(classProvider.isCPacketPlayerDigging(event.packet)){
                mc.thePlayer!!.addChatMessage(classProvider.createChatComponentText("C07"))
            }
            if(classProvider.isCPacketTryUseItem(event.packet)) {
                mc.thePlayer!!.addChatMessage(classProvider.createChatComponentText("C08"))
            }
        }


    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        val thePlayer = mc.thePlayer ?: return
        var test = fuckKotline(mc.thePlayer!!.ticksExisted and 1)
        val heldItem = thePlayer.heldItem
        if (heldItem == null || !classProvider.isItemSword(heldItem.item)) {
            return
        }
        if (!isBlock()){
            return
        }
        if (this.packet.get()) {
            when (event.eventState) {
                EventState.PRE -> {
                    thePlayerisBlocking = false
                    val digging = classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, getHytBlockpos(), classProvider.getEnumFacing(EnumFacingType.DOWN))
                    mc.netHandler.addToSendQueue(digging)
                    thePlayerisBlocking = true
                }
                EventState.POST -> {
                    thePlayerisBlocking = false
                    val blockPlace1 = createUseItemPacket(thePlayer.inventory.getCurrentItemInHand(), WEnumHand.MAIN_HAND)
                    val blockPlace2 = createUseItemPacket(thePlayer.inventory.getCurrentItemInHand(), WEnumHand.OFF_HAND)
                    if(LiquidBounce.moduleManager[OldHitting::class.java].state){
                        mc.netHandler.addToSendQueue(blockPlace2)
                    } else {
                        mc.netHandler.addToSendQueue(blockPlace1)
                        mc.netHandler.addToSendQueue(blockPlace2)
                    }
                    thePlayerisBlocking = true
                }
            }
        }
    }

    @EventTarget
    fun onSlowDown(event: SlowDownEvent) {
        val heldItem = mc.thePlayer!!.heldItem?.item

        event.forward = getMultiplier(heldItem, true)
        event.strafe = getMultiplier(heldItem, false)
    }

    private fun getMultiplier(item: IItem?, isForward: Boolean): Float {
        return when {
            classProvider.isItemFood(item) || classProvider.isItemPotion(item) || classProvider.isItemBucketMilk(item) -> {
                if (isForward) this.consumeForwardMultiplier.get() else this.consumeStrafeMultiplier.get()
            }
            classProvider.isItemSword(item) -> {
                if (isForward) this.blockForwardMultiplier.get() else this.blockStrafeMultiplier.get()
            }
            classProvider.isItemBow(item) -> {
                if (isForward) this.bowForwardMultiplier.get() else this.bowStrafeMultiplier.get()
            }
            else -> 0.2F
        }
    }

    fun getHytBlockpos(): WBlockPos {
        val random = java.util.Random()
        val dx = WMathHelper.floor_double(random.nextDouble() / 1000 + 2820)
        val jy = WMathHelper.floor_double(random.nextDouble() / 100 * 0.20000000298023224)
        val kz = WMathHelper.floor_double(random.nextDouble() / 1000 + 2820)
        return WBlockPos(dx, -jy % 255, kz)
    }

    override val tag: String?
        get() = when {
            packet.get() -> "HytPacket"
            else -> "Vanilla"
        }

}
