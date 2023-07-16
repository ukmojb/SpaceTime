package com.wdcftgg.spacetime.recipe;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 21:50
 */
import com.wdcftgg.spacetime.item.ModItems;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lumaceon.mods.clockworkphase.recipe.Recipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingLoader extends Recipes
{

    public static void init() {
        initMachineRecipes();
    }

    private static final ResourceLocation EMPTY = new ResourceLocation("");

    public static void initMachineRecipes() {
        addRecipe(new ShapedOreRecipe(EMPTY, new ItemStack(ModItems.SPACETIMEINGOT), new Object[]{"AA", "AA", 'A', ModItems.SPACETIMENUGGET}));
        addRecipe(new ShapedOreRecipe(EMPTY, new ItemStack(ModItems.SPACETIMENUGGET), new Object[]{"AA", "AA", 'A', ModItems.SPACETIMEDUST}));
    }
}
