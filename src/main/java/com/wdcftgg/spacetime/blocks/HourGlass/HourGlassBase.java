package com.wdcftgg.spacetime.blocks.HourGlass;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/3 15:20
 */
public class HourGlassBase extends BlockDirectional implements ITileEntityProvider, IHasModel {
    public HourGlassBase(String name){
        super(Material.ROCK);
        setTranslationKey(name);
        setRegistryName(name);

        STBlocks.BLOCKS.add(this);
        this.setCreativeTab(ModCreativeTab.SpaceTimeTab);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        setHarvestLevel("pickaxe", -1);
        setHardness(5.0F);
        setResistance(5.0F);
    }

    private String[] hourglassstr = new String[]{
            "air",
            "death",
            "earth",
            "fire",
            "life",
            "light",
            "moon",
            "water"
    };


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

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (itemStack.getItem().equals(STItems.MAGNIFYINGGLASS) && !world.isRemote) {
            for (int i=0;i<8;i++) {
                if (state.getBlock() instanceof HourGlassBase) {
                    if (state.getBlock().getTranslationKey().contains(hourglassstr[i])) {
                        if (world.getTileEntity(pos).getTileData().getInteger(hourglassstr[i]) != 0) {
                            String str = String.valueOf(world.getTileEntity(pos).getTileData().getInteger(hourglassstr[i]));
                            player.sendMessage(new TextComponentString(I18n.format("spacetime.energy.0") + str));
                        } else {
                            player.sendMessage(new TextComponentString(I18n.format("spacetime.energy.1")));
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }
}
