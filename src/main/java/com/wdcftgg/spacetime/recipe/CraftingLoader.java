package com.wdcftgg.spacetime.recipe;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 21:50
 */

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.item.STItems;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lumaceon.mods.clockworkphase.init.ModBlocks;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.recipe.Recipes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CraftingLoader
{

    private static TIntSet usedHashes = new TIntHashSet();

    private static Block[] STExtractor = new Block[]{
            STBlocks.LifeSTExtractor,
            STBlocks.LightSTExtractor,
            STBlocks.WaterSTExtractor,
            STBlocks.EarthSTExtractor,
            STBlocks.AirSTExtractor,
            STBlocks.FireSTExtractor,
            STBlocks.MoonSTExtractor,
            STBlocks.DeathSTExtractor
    };

    private static Block[] Extractor = new Block[]{
            ModBlocks.extractorsElements[0],
            ModBlocks.extractorsElements[1],
            ModBlocks.extractorsElements[2],
            ModBlocks.extractorsElements[3],
            ModBlocks.extractorsElements[4],
            ModBlocks.extractorsElements[5],
            ModBlocks.extractorsElements[6],
            ModBlocks.extractorsElements[7]
    };

    public static void init() {
        initMachineRecipes();
    }

    public static void initMachineRecipes() {
        addShapedRecipe(new ItemStack(STItems.SPACETIMEINGOT), new Object[]{"AA", "AA", 'A', STItems.SPACETIMENUGGET});
        addShapedRecipe(new ItemStack(STItems.SPACETIMENUGGET), new Object[]{"AA", "AA", 'A', STItems.SPACETIMEDUST});
        addShapedRecipe(new ItemStack(STItems.MODULEXP), new Object[]{"ADA", "BCB", "ADA", 'C', new ItemStack(ModItems.temporalCoreSedate), 'B', new ItemStack(ModItems.ingotTemporal), 'A', new ItemStack(Blocks.BOOKSHELF), 'D', new ItemStack(Blocks.EMERALD_BLOCK)});

        addShapedRecipe(new ItemStack(STBlocks.TIMEALTARCORE), new Object[]{"BAB", "ACA","BAB", 'C', new ItemStack(ModBlocks.celestialCompass), 'B', new ItemStack(ModItems.chipTemporal), 'A', STItems.TEMPORALBRASSINGOT});
        addShapedRecipe(new ItemStack(STBlocks.CONCRETIZATIONHOURGLASS), new Object[]{" A ", "DCD","BBB", 'C', new ItemStack(ModItems.hourglass), 'B', Items.GOLD_INGOT, 'A', new ItemStack(Blocks.END_ROD),'D', new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)});
        addShapedRecipe(new ItemStack(STBlocks.TIMECOMPRESSOR), new Object[]{"BAB", "ACA","BAB", 'C', new ItemStack(ModItems.gearChronosphere), 'B', new ItemStack(ModBlocks.brassBlock), 'A', new ItemStack(ModItems.ingotTemporal)});

        for (int i=0;i<8;i++) {
            addShapedRecipe(new ItemStack(STExtractor[i]), new Object[]{"BAB", "DCD","BBB", 'C', new ItemStack(Extractor[i]), 'B', Items.PAPER, 'A', new ItemStack(ModItems.temporalCoreActive),'D', new ItemStack(Items.LEATHER)});
        }

    }

    public static void addShapedRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapedRecipe(new ResourceLocation("spacetime", getName(output.getItem())), (ResourceLocation)null, output, params);
    }

    public static void addRecipe(IRecipe recipe) {
        ForgeRegistries.RECIPES.register(recipe.setRegistryName(new ResourceLocation("spacetime", getName(recipe.getRecipeOutput().getItem()))));
    }

    private static String getName(Item item) {
        int hash;
        for(hash = item.getRegistryName().hashCode(); usedHashes.contains(hash); ++hash) {
        }

        usedHashes.add(hash);
        return "spacetime_" + hash;
    }
}
