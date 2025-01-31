package com.wdcftgg.spacetime.mods.jei.specialcraft;

import com.wdcftgg.spacetime.item.STItems;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:33
 */
public class CompatSpecialCraft extends Compat {

    @Override
    public void addRecipes(List<Wrapper> list) {
        list.add(new Wrapper(0, I18n.format("spacetime.modulexp.craft"), new ItemStack(STItems.MODULEXP),
                new ItemStack(Blocks.BOOKSHELF),
                new ItemStack(Blocks.EMERALD_BLOCK),
                new ItemStack(Blocks.BOOKSHELF),
                new ItemStack(ModItems.ingotTemporal),
                new ItemStack(ModItems.temporalCoreSedate),
                new ItemStack(ModItems.ingotTemporal),
                new ItemStack(Blocks.BOOKSHELF),
                new ItemStack(Blocks.EMERALD_BLOCK),
                new ItemStack(Blocks.BOOKSHELF)
        ));
    }
}
