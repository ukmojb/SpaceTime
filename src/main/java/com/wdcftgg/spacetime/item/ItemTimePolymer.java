package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ITime;
import com.wdcftgg.spacetime.util.TimeHelper;
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
 * @create 2023/4/30 15:01
 */
public class ItemTimePolymer extends Item implements ITime, IHasModel {
    public ItemTimePolymer()
    {
        setTranslationKey("time_polymer");
        setRegistryName("time_polymer");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int time = getTime(is);
        if (time <= 0){
            list.add("Contain Time: §eNo Time Energy");
        } else {
            list.add("Contain Time: §e" + time);
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
        return STItems.TIMEPOLYMER;
    }
}
