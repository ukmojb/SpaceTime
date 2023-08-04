package com.wdcftgg.spacetime.mods.jei.recipes;

import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.mods.jei.Compat;
import com.wdcftgg.spacetime.mods.jei.Wrapper;
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
public class CompatTimeCrack extends Compat {

    public CompatTimeCrack() {
        super("spacetime");
    }

    @Override
    public void addRecipes(List<Wrapper> list) {
        list.add(new Wrapper("spacetime.jei.timecrack", STItems.TEMPORALBRASSINGOT.getDefaultInstance(), ModItems.ingotTemporal.getDefaultInstance(), ModItems.brassIngot.getDefaultInstance()));
        list.add(new Wrapper("spacetime.jei.timecrack", new ItemStack(Items.GOLDEN_APPLE, 1, 1), new ItemStack(Items.GOLD_INGOT, 64, 0) , Items.ENCHANTED_BOOK.getDefaultInstance(), Items.GOLDEN_APPLE.getDefaultInstance()));
        list.add(new Wrapper("spacetime.jei.timecrack", new ItemStack(STItems.SUPERGOLDENAPPLE), new ItemStack(Blocks.GOLD_BLOCK, 64, 0) , Items.ENCHANTED_BOOK.getDefaultInstance(), new ItemStack(Items.GOLDEN_APPLE, 1, 1)));
    }

}
