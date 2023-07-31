package com.wdcftgg.spacetime.item.ConfoundingContainers;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ISpace;
import com.wdcftgg.spacetime.util.SpaceHelper;
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
 * @create 2023/4/30 0:16
 */
public class ItemSpaceConfoundingContainers extends Item implements ISpace, IHasModel {
    public ItemSpaceConfoundingContainers()
    {
        setMaxStackSize(1);
        setUnlocalizedName("space_confounding_containers");
        setRegistryName("space_confounding_containers");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int Space = getSpace(is);
        if (Space <= 0){
            list.add("Space: §eNo Space Energy");
        } else {
            list.add("Space: §e" + Space);
        }
    }

    @Override
    public int getMaxSpace()
    {
        return 50000;
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
        return STItems.SPACECONFOUNDINGCONTAINERS;
    }
}
