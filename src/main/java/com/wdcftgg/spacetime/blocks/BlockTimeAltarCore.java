package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.HourGlass.HourGlassBase;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.network.MessageChest;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

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
        boolean output = true;
        if (!world.isRemote) {
            if (TimeAltarCoreEntity.TIMESAND >= 0 && TimeAltarCoreEntity.OUTPUT >= 0) {
                if (TimeSandHelper.getTimeSand(itemStack) >= TimeAltarCoreEntity.TIMESAND) {
                    ItemStack itemStack1 = TimeAltarCoreEntity.itemStackout;
                    Map<ItemStack, List<Integer>> in = TimeAltarCoreEntity.TimeAltarRecipesEnergy;
                    List<Integer> energy = in.get(itemStack1);
                    List<Integer> nulllist = new ArrayList<Integer>();
                    for (int i=0;i<8;i++) nulllist.add(0);
                    if (hasPlentyEnergy(energy, pos, world, player)) {
                        killItems(world, pos);
                        removeHourEnergy((player.isCreative() ? nulllist : energy), pos, world);
                        TimeSandHelper.removeTimeSand(itemStack, TimeAltarCoreEntity.TIMESAND);
                        if (config.ALTARAUTOMATE) {
//                            PacketHandler.INSTANCE.sendToAll();
//                            PacketHandler.INSTANCE.sendToServer(new MessageChest(pos.up(), itemStack1));
                            if (world.getTileEntity(pos.up()) != null) {
                                SpaceTime.Log("www0");
                                IInventory iinventory = null;
                                TileEntity tileentity = world.getTileEntity(pos.up());

                                if (tileentity instanceof IInventory) {
                                    iinventory = (IInventory)tileentity;
                                    iinventory.markDirty();
                                    iinventory.setInventorySlotContents(0, itemStack1);
                                    iinventory.markDirty();
                                    output = false;
                                }
                            }
                            if (output) {
                                EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), itemStack1);
                                world.spawnEntity(item);
                            }
                        }
                    } else {
                        player.sendMessage(new TextComponentString(I18n.format("spacetime.altar.hourglass.noenergy")));
                    }
                } else {
                    player.sendMessage(new TextComponentString(I18n.format("spacetime.altar.time.noenergy")));
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

    private boolean hasPlentyEnergy(List<Integer> energy, BlockPos pos, World world, EntityPlayer player){
        boolean pass = true;
        if (player.isCreative()) return true;
        BlockPos[] hourglasspos = new BlockPos[]{
                pos.east(3),
                pos.north(3),
                pos.south(3),
                pos.west(3),
                pos.east(2).south(2),
                pos.east(2).north(2),
                pos.west(2).south(2),
                pos.west(2).north(2),
        };
        for (int i=0;i<8;i++) {
            for (int ii=0;ii<8;ii++) {
                if (world.getBlockState(hourglasspos[i]).getBlock().getTranslationKey().contains(hourglassstr[ii])) {
                    if (world.getTileEntity(hourglasspos[i]).getTileData().getInteger(hourglassstr[ii]) < energy.get(i)) {
                        pass = false;
                        break;
                    }
                }
            }
        }
        return pass;
    }

    private Map<String, Integer> getHourEnergy(BlockPos pos, World world) {
        Map<String, Integer> hourenergy = new HashMap<String, Integer>();
        BlockPos[] hourglasspos = new BlockPos[]{
                pos.east(3),
                pos.north(3),
                pos.south(3),
                pos.west(3),
                pos.east(2).south(2),
                pos.east(2).north(2),
                pos.west(2).south(2),
                pos.west(2).north(2),
        };
        for (int i=0;i<8;i++) {
            if (world.getBlockState(hourglasspos[i]).getBlock().getTranslationKey().contains(hourglassstr[i])) {
                hourenergy.put(hourglassstr[i], world.getTileEntity(hourglasspos[i]).getTileData().getInteger(hourglassstr[i]));
            }
        }
        return hourenergy;
    }

    private void removeHourEnergy(List<Integer> energy, BlockPos pos, World world) {
        BlockPos[] hourglasspos = new BlockPos[]{
                pos.east(3),
                pos.north(3),
                pos.south(3),
                pos.west(3),
                pos.east(2).south(2),
                pos.east(2).north(2),
                pos.west(2).south(2),
                pos.west(2).north(2),
        };

        for (int i=0;i<8;i++) {
            for (int ii=0;ii<8;ii++) {
                if (world.getBlockState(hourglasspos[i]).getBlock() instanceof HourGlassBase) {
                    if (world.getBlockState(hourglasspos[i]).getBlock().getTranslationKey().contains(hourglassstr[ii])) {
                        removeHourEnergy(hourglassstr[ii], energy.get(ii), hourglasspos[i], world);
                    }
                }
            }
        }
    }

    private void removeHourEnergy(String str, int num, BlockPos pos, World world) {
        if (world.getBlockState(pos).getBlock().getTranslationKey().contains(str)) {
            int energy = world.getTileEntity(pos).getTileData().getInteger(str);
            world.getTileEntity(pos).getTileData().setInteger(str, energy - num);
        }
    }

    private final String[] hourglassstr = new String[]{
            "air",
            "death",
            "earth",
            "fire",
            "life",
            "light",
            "moon",
            "water"
    };
}