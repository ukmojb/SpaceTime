package com.wdcftgg.spacetime.util;

import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 15:09
 */
public interface ITimeSupplier {
    public int getMaxTime();

    public int getTimeAvailable(ItemStack is);

    public int addTime(ItemStack is, int Time);

}
