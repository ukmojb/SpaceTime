package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.entity.EntityPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPortal extends Render {

    public static final ResourceLocation portaltex = new ResourceLocation("spacetime", "textures/entity/portal.png");
    public static final VertexFormat VERTEXFORMAT_POS_TEX_CO_LM_NO = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    public RenderPortal(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.0F;
        this.shadowOpaque = 0.0F;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.renderPortal((EntityPortal) entity, x, y, z, entityYaw, partialTicks);
    }

    public void renderPortal(EntityPortal portal, double x, double y, double z, float entityYaw, float partialTicks) {
//        if (portal.isEntityAlive()) {

            float rotationYaw = portal.rotationYaw;
            float ticksExisted = portal.ticksExisted;
            float deadTicks = portal.deathTime;

            float size = (ticksExisted > 30 ? 30 : ticksExisted) / 30;
            float size2 = deadTicks / 20;



            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.enablePolygonOffset();
            GL11.glDisable(GL11.GL_CULL_FACE);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.translate((float) x,(float) y + 1 + (1 - size) * 1.5 - (1 - size2) * 1.5,(float) z);
            GlStateManager.color(1f, 1f, 1f, 0.7F);
            GlStateManager.rotate(-rotationYaw, 0.0F, 1.0F, 0.0F);

            Minecraft.getMinecraft().getTextureManager().bindTexture(portaltex);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(1.5 * size * (1 - size2), 0, 0).tex(0,0).endVertex();
            buffer.pos(-1.5 * size * (1 - size2), 0, 0).tex(1,0).endVertex();
            buffer.pos(-1.5 * size * (1 - size2), 3 * size * (1 - size2), 0).tex(1,1).endVertex();
            buffer.pos(1.5 * size * (1 - size2), 3 * size * (1 - size2), 0).tex(0,1).endVertex();
            tessellator.draw();

            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GlStateManager.popMatrix();
//        }

    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return portaltex;
    }
}

