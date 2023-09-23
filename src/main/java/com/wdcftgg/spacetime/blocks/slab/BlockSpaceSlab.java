package com.wdcftgg.spacetime.blocks.slab;

import net.minecraft.block.material.Material;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:04
 */
public class BlockSpaceSlab extends BlockSlabBase {
    public BlockSpaceSlab(Material materialIn) {
        super(materialIn);
        setHardness(1.6F);
        setResistance(6.0F);

        setTranslationKey("space_slab");
        setRegistryName("space_slab");
    }
}
