package com.wdcftgg.spacetime.gui.guicontainer;

import com.wdcftgg.spacetime.gui.button.GuiButtonItemActive;
import com.wdcftgg.spacetime.network.MessageSpaceTimePocketWatchGui;
import com.wdcftgg.spacetime.network.PacketHandler;
import lumaceon.mods.clockworkphase.lib.NBTTags;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class GuiSpaceTimePocketWatch extends GuiScreen
{
    public ItemStack[] items;
    public RenderItem itemRenders;
    public int guiLeft, guiTop, xSize, ySize;

    public GuiSpaceTimePocketWatch(ItemStack[] itemStacks)
    {
        super();

        itemRenders = Minecraft.getMinecraft().getRenderItem();
        if(itemStacks == null)
        {
            itemStacks = new ItemStack[0];
        }
        items = itemStacks;
        this.xSize = 300;
        this.ySize = 60;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        buttonList.clear();
        int index = 0;
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 2; y++)
            {
                if(items.length > index && items[index] != null)
                {
                    buttonList.add(new GuiButtonItemActive(items[index], index, guiLeft + (x % 10) * 30, guiTop + y * 30, "", itemRenders, fontRenderer, NBTHelper.getBoolean(items[index], NBTTags.ACTIVE)));
                }
                else
                {
                    buttonList.add(new GuiButtonItemActive(null, index, guiLeft + (x % 10) * 30, guiTop + y * 30, "", itemRenders, fontRenderer, false));
                }
                index++;
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSpaceTimePocketWatchGui(button.id));
        ((GuiButtonItemActive)buttonList.get(button.id)).active = !((GuiButtonItemActive)buttonList.get(button.id)).active;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.player.closeScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
