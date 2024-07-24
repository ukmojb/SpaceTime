package com.wdcftgg.spacetime.client.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EventSpaceStop {

    private int num = 60;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientChatEvent(ClientChatEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            if (player.isPotionActive(ModPotions.SpaceStop)) {
                boolean shouldFreeze = true;
                if (player.isDead || player.getHealth() <= 0) {
                    shouldFreeze = false;
                }
                if (shouldFreeze) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFOVUpdate(FOVUpdateEvent event) {
        EntityPlayer player = event.getEntity();
        if(player.isPotionActive(ModPotions.SpaceStop)) {
            boolean shouldFreeze = true;
            num = 60;
            if(player.isDead || player.getHealth() <= 0) {
                shouldFreeze = false;
            }
            if(shouldFreeze) {
                event.setNewfov(getFovModifier(player));
            }
        } else {
            if (num > 0) {
                num -= 1;
                event.setNewfov(getFovModifier(player));
            }
        }
    }

    public float getFovModifier(EntityPlayer player)
    {
        float f = 1.0F;

        if (player.capabilities.isFlying)
        {
            f *= 1.1F;
        }

        if (Float.isInfinite(f))
        {
            f = 1.0F;
        }

        if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof net.minecraft.item.ItemBow)
        {
            int i = player.getItemInUseMaxCount();
            float f1 = (float)i / 20.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 = f1 * f1;
            }

            f *= 1.0F - f1 * 0.15F;
        }

        return f;
    }
}
