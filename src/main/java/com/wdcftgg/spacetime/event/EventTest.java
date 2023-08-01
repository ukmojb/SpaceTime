package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.potion.ModPotions;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.module.ItemModule;
import lumaceon.mods.clockworkphase.util.InventorySearchHelper;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
