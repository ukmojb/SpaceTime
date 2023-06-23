package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.DeathHourGlassEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/3 15:25
 */
public class BlockDeathHourGlass extends HourGlassBase{
    public BlockDeathHourGlass() {
        super("deathhourglass");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new DeathHourGlassEntity();
    }
}
