package com.wdcftgg.spacetime.client.render.block.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.LifeHourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelLifeHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:55
 */
public class LifeHourGrassRender extends GeoBlockRenderer<LifeHourGlassEntity> {
    public LifeHourGrassRender() {
        super(new ModelLifeHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}
