package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.gui.book.BookRegistry;
import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.util.IBook;
import com.wdcftgg.spacetime.util.NBTHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BookBase extends ItemBase implements IBook {

    private BookData bookData;

    public BookBase(BookData bookData) {
        this.bookData = bookData;
    }

    @Override
    public int getPageNum(ItemStack itemStack) {
        return NBTHelper.getInt(itemStack, "pageNum");
    }

    @Override
    public void setPageNum(ItemStack itemStack, int num) {
        NBTHelper.setInteger(itemStack, "pageNum", num);
    }

    public int addPageNum(ItemStack is, int num)
    {
        int pageNum = NBTHelper.getInt(is, "pageNum");

        if(pageNum + num >= bookData.getAllPageNum())
        {
            NBTHelper.setInteger(is, "pageNum", bookData.getAllPageNum());
            return bookData.getAllPageNum() - pageNum;
        }
        else
        {
            NBTHelper.setInteger(is, "pageNum", pageNum + num);
            return num;
        }
    }

    public int removeSpace(ItemStack is, int Space)
    {
        int pageNum = NBTHelper.getInt(is, "pageNum");

        if(pageNum - Space <= 0)
        {
            NBTHelper.setInteger(is, "pageNum", 1);
            return pageNum;
        }
        else if(Space <= 0)
        {
            return 0;
        }
        else
        {
            NBTHelper.setInteger(is, "pageNum", pageNum - Space);
            return Space;
        }
    }

    @Override
    public BookData getBookData() {
        return bookData;
    }
}
