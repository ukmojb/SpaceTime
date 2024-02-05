package com.wdcftgg.spacetime.client.render;


import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.network.MessageTimeAltarCore;
import com.wdcftgg.spacetime.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.lwjgl.opengl.GL11;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 13:42
 */
public class RenderTimeAltarCore extends TileEntitySpecialRenderer<TimeAltarCoreEntity> {

    public static float p = -1f;
    public static float a = -1f;
    public static float g = -1f;
    public float x = 0f;

    @Override
    public void render(TimeAltarCoreEntity AltarCore, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        int num = AltarCore.getWorld().getTileEntity(AltarCore.getPos()).getTileData().getInteger("output");
        ItemStack output = Item.getItemById(num).getDefaultInstance();
        EntityItem entityItem = new EntityItem(AltarCore.getWorld(), 0D, 0D, 0D);
        entityItem.setItem(output);
        if (num != 0){
            PacketHandler.INSTANCE.sendToAllAround(new MessageTimeAltarCore(RenderTimeAltarCore.p, RenderTimeAltarCore.a, RenderTimeAltarCore.g), new NetworkRegistry.TargetPoint(AltarCore.getWorld().provider.getDimension(), (double)AltarCore.getPos().getX(), (double)AltarCore.getPos().getY(), (double)AltarCore.getPos().getZ(), 256.0D));
            renderItem(entityItem, x, y, z);
            renderTextures("textures/gui/normalmatrix.png", x, y, z);
        }
    }



    private void renderTextures(String str, double x, double y, double z){
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enablePolygonOffset();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate((float) x + 0.49,(float) y + 1.01,(float) z + 0.49);
//        GlStateManager.translate(-4f, -1f, +3f);
        GlStateManager.rotate(g, 0, 1f, 0);
        GlStateManager.color(53/255f, 254/255f, 255/255f, a);
        GlStateManager.doPolygonOffset(-1, -0);

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("spacetime", str));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(4, 0, -4).tex(0,0).endVertex();
        buffer.pos(-4, 0, -4).tex(1,0).endVertex();
        buffer.pos(-4, 0, 4).tex(1,1).endVertex();
        buffer.pos(4, 0, 4).tex(0,1).endVertex();
        tessellator.draw();

        GlStateManager.disablePolygonOffset();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.popMatrix();
    }

    private void renderItem(EntityItem entityItem, double x, double y, double z){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        entityItem.hoverStart = 3.0F;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.05F, (float) z + 0.5F);
        GlStateManager.scale(0.9F, 0.9F, 0.9F);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 180F, partial(), true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    float partial(){
        x += 0.25f;
        return x;
    }
}