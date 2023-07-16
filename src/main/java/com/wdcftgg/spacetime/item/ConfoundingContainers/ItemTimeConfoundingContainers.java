package com.wdcftgg.spacetime.item.ConfoundingContainers;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 19:34
 */

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.item.construct.abstracts.ITimeSand;
import lumaceon.mods.clockworkphase.item.construct.abstracts.ITimeSandSupplier;
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

public class ItemTimeConfoundingContainers extends Item implements ITimeSand, IHasModel, ITimeSandSupplier {

    public ItemTimeConfoundingContainers()
    {
        setMaxStackSize(1);
        setUnlocalizedName("time_confounding_containers");
        setRegistryName("time_confounding_containers");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        ModItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int timeSand = getTimeSand(is);
        if (timeSand <= 0){
            list.add("Time Sand: §eNo Time");
        } else {
            list.add("Time Sand: §e" + TimeSandParser.getStringForRenderingFromTimeSand(timeSand));
        }
    }

    @Override
    public int getMaxTimeSand()
    {
        return 50000;
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
    public int getTimeSandAvailable(ItemStack is) {
        return TimeSandHelper.getTimeSand(is);
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
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }


    public Item getItemChangeTo()
    {
        return ModItems.TIMECONFOUNDINGCONTAINERS;
    }
}
