package com.wdcftgg.spacetime.mods.jei.timealtar;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:29
 */

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

public abstract class Compat implements Comparable<Compat> {


    public Compat() {
    }
    public abstract void addRecipes(List<Wrapper> list);

    @Override
    public int compareTo(Compat o) {
        return 0;
    }


    public static ItemStack getModdedItem(String name) {
        return getModdedItem(name, 1, 0);
    }

    public static ItemStack getModdedItem(String name, int count) {
        return getModdedItem(name, count, 0);
    }

    public static ItemStack getModdedItem(String name, int count, int meta) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        if (item == null) return ItemStack.EMPTY;
        else return new ItemStack(item, count, meta);
    }
}
