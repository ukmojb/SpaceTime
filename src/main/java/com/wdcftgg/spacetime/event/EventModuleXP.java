package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.item.STItems;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.module.ItemModule;
import lumaceon.mods.clockworkphase.util.InventorySearchHelper;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/31 19:01
 */
@Mod.EventBusSubscriber
public class EventModuleXP {

    @SubscribeEvent
    public void onPlayerPickupXpEvent(PlayerPickupXpEvent event) {
        EntityPlayer player;
        if (event.getEntityLiving() instanceof EntityPlayer) {
            player = (EntityPlayer)event.getEntityLiving();
            NonNullList<ItemStack> armor = player.inventory.armorInventory;
            ItemStack[] pocketWatches = InventorySearchHelper.getPocketWatches(player.inventory);

            if (pocketWatches != null && ItemPocketWatch.doesActiveItemModuleExist(pocketWatches, (ItemModule) STItems.MODULEXP)) {
                if (TimeSandHelper.getTimeSandFromInventory(player.inventory) >= 50) {
                    player.addExperience(event.getOrb().xpValue);
                    TimeSandHelper.removeTimeSandFromInventory(player.inventory, 50);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem().equals(STItems.MODULEXP)) {
            EntityPlayer player = (EntityPlayer)event.player;
            if (player.experienceLevel >= 30) {
                player.addExperienceLevel(-30);
            }
        }
    }
}
