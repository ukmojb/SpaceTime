package com.wdcftgg.spacetime.recipe;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 21:50
 */
import com.wdcftgg.spacetime.blocks.ModBlocks;
import com.wdcftgg.spacetime.entity.EntityTimeCrack;
import com.wdcftgg.spacetime.item.ModItems;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lumaceon.mods.clockworkphase.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
        addShapedRecipe(new ItemStack(ModItems.SPACETIMEINGOT), new Object[]{"AA", "AA", 'A', ModItems.SPACETIMENUGGET});
        addShapedRecipe(new ItemStack(ModItems.SPACETIMENUGGET), new Object[]{"AA", "AA", 'A', ModItems.SPACETIMEDUST});

        addShapedRecipe(new ItemStack(ModBlocks.TIMEALTARCORE), new Object[]{"BAB", "ACA","BAB", 'C', new ItemStack(lumaceon.mods.clockworkphase.init.ModBlocks.celestialCompass), 'B', new ItemStack(lumaceon.mods.clockworkphase.init.ModItems.chipTemporal), 'A', ModItems.TEMPORALBRASSINGOT});
        addShapedRecipe(new ItemStack(ModBlocks.CONCRETIZATIONHOURGLASS), new Object[]{" A ", "DCD","BBB", 'C', new ItemStack(lumaceon.mods.clockworkphase.init.ModItems.hourglass), 'B', Items.GOLD_INGOT, 'A', new ItemStack(Blocks.END_ROD),'D', new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)});
    }
}
