package com.wdcftgg.spacetime.mods.jei.timealtar;

import com.wdcftgg.spacetime.item.STItems;
import lumaceon.mods.clockworkphase.init.ModBlocks;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:33
 */
public class CompatTimeAltar extends Compat {

    @Override
    public void addRecipes(List<Wrapper> list) {
        list.add(new Wrapper(66666, "spacetime.jei.altar", new ItemStack(STItems.TIMETICKET), ModItems.temporalCoreActive.getDefaultInstance(), new ItemStack(ModBlocks.blockTemporal), ModItems.preciousCharm.getDefaultInstance(), ModItems.gearChronosphere.getDefaultInstance()));
        list.add(new Wrapper(1000, "spacetime.jei.altar", new ItemStack(Blocks.DIAMOND_BLOCK), Items.DIAMOND.getDefaultInstance(), Items.DIAMOND.getDefaultInstance(), Items.DIAMOND.getDefaultInstance(), Items.DIAMOND.getDefaultInstance()));
    }
}