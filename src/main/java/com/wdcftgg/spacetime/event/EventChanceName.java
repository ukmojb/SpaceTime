package com.wdcftgg.spacetime.event;

import lumaceon.mods.clockworkphase.init.ModBlocks;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/25 20:51
 */
@Mod.EventBusSubscriber
public class EventChanceName {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        for (Item item : event.getRegistry().getValues()) {
            if (item.equals(ModItems.ingotTemporal)){
                item.setTranslationKey("spacetime.time_ingot");
            }
            if (item.equals(ModItems.nuggetTemporal)){
                item.setTranslationKey("spacetime.time_nugget");
            }
            if (item.equals(ModItems.chipTemporal)){
                item.setTranslationKey("spacetime.time_chip");
            }
            if (item.equals(ModItems.gearTemporal)){
                item.setTranslationKey("spacetime.time_gear");
            }
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        for (Block block : event.getRegistry().getValues()) {
            if (block.equals(ModBlocks.blockTemporal)) {
                block.setTranslationKey("spacetime.time_block");
            }
        }
    }
}
