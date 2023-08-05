package com.wdcftgg.spacetime.blocks.tileEntity;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.HourGlass.HourGlassBase;
import com.wdcftgg.spacetime.blocks.STBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public void update() {
        if (!world.isRemote) {
            if (isstructure(pos, world)){
                List<EntityItem> items = getItems();
                if(areItemsValid(items)) {
                    int num = Item.getIdFromItem(outPutItem(items));
                    int timesand = needtimeenergy(items);
                    OUTPUT = num;
                    TIMESAND = timesand;
                    SpaceTime.Log(num+"");
                    world.getTileEntity(pos).getTileData().setInteger("output", Item.getIdFromItem(outPutItem(items)));
                    world.setBlockState(pos.up(), STBlocks.AIR.getDefaultState());
                }else{
                    OUTPUT = -1;
                    TIMESAND = -1;
                    world.getTileEntity(pos).getTileData().setInteger("output", 0);
                    world.setBlockToAir(pos.up());
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
            for(Item[] recipes : TimeAltarRecipesin) {
                ArrayList<Item> list = new ArrayList<Item>(Arrays.asList(recipes));
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){
                    return true;
                }

            }
        }

        return false;
    }

    Item outPutItem(List<EntityItem> items) {
        if(items.size() != 4)
            return null;

        List<Item> Input = new ArrayList<Item>();
        for(EntityItem item : items) {
            Input.add(item.getItem().getItem());
            for(int i=0;i<TimeAltarRecipesin.length;i++) {
                ArrayList<Item> list = new ArrayList<Item>(Arrays.asList(TimeAltarRecipesin[i]));
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){

                    return TimeAltarRecipesout[i][0];
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
            for(int i=0;i<TimeAltarRecipesin.length;i++) {
//            for(Item[] recipes : TimeAltarRecipesin) {
                ArrayList<Item> list = new ArrayList<Item>(Arrays.asList(TimeAltarRecipesin[i]));
                if (Input.containsAll(list) && Input.size() == 4 && fouritems(items)){
                    return TimeAltarRecipesEnergy[i][0];
                }
            }
        }

        return 0;
    }

    public Item[][] TimeAltarRecipesin = new Item[][]{
            {Items.DIAMOND, Items.DIAMOND, Items.DIAMOND, Items.DIAMOND},
            {Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT}
    };
    public  Item[][] TimeAltarRecipesout = new Item[][]{
            {Items.APPLE},
            {Items.GOLDEN_APPLE}
    };
    public  int[][] TimeAltarRecipesEnergy = new int[][]{
            {100},
            {666}
    };
}