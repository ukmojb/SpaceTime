package com.wdcftgg.spacetime.blocks.stairs;

import com.wdcftgg.spacetime.blocks.STBlocks;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:03
 */
public class BlockSpaceStairs extends BlockBaseStairs {

    public BlockSpaceStairs() {
        super(STBlocks.SpaceBrick.getDefaultState());
        setTranslationKey("space_stairs");
        setRegistryName("space_stairs");
    }
}
