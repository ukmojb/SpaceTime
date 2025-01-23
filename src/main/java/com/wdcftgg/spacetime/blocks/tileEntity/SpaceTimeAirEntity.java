package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.blocks.STBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/15 0:02
 */
public class SpaceTimeAirEntity extends TileEntity implements ITickable {

    public static final AxisAlignedBB INFINITE_EXTENT_AABB = new AxisAlignedBB(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    @SuppressWarnings ("rawtypes")
    @Override
    public void update() {
        if (!world.isRemote) {
            if (world.getBlockState(pos.down()).getBlock() != STBlocks.TIMEALTARCORE){
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
