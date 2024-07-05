package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/7 11:29
 */
@Mod.EventBusSubscriber
public class EventLossSpatialSense {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderPre(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player.isPotionActive(ModPotions.LossSpatialSense)) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180, 0, 0, 0);
            GlStateManager.translate(0, -1.8f, 0);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onRenderPre(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player.isPotionActive(ModPotions.LossSpatialSense))
            GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity renderViewEntity = minecraft.getRenderViewEntity();
        if (renderViewEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) renderViewEntity;
            if (player.isPotionActive(ModPotions.LossSpatialSense)) {
                player.setRotationYawHead(-event.getYaw() + 180);
                player.setRenderYawOffset(-event.getYaw() + 180);
                GlStateManager.rotate(180, 0.0F, 0.0F, 1);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onOverlayRender(final RenderGameOverlayEvent.Pre event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity renderViewEntity = minecraft.getRenderViewEntity();
        if (renderViewEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) renderViewEntity;
            if (player.isPotionActive(ModPotions.LossSpatialSense)) {
                GlStateManager.pushMatrix();
                GlStateManager.rotate(180, 0, 0, 0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOverlayRender(final RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Entity renderViewEntity = minecraft.getRenderViewEntity();
        if (renderViewEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) renderViewEntity;
            if (player.isPotionActive(ModPotions.LossSpatialSense)) {
                GlStateManager.popMatrix();
            }
        }
    }
}
