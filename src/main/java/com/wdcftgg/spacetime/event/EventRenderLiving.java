package com.wdcftgg.spacetime.event;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.wdcftgg.spacetime.potion.ModPotions;
import org.lwjgl.opengl.GL11;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 14:07
 */
@Mod.EventBusSubscriber
public class EventRenderLiving {
    @SubscribeEvent
    public static void onPreRenderLiving(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.6f); // 设置透明度
                GlStateManager.enableBlend(); // 启用混合模式
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // 设置混合模式为半透明
            }
        }
    }

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.disableBlend(); // 关闭混合模式
            }
        }
    }


    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent event) {
        if (event.getEntityPlayer() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityPlayer();
            if (player.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.6f); // 设置透明度
                GlStateManager.enableBlend(); // 启用混合模式
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // 设置混合模式为半透明
            } else {
                GlStateManager.disableBlend(); // 关闭混合模式

            }
        }
    }

}
