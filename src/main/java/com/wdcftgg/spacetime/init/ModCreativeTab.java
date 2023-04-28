package com.wdcftgg.spacetime.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {
	public static final CreativeTabs SpaceTimeTab = new CreativeTabs(CreativeTabs.getNextID(), "spacetimetab")
	{
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.TOTEM_OF_UNDYING);
        }
    };
}
