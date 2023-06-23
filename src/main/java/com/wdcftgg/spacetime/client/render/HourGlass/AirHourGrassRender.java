package com.wdcftgg.spacetime.client.render.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.AirHourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelAirHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:54
 */
public class AirHourGrassRender extends GeoBlockRenderer<AirHourGlassEntity> {
    public AirHourGrassRender() {
        super(new ModelAirHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}