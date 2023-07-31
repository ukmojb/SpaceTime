package com.wdcftgg.spacetime.gui.guicontainer;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/27 10:15
 */

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.gui.container.ContainerSTExtractor;
import lumaceon.mods.clockworkphase.block.tileentity.TileEntityExtractor;
import lumaceon.mods.clockworkphase.client.gui.container.ContainerExtractor;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.lib.Textures;
import lumaceon.mods.clockworkphase.util.TimeSandParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiSTExtractor extends GuiContainer {
    public STExtractorEntity te;
    public int id;

    public GuiSTExtractor(STExtractorEntity te, InventoryPlayer ip, int STExtractorID) {
        super(new ContainerSTExtractor(te, ip, STExtractorID));
        this.xSize = 256;
        this.ySize = 256;
        this.id = STExtractorID;
        this.te = te;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.drawCenteredString(this.fontRenderer, TimeSandParser.getStringForRenderingFromTimeSand(te.getTimeSand()), 61, 14 - this.fontRenderer.FONT_HEIGHT / 2, 16777215);
//        this.drawCenteredString(this.fontRenderer, TimeSandParser.getStringForRenderingFromTimeSand(te.getTimeSand()), 61, 14 - this.fontRenderer.FONT_HEIGHT / 2, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (!te.getStackInSlot(0).isEmpty() && te.getStackInSlot(0).getItem().equals(ModItems.catalystElements[id])) {
            Minecraft.getMinecraft().renderEngine.bindTexture(Textures.EXTRACTOR_GUI_ACTIVE[id]);
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(Textures.EXTRACTOR_GUI[id]);
        }
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}