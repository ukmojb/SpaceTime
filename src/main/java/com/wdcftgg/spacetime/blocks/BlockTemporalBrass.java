package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeCompressorEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTemporalBrass extends Block implements IHasModel {
    public BlockTemporalBrass()
    {
        super(Material.ROCK);
        setTranslationKey("temporal_brass_block");
        setRegistryName("temporal_brass_block");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);;

        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setResistance(5.0F);
    }
    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }


}
