package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.entity.EntityTime;
import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/12 21:39
 */
@Mod.EventBusSubscriber
public class EventTime {

    private static final IAttribute LIFE_POWER = new RangedAttribute(null, "spacetime.attribute.life", 4.0, 0.0, 4.0).setShouldWatch(false);


    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityTime && !event.getEntityLiving().world.isRemote){
            EntityLiving living = (EntityLiving) event.getEntityLiving();
            if (living.getEntityAttribute(LIFE_POWER).getBaseValue() != 1.0D){
                event.setCanceled(true);
            }
        }
    }
}
