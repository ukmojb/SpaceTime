package com.wdcftgg.spacetime.event;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/1 15:57
 */
@Mod.EventBusSubscriber
public class EventTest {
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntitySheep) {
            event.getEntityLiving().onLivingUpdate();
            event.getEntityLiving().onLivingUpdate();
            event.getEntityLiving().onLivingUpdate();
            event.getEntityLiving().onLivingUpdate();
            event.getEntityLiving().onLivingUpdate();
            event.getEntityLiving().onLivingUpdate();
        }
    }
}
