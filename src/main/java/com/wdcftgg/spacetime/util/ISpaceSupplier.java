package com.wdcftgg.spacetime.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 0:09
 */

import net.minecraft.item.ItemStack;

public interface ISpaceSupplier {
    public int getMaxSpace();

    public int getSpaceAvailable(ItemStack is);

    public int addSpace(ItemStack is, int space);

    public int removeSpace(ItemStack is, int space);
}
