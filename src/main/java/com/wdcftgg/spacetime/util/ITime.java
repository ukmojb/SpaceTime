package com.wdcftgg.spacetime.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 15:07
 */
public interface ITime {
    public int getMaxTime();

    public int getTime(ItemStack is);

    public int getTimeFromInventory(IInventory inventory);

    public int addTime(ItemStack is, int time);
}