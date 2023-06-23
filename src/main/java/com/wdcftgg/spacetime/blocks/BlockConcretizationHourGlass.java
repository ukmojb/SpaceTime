package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.ConcretizationHourGlassEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.item.construct.hourglass.ItemHourglass;
import lumaceon.mods.clockworkphase.item.construct.hourglass.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 20:09
 */
public class BlockConcretizationHourGlass extends Block implements ITileEntityProvider, IHasModel {

    private static final AxisAlignedBB Collisionbox = new AxisAlignedBB(0, 0, 0, 14/16.0, 5/16.0, 14/16.0);
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.3, 1);

    public BlockConcretizationHourGlass(){
        super(Material.ROCK);
        setUnlocalizedName("concretization_hourglass");
        setRegistryName("concretization_hourglass");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        this.setCreativeTab(ModCreativeTab.SpaceTimeTab);
        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setResistance(5.0F);
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new ConcretizationHourGlassEntity();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> boxes, Entity entity, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, boxes, Collisionbox);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof ConcretizationHourGlassEntity && !world.isRemote) {
            if (player.getHeldItemMainhand().getItem() instanceof ItemHourglass){
                int max_tension = player.getHeldItemMainhand().serializeNBT().getInteger("max_tension");
                int tension_energy = player.getHeldItemMainhand().serializeNBT().getInteger("tension_energy");
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassAir.class){
                    world.setBlockState(pos.up(), ModBlocks.AIRHOURGLASS.getDefaultState());
                    setHourglassNBT("air", max_tension, tension_energy, pos, world);
                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassDeath.class){
                    world.setBlockState(pos.up(), ModBlocks.DEATHHOURGLASS.getDefaultState());
                    setHourglassNBT("death", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassEarth.class){
                    world.setBlockState(pos.up(), ModBlocks.EARTHHOURGLASS.getDefaultState());
                    setHourglassNBT("earth", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassFire.class){
                    world.setBlockState(pos.up(), ModBlocks.FIREHOURGLASS.getDefaultState());
                    setHourglassNBT("fire", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLife.class){
                    world.setBlockState(pos.up(), ModBlocks.LIFEHOURGLASS.getDefaultState());
                    setHourglassNBT("life", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLight.class){
                    world.setBlockState(pos.up(), ModBlocks.LIGHTHOURGLASS.getDefaultState());
                    setHourglassNBT("light", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLunar.class){
                    world.setBlockState(pos.up(), ModBlocks.MOONHOURGLASS.getDefaultState());
                    setHourglassNBT("moon", max_tension, tension_energy, pos, world);

                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassWater.class){
                    world.setBlockState(pos.up(), ModBlocks.WATERHOURGLASS.getDefaultState());
                    setHourglassNBT("water", max_tension, tension_energy, pos, world);
                }
                if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglass.class){
                    world.setBlockState(pos.up(), ModBlocks.HOURGLASS.getDefaultState());
                }
                player.getHeldItemMainhand().shrink(player.isCreative() ? 0 : 1);
            }
            return true;
        }
        return false;
    }

    private void setHourglassNBT(String hourglass_energy, int maxtension, int tension_energy, BlockPos pos, World world){
        TileEntity tilehourglass = world.getTileEntity(pos);
        if (tilehourglass != null){
            tilehourglass.serializeNBT().setInteger(hourglass_energy, tension_energy);
            tilehourglass.serializeNBT().setInteger("maxTension", maxtension);
        }
    }
}
