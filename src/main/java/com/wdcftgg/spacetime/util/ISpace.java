package com.wdcftgg.spacetime.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 0:17
 */
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ISpace
{
    public int getMaxSpace();

    public int getSpace(ItemStack is);

    public int addSpace(ItemStack is, int Space);

    public int removeSpace(ItemStack is, int Space);

    /**
     * This is to be used before trying to remove time sand from the specific ITimeSand tool.
     * The method will attempt to draw time sand from ITimeSandSupplier items present in the
     * current inventory, returning the deficit.
     * @param inventory The inventory to check for time sand supplying items.
     * @param Space The amount of Space to try and remove.
     * @return The amount that was actually able to be removed.
     */
    public int removeSpaceFromInventory(IInventory inventory, int Space);

    /**
     * The method will check the inventory and return the amount of available time sand
     * for use. Note, this only returns the time sand from ITimeSandSupplier items.
     * @param inventory The inventory to check.
     * @return The amount of time sand available in the inventory.
     */
    public int getSpaceFromInventory(IInventory inventory);
}
