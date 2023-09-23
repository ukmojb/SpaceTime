package com.wdcftgg.spacetime.blocks.fence;

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.item.STItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 15:02
 */
public class BlockSpaceFence extends BlockBaseFence{
    public BlockSpaceFence() {
        super(Material.ROCK, MapColor.PURPLE);
        setRegistryName("space_fence");
        setTranslationKey("space_fence");
        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}
