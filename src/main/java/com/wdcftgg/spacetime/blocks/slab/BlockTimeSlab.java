package com.wdcftgg.spacetime.blocks.slab;

import net.minecraft.block.material.Material;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:04
 */
public class BlockTimeSlab extends BlockSlabBase{
    public BlockTimeSlab(Material materialIn) {
        super(materialIn);
        setHardness(1.6F);
        setResistance(6.0F);

        setTranslationKey("time_slab");
        setRegistryName("time_slab");
    }
}
