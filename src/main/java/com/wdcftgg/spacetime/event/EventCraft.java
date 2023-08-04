package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.blocks.STBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/1 18:55
 */
@Mod.EventBusSubscriber
public class EventCraft {
    @SubscribeEvent
    public void onPlayerPickupXpEvent(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.equals(new ItemStack(STBlocks.TIMECOMPRESSOR))) {
            EntityPlayer player = (EntityPlayer)event.player;
        }
    }
}
