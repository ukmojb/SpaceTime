package com.wdcftgg.spacetime.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonItemActive extends GuiButton
{

    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");

    public boolean active;
    public ItemStack item;
    public RenderItem itemRender;
    public FontRenderer fontRenderer;
    public Minecraft mc;

    public GuiButtonItemActive(ItemStack is, int buttonId, int x, int y, String buttonText, RenderItem renderItem, FontRenderer fontRenderer, boolean active)
    {
        super(buttonId, x, y, 20, 20, buttonText);
        this.item = is;
        this.itemRender = renderItem;
        this.mc = Minecraft.getMinecraft();
        this.active = active;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(minecraft, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.hovered)
            {
                l = 16777120;
            }

            if(item != null)
            {
                if(!active)
                {
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                }
                else
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
                this.drawItemStack(item, this.x + 2, this.y + 2, this.displayString);
            }
        }
    }

    private void drawItemStack(ItemStack is, int x, int y, String name)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        float zLevelOrigin = this.zLevel;
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(is, x, y);
        this.zLevel = zLevelOrigin;
        itemRender.zLevel = 0.0F;
    }
}
