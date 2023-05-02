package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.ModBlocks;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeCompressorEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.example.block.tile.BotariumTileEntity;

import javax.annotation.Nullable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/2 16:52
 */
public class BlockHourGlass extends BlockDirectional implements ITileEntityProvider , IHasModel{
    public BlockHourGlass() {
        super(Material.ROCK);
        this.setCreativeTab(ModCreativeTab.SpaceTimeTab);
        setUnlocalizedName("hourglass");
        setRegistryName("hourglass");

        ModBlocks.BLOCKS.add(this);

        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setResistance(5.0F);
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new HourGlassEntity();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
