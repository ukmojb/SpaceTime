package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.blocks.stextractor.STExtractorBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/27 17:28
 */
@Mod.EventBusSubscriber
public class EventToolTip {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) event.getItemStack().getItem();
            if (itemBlock.getBlock() instanceof STExtractorBase) {
                event.getToolTip().add(1, I18n.format("spacetime.extractor.tooltip.1"));
                event.getToolTip().add(2, I18n.format("spacetime.extractor.tooltip.2"));
            }
        }
    }
}
