package com.wdcftgg.spacetime.blocks.tileEntity.stextractor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/27 9:37
 */

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.stextractor.*;
import lumaceon.mods.clockworkphase.block.extractor.ExtractorAreas;
import lumaceon.mods.clockworkphase.block.tileentity.TileEntityTimeSandCapacitor;
import lumaceon.mods.clockworkphase.block.tileentity.TileEntityTimeWell;
import lumaceon.mods.clockworkphase.custom.IInventoryHelper;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.ItemCatalyst;
import lumaceon.mods.clockworkphase.lib.MechanicTweaker;
import lumaceon.mods.clockworkphase.lib.NBTTags;
import lumaceon.mods.clockworkphase.network.MessageDoublePositionParticleSpawn;
import lumaceon.mods.clockworkphase.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase.lib.Phases;

public class STExtractorEntity extends TileEntityTimeSandCapacitor implements IInventoryHelper, ITickable
{

    public int timeWellX, timeWellY, timeWellZ;

    protected NonNullList<ItemStack> invv;
    public boolean extractorAreaReady = false;


    @Override
    public NonNullList<ItemStack> getInv() {
        return invv;
    }

    @Override
    public int getTimeSand() {
        return this.timeSandStored;
    }

    public STExtractorEntity()
    {
        super();
        invv = NonNullList.withSize(1, ItemStack.EMPTY);

    }

//    "catalyst_life"
//    "catalyst_light"
//    "catalyst_water"
//    "catalyst_earth"
//    "catalyst_air"
//    "catalyst_fire"
//    "catalyst_lunar"
//    "catalyst_death"
    public int getSTExtractorID()
    {
        if (this.world.getBlockState(pos).getBlock() instanceof LifeSTExtractor) {
            return 0;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof LightSTExtractor) {
            return 1;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof WaterSTExtractor) {
            return 2;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof EarthSTExtractor) {
            return 3;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof AirSTExtractor) {
            return 4;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof FireSTExtractor) {
            return 5;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof MoonSTExtractor) {
            return 6;
        }
        if (this.world.getBlockState(pos).getBlock() instanceof DeathSTExtractor) {
            return 7;
        }
        return 8;
    }

    public Phases getSTExtractorP(int id)
    {
        switch (id) {
            case 0:
                return Phases.LIFE;
            case 1:
                return Phases.LIGHT;
            case 2:
                return Phases.WATER;
            case 3:
                return Phases.EARTH;
            case 4:
                return Phases.AIR;
            case 5:
                return Phases.FIRE;
            case 6:
                return Phases.LUNAR;
            case 7:
                return Phases.DEATH;
                default:
                    return null;
        }
    }

    @Override
    public int getMaxTimeSandCapacity()
    {
        return 1200000;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagCompound compound = new NBTTagCompound();
        if(!getInv().get(0).isEmpty()) {
            getInv().get(0).writeToNBT(compound);
        }
        nbt.setTag(NBTTags.INVENTORY_ARRAY, compound);

        nbt.setInteger(NBTTags.CLOCKWORK_PHASE_X, timeWellX);
        nbt.setInteger(NBTTags.CLOCKWORK_PHASE_Y, timeWellY);
        nbt.setInteger(NBTTags.CLOCKWORK_PHASE_Z, timeWellZ);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        invv = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        invv.set(0, new ItemStack(nbt.getCompoundTag(NBTTags.INVENTORY_ARRAY)));

        timeWellX = nbt.getInteger(NBTTags.CLOCKWORK_PHASE_X);
        timeWellY = nbt.getInteger(NBTTags.CLOCKWORK_PHASE_Y);
        timeWellZ = nbt.getInteger(NBTTags.CLOCKWORK_PHASE_Z);
    }

    @Override
    public int getSizeInventory() {
        return getInv().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : getInv())
            if (!stack.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return getInv().get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int lossCount) {
        ItemStack is = getStackInSlot(index);
        if (!is.isEmpty())
        {
            if (lossCount >= is.getCount())
            {
                setInventorySlotContents(index, ItemStack.EMPTY);
            }
            else
            {
                is = is.splitStack(lossCount);
                if (is.getCount() == 0)
                {
                    setInventorySlotContents(index, ItemStack.EMPTY);
                }
            }
        }
        return is;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (!getInv().get(index).isEmpty())
        {
            ItemStack itemStack = getInv().get(index);
            getInv().set(index, ItemStack.EMPTY);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack is) {
        getInv().set(index, is);

        if (!is.isEmpty() && is.getCount() > this.getInventoryStackLimit())
        {
            is.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        getInv().clear();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack is)
    {
        return is.getItem() instanceof ItemCatalyst && is.getItem().equals(ModItems.catalystElements[this.getSTExtractorID()]);
    }

    public boolean applyEffect(int STExtractorID)
    {

        if(getInv().get(0).getItem() instanceof ItemCatalyst) {
            if (getInv().get(0).getItem().equals(ModItems.catalystElements[this.getSTExtractorID()])) {
                int newDamage, timeSandToAdd;
                switch (STExtractorID) {
                    case 0: //Life
                        addTimeSand(MechanicTweaker.TIME_SAND_FROM_NATURAL_SPAWN);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        break;
                    case 1: //Light
                        int lightLevels = 0;
                        for (EnumFacing facing : EnumFacing.VALUES)
                            lightLevels += world.getLightFromNeighbors(getPos().offset(facing));

                        timeSandToAdd = (int) ((lightLevels / 75.0) * MechanicTweaker.TIME_SAND_FROM_LIGHT_SECOND);
                        if (timeSandToAdd <= 0) {
                            return false;
                        }
                        if (timeSandToAdd > MechanicTweaker.TIME_SAND_FROM_LIGHT_SECOND) {
                            timeSandToAdd = MechanicTweaker.TIME_SAND_FROM_LIGHT_SECOND;
                        }
                        addTimeSand(timeSandToAdd);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        break;
                    case 2: //Water
                        int waterBlocks = 0;
                        for (int x = getPos().getX() - 1; x <= getPos().getX() + 1; x++) {
                            for (int y = getPos().getY() - 1; y <= getPos().getY() + 1; y++) {
                                for (int z = getPos().getZ() - 1; z <= getPos().getZ() + 1; z++) {
                                    if (world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) {
                                        waterBlocks++;
                                    }
                                }
                            }
                        }
                        timeSandToAdd = (int) ((waterBlocks / 26.0) * MechanicTweaker.TIME_SAND_FROM_WATER_SECOND);
                        if (timeSandToAdd <= 0) {
                            return false;
                        }
                        addTimeSand(timeSandToAdd);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        break;
                    case 3: //Earth
                        addTimeSand(MechanicTweaker.TIME_SAND_FROM_TREE_EXTRACTION);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        return true;
                    case 4: //Air
                        timeSandToAdd = (int) ((this.getPos().getY() / 255.0) * MechanicTweaker.TIME_SAND_FROM_AIR_SECOND);
                        if (timeSandToAdd <= 0) {
                            return false;
                        }
                        addTimeSand(timeSandToAdd);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        break;
                    case 5: //Fire
                        addTimeSand(MechanicTweaker.TIME_SAND_FROM_ONE_FIRE_DAMAGE);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        break;
                    case 6: //Lunar
                        if (world.getWorldTime() % 24000 > 12000 && world.getWorldTime() % 24000 < 24000) {
                            addTimeSand(MechanicTweaker.TIME_SAND_FROM_MOON_SECOND);
                            newDamage = getInv().get(0).getItemDamage() + 1;
                            if (newDamage > getInv().get(0).getMaxDamage()) {
                                decrStackSize(0, 1);
                            } else {
                                getInv().get(0).setItemDamage(newDamage);
                                markDirty();
                            }
                        }
                        break;
                    case 7: //Death
                        addTimeSand(MechanicTweaker.TIME_SAND_FROM_DEATH);
                        newDamage = getInv().get(0).getItemDamage() + 1;
                        if (newDamage > getInv().get(0).getMaxDamage()) {
                            decrStackSize(0, 1);
                        } else {
                            getInv().get(0).setItemDamage(newDamage);
                            markDirty();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public void update()
    {
        if(world != null)
        {
            if(!extractorAreaReady && getSTExtractorP(this.getSTExtractorID()) != null && !world.isRemote)
            {
                if(!ExtractorAreas.doesAreaExist(world, getPos().getX(), getPos().getY(), getPos().getZ()))
                {
                    int radius = 7;
                    ExtractorAreas.getAreasFromWorld(world, getSTExtractorP(this.getSTExtractorID())).addArea(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() - radius, getPos().getY() - radius, getPos().getZ() - radius, getPos().getX() + radius, getPos().getY() + radius, getPos().getZ() + radius);
                }
                this.extractorAreaReady = true;
            }

            int timeSandToRemove = 1000;
            if(getTimeSand() > 10000)
            {
                timeSandToRemove = getTimeSand() / 10;
            }

            if(world.getWorldTime() % 100 == 0 && getTimeSand() >= timeSandToRemove)
            {
                TileEntity te = world.getTileEntity(new BlockPos(timeWellX, timeWellY, timeWellZ));
                if(te instanceof TileEntityTimeWell)
                {
                    TileEntityTimeWell timeWell = (TileEntityTimeWell)te;
                    if(timeWell.getTimeSand() + timeSandToRemove <= timeWell.getMaxTimeSandCapacity()) //All time sand can be added
                    {
                        timeWell.addTimeSand(removeTimeSand(timeSandToRemove));
                    }
                    else //Time sand addition goes over max capacity
                    {
                        timeWell.addTimeSand(removeTimeSand(timeWell.getMaxTimeSandCapacity() - timeWell.getTimeSand())); //Add only enough to max.
                    }

                    if(!world.isRemote)
                    {
                        PacketHandler.INSTANCE.sendToAllAround(new MessageDoublePositionParticleSpawn(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, timeWellX + 0.5, timeWellY + 0.5, timeWellZ + 0.5, 1), new NetworkRegistry.TargetPoint(world.provider.getDimension(), getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 64));
                    }
                }
            }

            if(this.getSTExtractorID() != 8 && world.getWorldTime() % 20 == 0)
            {
                applyEffect(this.getSTExtractorID());
            }
        }
    }
}