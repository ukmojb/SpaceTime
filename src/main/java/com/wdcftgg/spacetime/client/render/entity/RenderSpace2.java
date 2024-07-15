package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.client.model.ModelSpace;
import com.wdcftgg.spacetime.client.model.ModelSpace2;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpace2;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderSpace2 extends GeoEntityRenderer<EntitySpace2>
{
    public RenderSpace2(RenderManager renderManager)
    {
        super(renderManager, new ModelSpace2());
    }

    @Override
    public void doRender(EntitySpace2 entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        entity.hurtTime = 0;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}