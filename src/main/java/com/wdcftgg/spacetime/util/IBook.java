package com.wdcftgg.spacetime.util;

import com.wdcftgg.spacetime.gui.book.data.BookData;
import net.minecraft.item.ItemStack;

public interface IBook {
    int getPageNum(ItemStack itemStack);
    void setPageNum(ItemStack itemStack, int num);
    BookData getBookData();
}
