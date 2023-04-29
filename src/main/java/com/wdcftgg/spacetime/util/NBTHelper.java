package com.wdcftgg.spacetime.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 0:06
 */

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class NBTHelper {
    
    private static void initNBTTagCompound(ItemStack is)
    {
        if (is.getTagCompound() == null)
        {
            is.setTagCompound(new NBTTagCompound());
        }
    }
    

    public static int getInt(ItemStack is, String keyName)
    {
        initNBTTagCompound(is);

        if (!is.getTagCompound().hasKey(keyName))
        {
            setInteger(is, keyName, 0);
        }

        return is.getTagCompound().getInteger(keyName);
    }

    public static void setInteger(ItemStack is, String keyName, int keyValue)
    {
        initNBTTagCompound(is);

        is.getTagCompound().setInteger(keyName, keyValue);
    }
}
