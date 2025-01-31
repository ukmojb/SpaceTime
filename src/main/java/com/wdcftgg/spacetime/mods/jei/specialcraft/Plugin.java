package com.wdcftgg.spacetime.mods.jei.specialcraft;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:27
 */

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JEIPlugin
public class Plugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        List<Wrapper> list = new ArrayList<>();
        List<Compat> compats = new ArrayList<>();

        compats.add(new CompatSpecialCraft());

        Collections.sort(compats);

        for (Compat com : compats) com.addRecipes(list);

        registry.addRecipes(list, "minecraft.crafting");
        registry.handleRecipes(ShapedRecipes.class, (recipe) -> {
            return new ShapedRecipesWrapper(registry.getJeiHelpers(), recipe);
        }, "minecraft.crafting");
        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerWorkbench.class, "minecraft.crafting", 1, 9, 10, 36);
    }
}
