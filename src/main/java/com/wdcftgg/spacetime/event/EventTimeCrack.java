package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.achievement.ModAdvancements;
import com.wdcftgg.spacetime.entity.EntityTimeCrack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 20:58
 */
@Mod.EventBusSubscriber
public class EventTimeCrack {
    @SubscribeEvent
    public static void onAttackEntityEvent(AttackEntityEvent event) {
        if (!event.getTarget().world.isRemote){
            if (event.getTarget() instanceof EntityLivingBase) {
                if (event.getTarget() instanceof EntityTimeCrack){
                    EntityLivingBase living = (EntityLivingBase) event.getTarget();
    //                if (living.getMaxHealth() >= 200000000 && living.getHealth() <= 100000000) {
                        EntityPlayerMP playermp = (EntityPlayerMP) event.getEntityPlayer();
                        ModAdvancements.HARD_WORKING.trigger(playermp);
    //                }
                }
            }
        }
    }
}
