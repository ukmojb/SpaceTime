package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimePillarEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 3:04
 */
public class BlockTimePillar extends Block implements IHasModel {
    public BlockTimePillar()
    {
        super(Material.ROCK);
        setTranslationKey("time_pillar");
        setRegistryName("time_pillar");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);;

        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        setResistance(1000.0F);
        setHarvestLevel("pickaxe", -1);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TimePillarEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TimePillarEntity timePillarEntity = (TimePillarEntity) worldIn.getTileEntity(pos);
        if (!worldIn.isRemote) {

        }
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
