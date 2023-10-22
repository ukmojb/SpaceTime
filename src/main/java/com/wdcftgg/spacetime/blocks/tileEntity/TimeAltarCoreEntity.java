package com.wdcftgg.spacetime.blocks.tileEntity;


import com.wdcftgg.spacetime.blocks.HourGlass.HourGlassBase;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.network.MessageTimeAltarCore;
import com.wdcftgg.spacetime.network.PacketHandler;
import lumaceon.mods.clockworkphase.init.ModBlocks;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 13:38
 */
public class TimeAltarCoreEntity extends TileEntity implements ITickable {


    int timeenergy;


    public static int OUTPUT = -1;
    public static int TIMESAND = -1;

    public static boolean init = false;
    public static ItemStack itemStackout = ItemStack.EMPTY;

    public static List<List<ItemStack>> TimeAltarRecipesin = new ArrayList<List<ItemStack>>();
    public static List<List<Item>> ItemTimeAltarRecipesin = new ArrayList<List<Item>>();
    public static List<ItemStack> TimeAltarRecipesout = new ArrayList<ItemStack>();
    public static List<Integer> TimeAltarRecipesTimeEnergy = new ArrayList<Integer>();
    public static Map<ItemStack, List<Integer>> TimeAltarRecipesEnergy = new HashMap<ItemStack, List<Integer>>();
    private static List<Integer> in;

    @Override
    public void update() {
        if (!world.isRemote) {
            if (isstructure(pos, world)){
                List<EntityItem> items = getItems();
                if(areItemsValid(items) && outPutItem(items) != null) {
                    int num = Item.getIdFromItem(outPutItem(items).getItem());
                    int timesand = needtimeenergy(items);
                    OUTPUT = num;
                    TIMESAND = timesand;
                    world.getTileEntity(pos).getTileData().setInteger("output", num);
                    if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                        world.setBlockState(pos.up(), STBlocks.AIR.getDefaultState());
                    }
              }else{
                    OUTPUT = -1;
                    TIMESAND = -1;
                    world.getTileEntity(pos).getTileData().setInteger("output", 0);
                    if (world.getBlockState(pos.up()).getBlock() == STBlocks.AIR) {
                        world.setBlockToAir(pos.up());
                    }
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTimeAltarCore(0, 0, 0), new NetworkRegistry.TargetPoint(this.getWorld().provider.getDimension(), (double)this.getPos().getX(), (double)this.getPos().getY(), (double)this.getPos().getZ(), 256.0D));
                }
            }
        }
        super.updateContainingBlockInfo();
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        NBTTagCompound ret = super.writeToNBT(data);
        ret.setInteger("timeenergy", timeenergy);
        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        timeenergy = data.getInteger("timeenergy");
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return world.getTileEntity(pos).serializeNBT();
    }




    boolean isstructure (BlockPos pos, World world) {

        boolean pass0 = true;
        boolean pass1 = true;
        boolean pass2 = true;
        boolean pass3 = true;
        boolean pass4 = true;

        for(int i=0;i<structureblock.length;i++) {
            for (int ii = 0; ii < structurepos.length; ii++) {
                for (int iii = 0; iii < structurepos[ii].length; iii++) {

                    if (iii > structurepos[ii].length) continue;
                    int x0 = structurepos[ii][iii][0];
                    int y0 = structurepos[ii][iii][1];
                    int z0 = structurepos[ii][iii][2];
                    BlockPos pos0 = new BlockPos(pos.getX() + x0, pos.getY() + y0, pos.getZ() + z0);

                    if (ii == 0 && world.getBlockState(pos0).getBlock() != structureblock[ii]) {
                        pass0 = false;
                    }
                    if (ii == 1 && world.getBlockState(pos0).getBlock() != structureblock[ii]) {
                        pass1 = false;
                    }
                    if (ii == 2 && world.getBlockState(pos0).getBlock() != structureblock[ii]) {
                        pass2 = false;
                    }
                    if (ii == 3 && world.getBlockState(pos0).getBlock() != structureblock[ii]) {
                        pass3 = false;
                    }
                    if (ii == 4 && !(world.getBlockState(pos0).getBlock() instanceof HourGlassBase)) {
                        pass4 = false;
                    }
                }
            }
        }
        if (pass0 && pass1 && pass2 && pass3 && pass4){
            return true;
        } else {
            return false;
        }
    }

    int[][][] structurepos = new int[][][]{
            {{0, -1, 0}},
            {{1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1}},
            {{2, -1, 0}, {-2, -1, 0}, {0, -1, 2}, {0, -1, -2}, {1, -1, 1}, {-1, -1, -1}, {-1, -1, 1}, {-1, -1, -1}, {2, -1, -1}, {2, -1, 1}, {-2, -1, -1}, {-2, -1, 1}, {-1, -1, 2}, {1, -1, 2}, {-1, -1, -2}, {1, -1, -2}},
            {{3, -1, 0}, {-3, -1, 0}, {0, -1, 3}, {0, -1, -3}, {2, -1, 2}, {-2, -1, -2}, {-2, -1, 2}, {-2, -1, -2}},
            {{3, 0, 0}, {-3, 0, 0}, {0, 0, 3}, {0, 0, -3}, {2, 0, 2}, {-2, 0, -2}, {-2, 0, 2}, {-2, 0, -2}}
    };

    Block[] structureblock = new Block[]{
            Blocks.DIAMOND_BLOCK,
            Blocks.WOOL,
            lumaceon.mods.clockworkphase.init.ModBlocks.brassBlock,
            STBlocks.CONCRETIZATIONHOURGLASS
    };

    List<EntityItem> getItems() {
        List<EntityItem> list = new ArrayList<EntityItem>();
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(-1, 1, 1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(2, 1, 1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, -1))));
        list.addAll(world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 2))));
        return list;
    }

    boolean areItemsValid(List<EntityItem> items) {
        if(items.size() != 4)
            return false;

        List<Item> Input = new ArrayList<Item>();
        for(EntityItem item : items) {
            Input.add(item.getItem().getItem());
            for(List<Item> recipes : ItemTimeAltarRecipesin) {
                ArrayList<Item> list = new ArrayList<Item>(recipes);
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){
                    return true;
                }

            }
        }

        return false;
    }

    ItemStack outPutItem(List<EntityItem> items) {
        if(items.size() != 4)
            return null;

        List<Item> Input = new ArrayList<Item>();
        for(EntityItem item : items) {
            Input.add(item.getItem().getItem());
            for(int i=0;i<=(TimeAltarRecipesin.size()-1);i++) {
                ArrayList<Item> list = new ArrayList<Item>(ItemTimeAltarRecipesin.get(i));
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){
                    itemStackout = TimeAltarRecipesout.get(i);
                    return TimeAltarRecipesout.get(i);
                }
            }
        }

        return null;
    }

    boolean fouritems(List<EntityItem> items) {
        if(items.size() != 4)
            return false;

        int num = 0;
        for(EntityItem item : items) {
            num += item.getItem().getCount();
        }

        return num == 4;
    }

    int needtimeenergy(List<EntityItem> items) {
        if(items.size() != 4)
            return 0;

        List<Item> Input = new ArrayList<Item>();
        for(EntityItem item : items) {
            Input.add(item.getItem().getItem());
            for(int i=0;i<TimeAltarRecipesin.size();i++) {
//            for(Item[] recipes : TimeAltarRecipesin) {
                ArrayList<Item> list = new ArrayList<Item>(ItemTimeAltarRecipesin.get(i));
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){
                    return TimeAltarRecipesTimeEnergy.get(i);
                }
            }
        }

        return 0;
    }

    public static void initRecipes() {
        addAltarRecipes(newArray(new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND)), new ItemStack(Blocks.DIAMOND_BLOCK), 1000, 0, 0, 0, 0, 0, 0, 0, 0);
        addAltarRecipes(newArray(new ItemStack(ModItems.temporalCoreActive), new ItemStack(ModBlocks.blockTemporal), new ItemStack(ModItems.preciousCharm), new ItemStack(ModItems.gearChronosphere)), new ItemStack(STItems.TIMETICKET), 666666, 500000, 500000, 500000, 500000, 500000, 500000, 500000, 500000);
    }

    public static void addAltarRecipes(List<ItemStack> input, ItemStack output, int timeenergy, int... energy) {
        TimeAltarRecipesin.add(input);
        TimeAltarRecipesout.add(output);
        TimeAltarRecipesTimeEnergy.add(timeenergy);
        List<Item> list = new ArrayList<Item>();
        for (ItemStack itemStack : input) {
            list.add(itemStack.getItem());
        }
        ItemTimeAltarRecipesin.add(list);
        in = new ArrayList<Integer>();
        for (int num : energy) {
            in.add(num);
        }
        TimeAltarRecipesEnergy.put(output, in);
    }

    private static List<ItemStack> newArray(ItemStack item, ItemStack item1, ItemStack item2, ItemStack item3){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        input.add(item1);
        input.add(item2);
        input.add(item3);
        return input;
    }

}