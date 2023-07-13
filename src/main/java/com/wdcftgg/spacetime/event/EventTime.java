package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityTime;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

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
    public static void onLivingDeathClient(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityTime){
            EntityLiving living = (EntityLiving) event.getEntityLiving();
            if (!living.world.isRemote && living.getEntityAttribute(LIFE_POWER).getBaseValue() != 1.0D){
                event.setCanceled(true);
            }
        }
    }
}
