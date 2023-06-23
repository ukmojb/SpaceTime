package com.wdcftgg.spacetime.client.render.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.FireHourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelFireHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:55
 */
public class FireHourGrassRender extends GeoBlockRenderer<FireHourGlassEntity> {
    public FireHourGrassRender() {
        super(new ModelFireHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}