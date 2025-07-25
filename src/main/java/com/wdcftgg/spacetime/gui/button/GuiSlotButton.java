package com.wdcftgg.spacetime.gui.button;

import com.wdcftgg.spacetime.gui.book.STBookGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class GuiSlotButton extends GuiButton {

    private final boolean isDown;
    private int ticks;

    public GuiSlotButton(int id, int x, int y, int widthIn, int heightIn, boolean isDown) {
        super(id, x, y, widthIn, heightIn, "");
        this.isDown = isDown;
    }

    @Override
    public boolean mousePressed(@Nonnull Minecraft mc, int mouseX, int mouseY) {
        boolean pressed = super.mousePressed(mc, mouseX, mouseY);
        if (pressed && ticks <= 0) {
            this.ticks = 10;
        }
        return pressed;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(STBookGui.BOOK_GUI_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 200);

            if (ticks > 0) {
                this.drawTexturedModalRect(x, y, 62 - (isDown ? 1 : 0) * 22, 196 + 32, width, height);
                ticks--;
            }else if (this.hovered) {
                this.drawTexturedModalRect(x, y, 62 - (isDown ? 1 : 0) * 22, 196 + 32, width, height);
            } else this.drawTexturedModalRect(x, y, 62 - (isDown ? 1 : 0) * 22, 196, width, height);

            GlStateManager.popMatrix();

            this.mouseDragged(mc, mouseX, mouseY);
        }
    }

}
