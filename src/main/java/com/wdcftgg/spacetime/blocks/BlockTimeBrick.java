package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:04
 */
public class BlockTimeBrick extends Block implements IHasModel {
    public BlockTimeBrick() {
        super(Material.ROCK);
        setTranslationKey("time_brick");
        setRegistryName("time_brick");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);


        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        setResistance(100.0F);
        setHarvestLevel("pickaxe", 1);
    }
    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}