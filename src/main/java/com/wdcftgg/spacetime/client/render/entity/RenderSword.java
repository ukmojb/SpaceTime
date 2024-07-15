package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntitySword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/24 19:56
 */
public class RenderSword extends RenderLiving<EntitySword> {
    private static final ResourceLocation NULL_TEXTURE = new ResourceLocation(
            SpaceTime.MODID + ":" + "textures/blocks/blank.png");
    private int swordlength = 0;
    private List<Item> swordList = new ArrayList<Item>();


    public RenderSword(RenderManager renderManager)
    {
        super(renderManager, null, 0F);
    }



    @Override
    protected void preRenderCallback(EntitySword entity, float partialTickTime)
    {
        GlStateManager.scale(0F, 0F, 0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySword entity)
    {
        return NULL_TEXTURE;
    }

    @Override
    public void doRender(EntitySword entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        EntityItem entityItem = new EntityItem(entity.world, 0D, 0D, 0D);
//        ItemStack itemStack = Item.getItemById(Item.getIdFromItem(swordList.get((int) entity.getHealth()))).getDefaultInstance();
        entityItem.setItem(Item.getItemById(entity.getSwordType()).getDefaultInstance());

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        entityItem.hoverStart = 0.0F;
        GlStateManager.translate(x - 2.35, y + 5, z);
        GlStateManager.rotate(225, 0, 0, 90);
        GlStateManager.scale(7F, 7F, 7F);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D , 0.0F, 0.0F, true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
