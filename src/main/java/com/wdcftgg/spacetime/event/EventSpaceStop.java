package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventSpaceStop {


    @SubscribeEvent
    public void onLivingDestroyBlock(LivingDestroyBlockEvent event) {
        if(event.getEntityLiving().isPotionActive(ModPotions.SpaceStop)) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntityLiving().isPotionActive(ModPotions.SpaceStop)) {
            event.getEntityLiving().motionY = 0;
            event.getEntityLiving().isAirBorne = false;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingTickTest(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase e = event.getEntityLiving();
        if(e.isPotionActive(ModPotions.SpaceStop)) {
            boolean shouldFreeze = true;
            if(e.isDead || e.getHealth() <= 0) {
                shouldFreeze = false;
            }
            if(e instanceof EntityDragon && ((EntityDragon) e).getPhaseManager().getCurrentPhase().getType() == PhaseList.DYING) {
                shouldFreeze = false;
            }
            if(shouldFreeze) {
                e.motionX = 0;
                e.motionZ = 0;
            }
        }
    }
}
