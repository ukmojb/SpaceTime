package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.blocks.ModBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

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
            if (world.getBlockState(pos.down()).getBlock() != ModBlocks.TIMEALTARCORE){
                world.setBlockToAir(pos);
            }
        }
    }
}
