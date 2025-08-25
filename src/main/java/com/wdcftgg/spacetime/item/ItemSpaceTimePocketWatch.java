package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.ClockworkPhase;
import lumaceon.mods.clockworkphase.client.gui.interfaces.GuiPocketWatch;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.lib.GUIs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpaceTimePocketWatch extends ItemPocketWatch implements IHasModel {
    private String translationKey;

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return "item.spacetime." + this.translationKey;
    }

    @Override
    public Item setTranslationKey(String key)
    {
        this.translationKey = key;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void useTemporalAbility()
    {
        this.handlePocketWatchAbility();
    }

    @SideOnly(Side.CLIENT)
    private  void handlePocketWatchAbility()
    {
        if(!FMLClientHandler.instance().isGUIOpen(GuiPocketWatch.class))
        {
            EntityPlayer player = Minecraft.getMinecraft().player;
            player.openGui(ClockworkPhase.instance, GUIs.POCKET_WATCH_GUI.ordinal(), player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
    }
}
