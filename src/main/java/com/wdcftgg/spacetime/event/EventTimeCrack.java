package com.wdcftgg.spacetime.event;


import com.wdcftgg.spacetime.entity.EntityTimeCrack;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
    public void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote){
            if (event.getEntityLiving() instanceof EntityTimeCrack){
                EntityLiving living = (EntityLiving) event.getEntityLiving();
                event.getEntityLiving().world.removeEntity(living);
            }
        }
    }
}
