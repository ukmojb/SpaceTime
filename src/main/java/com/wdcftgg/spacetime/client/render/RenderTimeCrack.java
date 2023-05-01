package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.client.model.ModelTimeCrack;
import com.wdcftgg.spacetime.entity.EntityTimeCrack;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 15:21
 */
public class RenderTimeCrack extends RenderLiving<EntityTimeCrack> {

    private static final ResourceLocation TIMECRACK = new ResourceLocation(
             "spacetime:textures/entity/time_crack.png");

    public RenderTimeCrack(RenderManager renderManager)
    {
        super(renderManager, new ModelTimeCrack(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityTimeCrack entity, float partialTickTime)
    {
        GlStateManager.scale(0.3F, 0.4F, 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTimeCrack entity)
    {
        return RenderTimeCrack.TIMECRACK;
    }

    @Override
    public void doRender(EntityTimeCrack entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
