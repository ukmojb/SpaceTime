package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityTime;
import com.wdcftgg.spacetime.entity.EntityTimePhantom;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/4 12:25
 */
public class RenderTimePhantom extends RenderLiving<EntityTimePhantom> {
    private static final ResourceLocation TIME_TEXTURE = new ResourceLocation(
            SpaceTime.MODID + ":" + "textures/entity/time.png");

    public RenderTimePhantom(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(1f, true), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityTimePhantom entity, float partialTickTime)
    {
        GlStateManager.scale(0.9F, 1F, 0.9F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTimePhantom entity)
    {
        return RenderTimePhantom.TIME_TEXTURE;
    }

    @Override
    public void doRender(EntityTimePhantom entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
