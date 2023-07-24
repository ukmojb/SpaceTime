package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.item.Item;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/7 20:10
 */
public class ItemSpaceTimeNugget extends Item implements IHasModel {
    public ItemSpaceTimeNugget()
    {
        setRegistryName("spacetime_nugget");
        setUnlocalizedName("spacetime_nugget");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        ModItems.ITEMS.add(this);

    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
