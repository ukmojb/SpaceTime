package com.wdcftgg.spacetime.blocks.stairs;

import com.wdcftgg.spacetime.blocks.STBlocks;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:03
 */
public class BlockTimeStairs extends BlockBaseStairs {
    public BlockTimeStairs() {
        super(STBlocks.TimeBrick.getDefaultState());
        setTranslationKey("time_stairs");
        setRegistryName("time_stairs");
    }
}
