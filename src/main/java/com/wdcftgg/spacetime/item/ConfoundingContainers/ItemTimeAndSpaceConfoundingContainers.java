package com.wdcftgg.spacetime.item.ConfoundingContainers;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ISpace;
import com.wdcftgg.spacetime.util.SpaceHelper;
import lumaceon.mods.clockworkphase.item.construct.abstracts.ITimeSand;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import lumaceon.mods.clockworkphase.util.TimeSandParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
 * @create 2023/4/30 4:46
 */
public class ItemTimeAndSpaceConfoundingContainers extends Item implements ITimeSand, ISpace, IHasModel {

    public ItemTimeAndSpaceConfoundingContainers()
    {
        setUnlocalizedName("time_and_space_confounding_containers");
        setRegistryName("time_and_space_confounding_containers");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        ModItems.ITEMS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int timeSand = getTimeSand(is);
        int Space = getSpace(is);
        if (timeSand <= 0){
            list.add("Time Sand: §eNo Time");
        } else {
            list.add("Time Sand: §e" + TimeSandParser.getStringForRenderingFromTimeSand(timeSand));
        }
        if (Space <= 0){
            list.add("Space: §eNo Space Energy");
        } else {
            list.add("Space: §e" + Space);
        }
    }

    @Override
    public int getMaxTimeSand()
    {
        return 100000;
    }

    @Override
    public int getTimeSand(ItemStack is)
    {
        return TimeSandHelper.getTimeSand(is);
    }

    @Override
    public int addTimeSand(ItemStack is, int timeSand)
    {
        return TimeSandHelper.addTimeSand(is, timeSand, getMaxTimeSand());
    }

    @Override
    public int removeTimeSand(ItemStack is, int timeSand)
    {
        return TimeSandHelper.removeTimeSand(is, timeSand);
    }

    @Override
    public int removeTimeSandFromInventory(IInventory inventory, int timeSand)
    {
        return TimeSandHelper.removeTimeSandFromInventory(inventory, timeSand);
    }

    @Override
    public int getTimeSandFromInventory(IInventory inventory)
    {
        return TimeSandHelper.getTimeSandFromInventory(inventory);
    }

    @Override
    public int getMaxSpace()
    {
        return 100000;
    }

    @Override
    public int getSpace(ItemStack is)
    {
        return SpaceHelper.getSpace(is);
    }

    @Override
    public int addSpace(ItemStack is, int Space)
    {
        return SpaceHelper.addSpace(is, Space, getMaxSpace());
    }

    @Override
    public int removeSpace(ItemStack is, int Space)
    {
        return SpaceHelper.removeSpace(is, Space);
    }

    @Override
    public int removeSpaceFromInventory(IInventory inventory, int Space)
    {
        return SpaceHelper.removeSpaceFromInventory(inventory, Space);
    }

    @Override
    public int getSpaceFromInventory(IInventory inventory)
    {
        return SpaceHelper.getSpaceFromInventory(inventory);
    }

    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }


    public Item getItemChangeTo()
    {
        return ModItems.TIMEANDSPACECONFOUNDINGCONTAINERS;
    }
}
