package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.LifeHourGlassEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/3 15:28
 */
public class BlockLifeHourGlass extends HourGlassBase {
    public BlockLifeHourGlass() {
        super("lifehourglass");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new LifeHourGlassEntity();
    }
}