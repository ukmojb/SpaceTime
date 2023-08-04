package com.wdcftgg.spacetime.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 0:02
 */
public class SpaceHelper {
    public static int removeSpaceFromInventory(IInventory inventory, int Space)
    {
        ItemStack is;
        int amountRemoved;
        int originalSpace = Space;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ISpaceSupplier)
            {
                amountRemoved = ((ISpaceSupplier)is.getItem()).removeSpace(is, Space);
                Space -= amountRemoved;

                if(Space <= 0)
                {
                    return originalSpace;
                }
            }
        }
        return originalSpace - Space;
    }

    public static int getSpaceFromInventory(IInventory inventory)
    {
        ItemStack is;
        int amountAvailable = 0;
        for(int n = 0; n < inventory.getSizeInventory(); n++)
        {
            is = inventory.getStackInSlot(n);
            if(is != null && is.getItem() instanceof ISpaceSupplier)
            {
                amountAvailable += ((ISpaceSupplier)is.getItem()).getSpaceAvailable(is);
            }
        }
        return amountAvailable;
    }

    /**
     * Attempts to add Space to the item stack.
     *
     * @param is The item stack to add to.
     * @param Space The Space to add.
     * @param maxSpace The max capacity of the item stack.
     * @return The amount of Space that was able to be added.
     */
    public static int addSpace(ItemStack is, int Space, int maxSpace)
    {
        int currentSpace = NBTHelper.getInt(is, "internal_space");

        if(currentSpace + Space >= maxSpace)
        {
            NBTHelper.setInteger(is, "internal_space", maxSpace);
            return maxSpace - currentSpace;
        }
        else
        {
            NBTHelper.setInteger(is, "internal_space", currentSpace + Space);
            return Space;
        }
    }

    /**
     * Attempts to remove Space from the item stack.
     *
     * @param is The item stack to remove from.
     * @param Space The amount of Space to remove.
     * @return The amount of time sand that was actually removed.
     */
    public static int removeSpace(ItemStack is, int Space)
    {
        int currentSpace = NBTHelper.getInt(is, "internal_space");

        if(currentSpace - Space <= 0)
        {
            NBTHelper.setInteger(is, "internal_space", 0);
            return currentSpace;
        }
        else if(Space <= 0)
        {
            return 0;
        }
        else
        {
            NBTHelper.setInteger(is, "internal_space", currentSpace - Space);
            return Space;
        }
    }

    /**
     * Gets the time sand within the given item.
     * @param is
     * @return The time sand contained with the item stack.
     */
    public static int getSpace(ItemStack is)
    {
        return NBTHelper.getInt(is, "internal_space");
    }
}
