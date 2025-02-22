package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.client.model.ModelSpace;
import com.wdcftgg.spacetime.entity.EntitySpace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/3 14:19
 */
public class RenderSpace extends GeoEntityRenderer<EntitySpace>
{
    public RenderSpace(RenderManager renderManager)
    {
        super(renderManager, new ModelSpace());
    }

    @Override
    public void doRender(EntitySpace entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        entity.hurtTime = 0;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void applyRotations(EntitySpace entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {

        float r = (float) ((entityLiving.rotationYawHead - entityLiving.rotationYaw) * 0.5);

        GlStateManager.rotate(entityLiving.rotationYawHead + 180, 0, -1, 0);
    }
}