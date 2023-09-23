package com.wdcftgg.spacetime.blocks.stairs;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 11:11
 */
public class BlockBaseStairs extends BlockStairs implements IHasModel {

    public BlockBaseStairs(IBlockState modelState) {
        super(modelState);
        this.useNeighborBrightness = true;
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
