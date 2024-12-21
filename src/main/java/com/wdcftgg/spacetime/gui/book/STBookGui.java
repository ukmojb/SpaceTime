package com.wdcftgg.spacetime.gui.book;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class STBookGui extends GuiScreen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SpaceTime.MODID, "textures/gui/book_gui.jpg");

    private int guiLeft;
    private int guiTop;
    private int xSize;
    private int ySize;
    private boolean isDragging = false;

    @Override
    public void initGui() {
        // 计算背景的宽度和高度，使其占据屏幕的四分之三
        this.xSize = (int) (this.width * 0.75);
        this.ySize = (int) (this.height * 0.75);

        // 计算背景的位置，使其位于屏幕中央
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

//        this.buttonList.add()
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        // 使用 guiLeft 和 guiTop 绘制固定的背景纹理
        drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        // 禁用拖动功能：不执行任何拖动相关的操作
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        // 禁用拖动功能：不执行任何拖动相关的操作
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        // 禁用拖动功能：不执行任何拖动相关的操作
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}