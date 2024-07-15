package com.wdcftgg.spacetime.client.render.block.HourGlass;

import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.DeathHourGlassEntity;
import com.wdcftgg.spacetime.client.model.HourGlass.ModelDeathHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:55
 */
public class DeathHourGrassRender extends GeoBlockRenderer<DeathHourGlassEntity> {
    public DeathHourGrassRender() {
        super(new ModelDeathHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}