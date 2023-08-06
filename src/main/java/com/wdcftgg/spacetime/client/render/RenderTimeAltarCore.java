package com.wdcftgg.spacetime.client.render;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.config.config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL12.GL_LIGHT_MODEL_COLOR_CONTROL;
import static org.lwjgl.opengl.GL12.GL_SEPARATE_SPECULAR_COLOR;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 13:42
 */
public class RenderTimeAltarCore extends TileEntitySpecialRenderer<TimeAltarCoreEntity> {

    private float p = -1f;
    private float a = -1f;
    private float g = -1f;

    @Override
    public void render(TimeAltarCoreEntity AltarCore, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        int num = AltarCore.getWorld().getTileEntity(AltarCore.getPos()).getTileData().getInteger("output");
        ItemStack output = Item.getItemById(num).getDefaultInstance();
        EntityItem entityItem = new EntityItem(AltarCore.getWorld(), 0D, 0D, 0D);
        entityItem.setItem(output);
        if (p == -1 || a == -1 || g == -1) {
            p = 0;
            a = 0;
            g = 0;
        }
        if (num != 0){
            renderItem(entityItem, x, y, z);
            renderTextures("textures/gui/normalmatrix.png", x, y, z);
        } else {
            p = 0;
            a = 0;
            g = 0;
        }
        SpaceTime.Log(partialTicks+"");
    }



    private void renderTextures(String str, double x, double y, double z){
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enablePolygonOffset();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslatef((float) x + 4.5f,(float) y + 1,(float) z - 2.5f);
        GlStateManager.translate(-4f, -1f, +3f);
        GlStateManager.rotate(angle(), 0F, 1F, 0F);
        GlStateManager.color(53/255f, 254/255f, 255/255f, alpha());
        GlStateManager.doPolygonOffset(-1, -0);
        GL11.glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("spacetime", str));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(3.5, 1, -3.5).tex(0,0).endVertex();
        buffer.pos(-3.5, 1, -3.5).tex(1,0).endVertex();
        buffer.pos(-3.5, 1, 3.5).tex(1,1).endVertex();
        buffer.pos(3.5, 1, 3.5).tex(0,1).endVertex();
        tessellator.draw();

        GlStateManager.disablePolygonOffset();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void renderItem(EntityItem entityItem, double x, double y, double z){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        entityItem.hoverStart = 0.0F;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.05F, (float) z + 0.5F);
        GlStateManager.scale(0.9F, 0.9F, 0.9F);
        GlStateManager.color(1.0f, 1.0f, 1.0f, alpha());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 180F, partial(), true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    float partial(){
        p += 0.5f;
        return p;
    }

    float alpha(){
        a += 0.001f;
        if (a >= 0.9f){
            return  0.9f;
        }
        return a;
    }

    float angle(){
        if (alpha() == 0.9f){
            g += (float) config.ROTATIONALSPEED;
            if (g > 360.0f){
                g = 0;
                return  0.1f;
            }
            return g;
        } else {
            return 0f;
        }
    }
}