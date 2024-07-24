package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.model.ModelSpearsubspace;
import com.wdcftgg.spacetime.entity.EntitySpearsubspace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderSpearsubspace extends Render<EntitySpearsubspace> {

    private static final ModelSpearsubspace model = new ModelSpearsubspace();
    private static final ResourceLocation SPEAR_TEXTURES = new ResourceLocation(SpaceTime.MODID, "textures/entity/spearsubspace.png");

    public RenderSpearsubspace(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntitySpearsubspace weapon, double d0, double d1, double d2, float par8, float par9) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate(d0, d1, d2);
        GlStateManager.translate(0F, 0.5F, 0F);
        GlStateManager.rotate(weapon.rotationYaw, 0F, 1F, 0F);
        GlStateManager.rotate(90F, 1F, 0F, 0F);
        GlStateManager.rotate(-weapon.rotationPitch, 1F, 0F, 0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(SPEAR_TEXTURES);
        GlStateManager.scale(1F, -1F, -1F);
        model.render(0.07F);
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySpearsubspace entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}