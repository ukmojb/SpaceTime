package com.wdcftgg.spacetime.client.renderer;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.client.model.ModelHourGrass;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import software.bernie.example.block.tile.BotariumTileEntity;
import software.bernie.example.client.model.tile.BotariumModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/2 17:05
 */
public class HourGrassRenderer extends GeoBlockRenderer<HourGlassEntity> {
    public HourGrassRenderer() {
        super(new ModelHourGrass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}
