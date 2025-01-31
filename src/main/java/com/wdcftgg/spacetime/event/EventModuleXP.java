package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.TimeSandHelper;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.module.ItemModule;
import lumaceon.mods.clockworkphase.util.InventorySearchHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
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
        if (!event.player.world.isRemote) {
            if (event.crafting.getItem().equals(STItems.MODULEXP)) {
                EntityPlayer player = (EntityPlayer) event.player;
                IInventory inventory = event.craftMatrix;
                if (inventory instanceof InventoryCrafting) {
                    InventoryCrafting inv = (InventoryCrafting) inventory;
                    if (sameItemStack(inv.getStackInSlot(0), new ItemStack(Blocks.BOOKSHELF)) &&
                            sameItemStack(inv.getStackInSlot(2), new ItemStack(Blocks.BOOKSHELF)) &&
                            sameItemStack(inv.getStackInSlot(6), new ItemStack(Blocks.BOOKSHELF)) &&
                            sameItemStack(inv.getStackInSlot(8), new ItemStack(Blocks.BOOKSHELF)) &&

                            sameItemStack(inv.getStackInSlot(4), new ItemStack(ModItems.temporalCoreSedate)) &&
                            sameItemStack(inv.getStackInSlot(1), new ItemStack(Blocks.EMERALD_BLOCK)) &&
                            sameItemStack(inv.getStackInSlot(7), new ItemStack(Blocks.EMERALD_BLOCK)) &&
                            sameItemStack(inv.getStackInSlot(3), new ItemStack(ModItems.ingotTemporal)) &&
                            sameItemStack(inv.getStackInSlot(5), new ItemStack(ModItems.ingotTemporal))
                    ) {
//                        if (player.experienceLevel >= 30) {
//                            player.addExperienceLevel(-30);
//                        }
                    }
                }
            }
        }
    }

    private boolean sameItemStack(ItemStack itemStack1, ItemStack itemStack2) {
        return (itemStack1.getItem() == itemStack2.getItem()) && (itemStack1.getMetadata() == itemStack2.getMetadata());
    }
}
