package com.wdcftgg.spacetime.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 15:10
 */
public class TimeHelper {

    public static int getTimeFromInventory(IInventory inventory)
    {
        ItemStack is;
        int amountAvailable = 0;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ITimeSupplier)
            {
                amountAvailable += ((ITimeSupplier)is.getItem()).getTimeAvailable(is);
            }
        }
        return amountAvailable;
    }

    public static int addTime(ItemStack is, int Time, int maxTime)
    {
        int currentTime = NBTHelper.getInt(is, "internal_time");

        if(currentTime + Time >= maxTime)
        {
            NBTHelper.setInteger(is, "internal_time", maxTime);
            return maxTime - currentTime;
        }
        else
        {
            NBTHelper.setInteger(is, "internal_time", currentTime + Time);
            return Time;
        }
    }

    /**
     * Attempts to remove Space from the item stack.
     *
     * @param is The item stack to remove from.
     * @param Space The amount of Space to remove.
     * @return The amount of time sand that was actually removed.
     */


    /**
     * Gets the time sand within the given item.
     * @param is
     * @return The time sand contained with the item stack.
     */
    public static int getTime(ItemStack is)
    {
        return NBTHelper.getInt(is, "internal_time");
    }
}
