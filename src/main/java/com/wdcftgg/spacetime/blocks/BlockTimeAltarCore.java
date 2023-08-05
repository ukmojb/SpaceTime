package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/23 22:22
 */
public class BlockTimeAltarCore extends Block implements IHasModel {

    boolean canoutput = false;

    public BlockTimeAltarCore()
    {
        super(Material.ROCK);
        setTranslationKey("time_altarcore");
        setRegistryName("time_altarcore");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);;

        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setResistance(5.0F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TimeAltarCoreEntity();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (TimeAltarCoreEntity.TIMESAND >= 0 && TimeAltarCoreEntity.OUTPUT >= 0) {
                if (TimeSandHelper.getTimeSand(itemStack) >= TimeAltarCoreEntity.TIMESAND) {
                    killItems(world, pos);
                    TimeSandHelper.removeTimeSand(itemStack, TimeAltarCoreEntity.TIMESAND);
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), Item.getItemById(TimeAltarCoreEntity.OUTPUT).getDefaultInstance());
                    world.spawnEntity(item);
                }
            }
        }
        return true;
    }

    private void killItems(World world, BlockPos pos) {
        List<EntityItem> list = new ArrayList<EntityItem>();
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(-1, 1, 1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(2, 1, 1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, -1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 2))));

        for (EntityItem i : list) {
            world.removeEntity(i);
        }
    }

}