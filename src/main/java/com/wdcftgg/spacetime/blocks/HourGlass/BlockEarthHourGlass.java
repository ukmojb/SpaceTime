package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.EarthHourGlassEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/3 15:27
 */
public class BlockEarthHourGlass extends HourGlassBase{
    public BlockEarthHourGlass() {
        super("earthhourglass");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new EarthHourGlassEntity();
    }
}
