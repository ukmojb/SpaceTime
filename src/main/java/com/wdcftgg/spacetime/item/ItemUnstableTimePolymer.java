package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ITime;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.client.util.ITooltipFlag;
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
 * @create 2023/4/30 15:24
 */
public class ItemUnstableTimePolymer extends Item implements ITime, IHasModel {
    public ItemUnstableTimePolymer()
    {
        setUnlocalizedName("unstable_time_polymer");
        setRegistryName("unstable_time_polymer");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        ModItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        int time = getTime(is);
        int chance = 80000;
        if (time > 0) {
            chance = chance / time;
            if (chance < 1) {
                chance = 1;
            }
        }
        float percentagechance = (float) 1 / chance * 100;
        if (time <= 0){
            list.add("Contain Time: §eNo Space Energy");
        } else {
            list.add("Contain Time: §e" + time);
        }
        if (time > 0) {
            list.add("Chance of Split time crack: " + percentagechance + "%");
        }
    }

    @Override
    public int getMaxTime()
    {
        return 200000;
    }

    @Override
    public int addTime(ItemStack is, int time)
    {
        return TimeHelper.addTime(is, time, getMaxTime());
    }

    @Override
    public int getTime(ItemStack is)
    {
        return TimeHelper.getTime(is);
    }

    @Override
    public int getTimeFromInventory(IInventory inventory)
    {
        return TimeHelper.getTimeFromInventory(inventory);
    }

    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public Item getItemChangeTo()
    {
        return ModItems.UNSTABLETIMEPOLYMER;
    }
}
