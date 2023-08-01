package com.wdcftgg.spacetime.mods.jei;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:27
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.mods.jei.recipes.CompatTimeCrack;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class Plugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        List<Wrapper> list = new ArrayList<>();
        List<Compat> compats = new ArrayList<>();

        compats.add(new CompatTimeCrack());

        //Sort them alphabetically by modid
        Collections.sort(compats);

        for (Compat com : compats) if (com.shouldLoad()) com.addRecipes(list);
        registry.addRecipes(list, SpaceTime.MODID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        registry.addRecipeCategories(new Category(registry.getJeiHelpers().getGuiHelper()));
    }

}
