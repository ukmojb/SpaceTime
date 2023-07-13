package com.wdcftgg.spacetime.client.render;


import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 13:42
 */
public class RenderTimeAltarCore extends TileEntitySpecialRenderer<TimeAltarCoreEntity> {


    @Override
    public void render(TimeAltarCoreEntity AltarCore, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        ItemStack output = Item.getItemById(AltarCore.getWorld().getTileEntity(AltarCore.getPos()).getTileData().getInteger("output")).getDefaultInstance();
        EntityItem entityItem = new EntityItem(null, 0D, 0D, 0D);
        entityItem.setItem(output);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        entityItem.hoverStart = 0.0F;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.05F, (float) z + 0.18F);
        GlStateManager.translate(0, -0.1, 0);
        GL11.glScalef(0.9F, 0.9F, 0.9F);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 3D, 0D, 0.0F, 0.0F, false);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();


    }
}