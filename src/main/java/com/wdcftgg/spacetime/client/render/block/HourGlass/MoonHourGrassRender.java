package com.wdcftgg.spacetime.client.render.block.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.MoonHourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelMoonHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:55
 */
public class MoonHourGrassRender extends GeoBlockRenderer<MoonHourGlassEntity> {
    public MoonHourGrassRender() {
        super(new ModelMoonHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}
