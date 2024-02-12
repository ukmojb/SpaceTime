package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.client.model.ModelSpace;
import com.wdcftgg.spacetime.client.model.ModelSpaceSword;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/9 14:24
 */
public class RenderSpaceSword extends GeoEntityRenderer<EntitySpaceSword>
{
    public RenderSpaceSword(RenderManager renderManager)
    {
        super(renderManager, new ModelSpaceSword());
    }

    @Override
    public void doRender(EntitySpaceSword entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}