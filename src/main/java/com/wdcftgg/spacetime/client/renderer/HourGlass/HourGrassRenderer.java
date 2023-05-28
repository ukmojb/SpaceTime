package com.wdcftgg.spacetime.client.renderer.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelHourGrass;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
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
