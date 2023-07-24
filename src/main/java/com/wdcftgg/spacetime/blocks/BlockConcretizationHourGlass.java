package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.ConcretizationHourGlassEntity;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.item.construct.hourglass.*;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 20:09
 */
public class BlockConcretizationHourGlass extends Block implements ITileEntityProvider, IHasModel {

    private static final AxisAlignedBB Collisionbox = new AxisAlignedBB(0, 0, 0, 14/16.0, 5/16.0, 14/16.0);
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.3, 1);
    private Map<String, String> hourglass = new HashMap<String, String>();

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

        hourglass.put("air", "clockworkphase:hourglass_air");
        hourglass.put("death", "clockworkphase:hourglass_death");
        hourglass.put("earth", "clockworkphase:hourglass_earth");
        hourglass.put("fire", "clockworkphase:hourglass_fire");
        hourglass.put("life", "clockworkphase:hourglass_life");
        hourglass.put("light", "clockworkphase:hourglass_light");
        hourglass.put("moon", "clockworkphase:hourglass_lunar");
        hourglass.put("water", "clockworkphase:hourglass_water");
        hourglass.put("null", "clockworkphase:hourglass");
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
            if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                if (player.getHeldItemMainhand().getItem() instanceof ItemHourglass) {
                    int max_tension = player.getHeldItemMainhand().getTagCompound().getInteger("max_tension");
                    int tension_energy = player.getHeldItemMainhand().getTagCompound().getInteger("tension_energy");
                    int memory = player.getHeldItemMainhand().getTagCompound().getInteger("cp_memory");
                    int quality = player.getHeldItemMainhand().getTagCompound().getInteger("cp_quality");
                    int speed = player.getHeldItemMainhand().getTagCompound().getInteger("cp_speed");
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassAir.class) {
                        world.setBlockState(pos.up(), ModBlocks.AIRHOURGLASS.getDefaultState());
                        setHourglassNBT("air", max_tension, tension_energy, memory, quality, speed, pos, world);
                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassDeath.class) {
                        world.setBlockState(pos.up(), ModBlocks.DEATHHOURGLASS.getDefaultState());
                        setHourglassNBT("death", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassEarth.class) {
                        world.setBlockState(pos.up(), ModBlocks.EARTHHOURGLASS.getDefaultState());
                        setHourglassNBT("earth", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassFire.class) {
                        world.setBlockState(pos.up(), ModBlocks.FIREHOURGLASS.getDefaultState());
                        setHourglassNBT("fire", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLife.class) {
                        world.setBlockState(pos.up(), ModBlocks.LIFEHOURGLASS.getDefaultState());
                        setHourglassNBT("life", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLight.class) {
                        world.setBlockState(pos.up(), ModBlocks.LIGHTHOURGLASS.getDefaultState());
                        setHourglassNBT("light", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassLunar.class) {
                        world.setBlockState(pos.up(), ModBlocks.MOONHOURGLASS.getDefaultState());
                        setHourglassNBT("moon", max_tension, tension_energy, memory, quality, speed, pos, world);

                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglassWater.class) {
                        world.setBlockState(pos.up(), ModBlocks.WATERHOURGLASS.getDefaultState());
                        setHourglassNBT("water", max_tension, tension_energy, memory, quality, speed, pos, world);
                    }
                    if (player.getHeldItemMainhand().getItem().getClass() == ItemHourglass.class) {
                        world.setBlockState(pos.up(), ModBlocks.HOURGLASS.getDefaultState());
                    }
                    player.getHeldItemMainhand().shrink(1);
                }
            } else {
                if (player.getHeldItemMainhand().isEmpty()) {
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockAirHourGlass.class) {
                        giveHourglass("air", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockDeathHourGlass.class) {
                        giveHourglass("death", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockEarthHourGlass.class) {
                        giveHourglass("earth", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockFireHourGlass.class) {
                        giveHourglass("fire", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockLifeHourGlass.class) {
                        giveHourglass("life", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockLightHourGlass.class) {
                        giveHourglass("light", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockMoonHourGlass.class) {
                        giveHourglass("moon", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockWaterHourGlass.class) {
                        giveHourglass("water", pos.up(), world, player);
                    }
                    if (world.getBlockState(pos.up()).getBlock().getClass() == BlockHourGlass.class) {
                        giveHourglass("null", pos.up(), world, player);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void setHourglassNBT(String hourglass_energy, int maxtension, int tension_energy, int memory, int quality, int speed, BlockPos pos, World world){
        TileEntity tilehourglass = world.getTileEntity(pos.up());
        tilehourglass.getTileData().setInteger(hourglass_energy, tension_energy);
        tilehourglass.getTileData().setInteger("maxTension", maxtension);
        tilehourglass.getTileData().setInteger("cp_memory", memory);
        tilehourglass.getTileData().setInteger("cp_quality", quality);
        tilehourglass.getTileData().setInteger("cp_speed", speed);
    }

    private void giveHourglass(String hourglass_energy, BlockPos pos, World world, EntityPlayer player){
        int max_tension = world.getTileEntity(pos).getTileData().getInteger("maxTension");
        int tension_energy = world.getTileEntity(pos).getTileData().getInteger(hourglass_energy);
        int memory = world.getTileEntity(pos).getTileData().getInteger("cp_memory");
        int quality = world.getTileEntity(pos).getTileData().getInteger("cp_quality");
        int speed = world.getTileEntity(pos).getTileData().getInteger("cp_speed");
        ItemStack item = Item.getByNameOrId(hourglass.get(hourglass_energy)).getDefaultInstance();

        NBTHelper.setInteger(item, "tension_energy", tension_energy);
        NBTHelper.setInteger(item, "max_tension", max_tension);
        NBTHelper.setInteger(item, "cp_memory", memory);
        NBTHelper.setInteger(item, "cp_quality", quality);
        NBTHelper.setInteger(item, "cp_speed", speed);

        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, item);
        Timer timer = new Timer();
        TimerTask task1 =new TimerTask() {
            public void run() {
                world.setBlockToAir(pos);
            }
        };
        timer.schedule(task1,1);
    }
}
