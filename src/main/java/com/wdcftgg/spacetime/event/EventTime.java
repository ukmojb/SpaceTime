package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.entity.EntityTime;
import com.wdcftgg.spacetime.entity.EntityTimePhantom;
import com.wdcftgg.spacetime.network.MessageTimeBack;
import com.wdcftgg.spacetime.network.MessageTimeParticle;
import com.wdcftgg.spacetime.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
            if (living.getEntityAttribute(LIFE_POWER).getBaseValue() == 2.0D){
                living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(config.TIMELASTLIFEMAXHEALTH);
                EntityTimePhantom ent = new EntityTimePhantom(living.world);
                ent.setPosition(living.posX, living.posY, living.posZ);
                living.world.spawnEntity(ent);
            }
            if (living.getEntityAttribute(LIFE_POWER).getBaseValue() != 1.0D){
                event.setCanceled(true);
            } else {
                for (Entity ent : living.world.getLoadedEntityList()) {
                    if (ent instanceof EntityTimePhantom) {
                        PacketHandler.INSTANCE.sendToAllAround(new MessageTimeParticle(living.getPosition(), false, true), new NetworkRegistry.TargetPoint(living.world.provider.getDimension(), (double)living.getPosition().getX(), (double)living.getPosition().getY(), (double)living.getPosition().getZ(), 256.0D));
                        living.world.removeEntity(ent);
                    }
                }
                PacketHandler.INSTANCE.sendToAllAround(new MessageTimeBack(false, true), new NetworkRegistry.TargetPoint(living.world.provider.getDimension(), (double)living.getPosition().getX(), (double)living.getPosition().getY(), (double)living.getPosition().getZ(), 256.0D));
            }
        }
    }
}
