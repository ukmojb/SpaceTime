package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.EndGatewayImitateEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/10/1 15:51
 */
public class BlockEndGatewayImitate extends Block implements IHasModel {
    public BlockEndGatewayImitate()
    {
        super(Material.ROCK);
        setTranslationKey("endgatewayimitate");
        setRegistryName("endgatewayimitate");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);;

        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setBlockUnbreakable();
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new EndGatewayImitateEntity();
    }


    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}