package com.wdcftgg.spacetime.mods.jei.timealtar;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:27
 */

import com.wdcftgg.spacetime.SpaceTime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JEIPlugin
public class Plugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        List<Wrapper> list = new ArrayList<>();
        List<Compat> compats = new ArrayList<>();

        compats.add(new CompatTimeAltar());

        //Sort them alphabetically by modid
        Collections.sort(compats);

        for (Compat com : compats) com.addRecipes(list);
        registry.addRecipes(list, SpaceTime.MODID + "timealtar");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        registry.addRecipeCategories(new Category(registry.getJeiHelpers().getGuiHelper()));
    }

}
