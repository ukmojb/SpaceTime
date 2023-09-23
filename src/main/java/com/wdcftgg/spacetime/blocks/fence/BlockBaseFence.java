package com.wdcftgg.spacetime.blocks.fence;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.BlockStateContainer;
/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/23 15:02
 */
public class BlockBaseFence extends BlockFence implements IHasModel {

    public static final PropertyBool UP = PropertyBool.create("up");

    public BlockBaseFence(Material materialIn, MapColor mapColorIn) {
        super(materialIn, mapColorIn);
        setCreativeTab(ModCreativeTab.SpaceTimeTab);
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        boolean flag =  canFenceConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean flag1 = canFenceConnectTo(worldIn, pos, EnumFacing.EAST);
        boolean flag2 = canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean flag3 = canFenceConnectTo(worldIn, pos, EnumFacing.WEST);
        boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
        return state.withProperty(UP, Boolean.valueOf(!flag4 || !worldIn.isAirBlock(pos.up()))).withProperty(NORTH, Boolean.valueOf(flag)).withProperty(EAST, Boolean.valueOf(flag1)).withProperty(SOUTH, Boolean.valueOf(flag2)).withProperty(WEST, Boolean.valueOf(flag3));
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH});
    }

    /**
     *
     */
    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
