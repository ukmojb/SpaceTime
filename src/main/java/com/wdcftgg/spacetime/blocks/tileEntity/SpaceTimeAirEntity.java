package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.blocks.STBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/15 0:02
 */
public class SpaceTimeAirEntity extends TileEntity implements ITickable {
    @SuppressWarnings ("rawtypes")
    @Override
    public void update() {
        if (!world.isRemote) {
            if (world.getBlockState(pos.down()).getBlock() != STBlocks.TIMEALTARCORE){
                world.setBlockToAir(pos);
            }
        }
    }
}
