package com.paragon.client.systems.module.impl.render

import com.paragon.api.event.player.RenderItemEvent
import com.paragon.api.module.Category
import com.paragon.api.module.Module
import com.paragon.api.setting.Setting
import me.wolfsurge.cerauno.listener.Listener
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.EnumHandSide

/**
 * @author Surge
 */
object ViewModel : Module("ViewModel", Category.RENDER, "Changes the way items are rendered in your hand") {

    // Main hand settings
    private val main = Setting("MainHand", true)
        .setDescription("Modify your main hand")

    private val mainX = Setting("X", 0.19f, -2f, 2f, 0.01f)
        .setDescription("The x of the item")
        .setParentSetting(main)

    private val mainY = Setting("Y", -0.14f, -2f, 2f, 0.01f)
        .setDescription("The y of the item")
        .setParentSetting(main)

    private val mainZ = Setting("Z", -0.43f, -2f, 2f, 0.01f)
        .setDescription("The z of the item")
        .setParentSetting(main)

    private val mainYaw = Setting("Yaw", 0f, -100f, 100f, 1f)
        .setDescription("The yaw of the item")
        .setParentSetting(main)

    private val mainPitch = Setting("Pitch", 0f, -100f, 100f, 1f)
        .setDescription("The pitch of the item")
        .setParentSetting(main)

    private val mainRoll = Setting("Roll", 0f, -100f, 100f, 1f)
        .setDescription("The roll of the item")
        .setParentSetting(main)

    private val mainScaleX = Setting("ScaleX", 1f, 0f, 1f, 0.01f)
        .setDescription("The X scale of the item")
        .setParentSetting(main)

    private val mainScaleY = Setting("ScaleY", 1f, 0f, 1f, 0.01f)
        .setDescription("The Y scale of the item")
        .setParentSetting(main)

    private val mainScaleZ = Setting("ScaleZ", 1f, 0f, 1f, 0.01f)
        .setDescription("The Z scale of the item")
        .setParentSetting(main)

    // Offhand settings
    private val offhand = Setting("Offhand", true)
        .setDescription("Modify your offhand")

    private val offhandX = Setting("X", -0.19f, -2f, 2f, 0.01f)
        .setDescription("The x of the item")
        .setParentSetting(offhand)

    private val offhandY = Setting("Y", -0.14f, -2f, 2f, 0.01f)
        .setDescription("The y of the item")
        .setParentSetting(offhand)

    private val offhandZ = Setting("Z", -0.43f, -2f, 2f, 0.01f)
        .setDescription("The z of the item")
        .setParentSetting(offhand)

    private val offhandYaw = Setting("Yaw", 0f, -100f, 100f, 1f)
        .setDescription("The yaw of the item")
        .setParentSetting(offhand)

    private val offhandPitch = Setting("Pitch", 0f, -100f, 100f, 1f)
        .setDescription("The pitch of the item")
        .setParentSetting(offhand)

    private val offhandRoll = Setting("Roll", 0f, -100f, 100f, 1f)
        .setDescription("The roll of the item")
        .setParentSetting(offhand)

    private val offhandScaleX = Setting("ScaleX", 1f, 0f, 1f, 0.01f)
        .setDescription("The X scale of the item")
        .setParentSetting(offhand)

    private val offhandScaleY = Setting("ScaleY", 1f, 0f, 1f, 0.01f)
        .setDescription("The Y scale of the item")
        .setParentSetting(offhand)

    private val offhandScaleZ = Setting("ScaleZ", 1f, 0f, 1f, 0.01f)
        .setDescription("The Z scale of the item")
        .setParentSetting(offhand)

    @Listener
    fun onRenderItemPre(event: RenderItemEvent.Pre) {
        if (event.side == EnumHandSide.LEFT && offhand.value) {
            // Translate offhand item according to x, y, and z settings
            GlStateManager.translate(offhandX.value, offhandY.value, offhandZ.value)

            // Scale offhand
            GlStateManager.scale(offhandScaleX.value, offhandScaleY.value, offhandScaleZ.value)
        }
        if (event.side == EnumHandSide.RIGHT && main.value) {
            // Translate main hand item according to x, y, and z settings
            GlStateManager.translate(mainX.value, mainY.value, mainZ.value)

            // Scale main hand
            GlStateManager.scale(mainScaleX.value, mainScaleY.value, mainScaleZ.value)
        }
    }

    @Listener
    fun onRenderItemPost(event: RenderItemEvent.Post) {
        if (event.side == EnumHandSide.LEFT && offhand.value) {
            // Rotate offhand item according to yaw, pitch, and roll settings
            GlStateManager.rotate(offhandYaw.value, 0f, 1f, 0f)
            GlStateManager.rotate(offhandPitch.value, 1f, 0f, 0f)
            GlStateManager.rotate(offhandRoll.value, 0f, 0f, 1f)
        }
        if (event.side == EnumHandSide.RIGHT && main.value) {
            // Rotate main hand item according to yaw, pitch, and roll settings
            GlStateManager.rotate(mainYaw.value, 0f, 1f, 0f)
            GlStateManager.rotate(mainPitch.value, 1f, 0f, 0f)
            GlStateManager.rotate(mainRoll.value, 0f, 0f, 1f)
        }
    }

}