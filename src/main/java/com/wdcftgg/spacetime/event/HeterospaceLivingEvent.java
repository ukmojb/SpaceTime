package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 15:24
 */

@Mod.EventBusSubscriber
public class HeterospaceLivingEvent {

//    @SubscribeEvent
//    public static void onLivingHurt(LivingHurtEvent event) {
//        if (event.getEntity() instanceof EntityLivingBase) {
//            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
//            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
//                event.setCanceled(true);
//            }
//        }
//    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                if (entity.isBurning()) {
                    entity.extinguish();
                }
            }
        }
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (player.getActivePotionEffect(ModPotions.heterospace) != null) {
                if (player.isBurning()) {
                    player.extinguish();
                }
                player.setAir(300);
            }
        }
    }

}
