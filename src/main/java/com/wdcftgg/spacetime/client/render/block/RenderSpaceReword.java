package com.wdcftgg.spacetime.client.render.block;

import com.wdcftgg.spacetime.blocks.tileEntity.SpaceRewordEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RenderSpaceReword extends TileEntitySpecialRenderer<SpaceRewordEntity> {
    @Override
    public void render(SpaceRewordEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler == null) return;

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        ItemStack clock = new ItemStack(Items.CLOCK);

//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x + 0.5, y + (double) 9 / 16, z + (double) 1.7 / 16);
//        renderItem.renderItem(clock, ItemCameraTransforms.TransformType.GROUND);
//        GlStateManager.popMatrix();
//
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x + 0.5, y + (double) 9 / 16, z + (double) 14.3 / 16);
//        renderItem.renderItem(clock, ItemCameraTransforms.TransformType.GROUND);
//        GlStateManager.popMatrix();
//
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x + 14.3 / 16, y + (double) 9 / 16, z + 0.5);
//        GlStateManager.rotate(90, 0, 1, 0);
//        renderItem.renderItem(clock, ItemCameraTransforms.TransformType.GROUND);
//        GlStateManager.popMatrix();
//
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x + 1.7 / 16, y + (double) 9 / 16, z + 0.5);
//        GlStateManager.rotate(90, 0, 1, 0);
//        renderItem.renderItem(clock, ItemCameraTransforms.TransformType.GROUND);
//        GlStateManager.popMatrix();

        ItemStack inSlot = handler.getStackInSlot(0);
        if (inSlot.isEmpty()) return;

        GlStateManager.pushMatrix();

        if (inSlot.getItem() instanceof ItemBlock) {
            GlStateManager.translate(x + 0.5, y + (double) 11 / 16, z + 0.5);
            GlStateManager.scale(1.3F, 1.3F, 1.3F);
        } else {
            GlStateManager.translate(x + 0.5, y + (double) 13 / 16, z + 0.5);
        }

        renderItem.renderItem(inSlot, ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.popMatrix();
    }
}