package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL12.GL_LIGHT_MODEL_COLOR_CONTROL;
import static org.lwjgl.opengl.GL12.GL_SEPARATE_SPECULAR_COLOR;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/11 16:44
 */
public class RenderTime extends RenderLiving<EntityTime> {
    private static final ResourceLocation TIME_TEXTURE = new ResourceLocation(
            SpaceTime.MODID + ":" + "textures/entity/time.png");

    public RenderTime(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(1f, true), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityTime entity, float partialTickTime)
    {
        GlStateManager.scale(0.9F, 1F, 0.9F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTime entity)
    {
        return RenderTime.TIME_TEXTURE;
    }

    @Override
    public void doRender(EntityTime entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
