package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.LightHourGlassEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/3 15:29
 */
public class BlockLightHourGlass extends HourGlassBase {
    public BlockLightHourGlass() {
        super("lighthourglass");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new LightHourGlassEntity();
    }
}
