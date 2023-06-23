package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.blocks.tileEntity.ConcretizationHourGlassEntity;
import com.wdcftgg.spacetime.client.model.ModelConcretizationHourGlass;
import net.minecraft.util.EnumFacing;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 21:13
 */
public class RenderConcretizationHourGlass extends GeoBlockRenderer<ConcretizationHourGlassEntity> {
    public RenderConcretizationHourGlass() {
        super(new ModelConcretizationHourGlass());
        rotateBlock(null);
    }

    @Override
    protected void rotateBlock(EnumFacing facing)
    {
    }
}
