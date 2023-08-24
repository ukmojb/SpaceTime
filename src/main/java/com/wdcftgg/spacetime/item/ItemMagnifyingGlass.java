package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/8 9:20
 */
public class ItemMagnifyingGlass extends Item implements IHasModel {
    public ItemMagnifyingGlass()
    {
        setTranslationKey("magnifying_glass");
        setRegistryName("magnifying_glass");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);
    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        list.add(I18n.format("spacetime.magnifying_glass.tooltip"));
    }
}