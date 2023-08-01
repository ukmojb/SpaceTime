package com.wdcftgg.spacetime.mods.jei;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:26
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreIngredient;


public class Wrapper implements IRecipeWrapper {
    protected List<List<ItemStack>> in;
    protected ItemStack outItem;
    protected String info;

    private Wrapper(String info, ItemStack... input) {
        this.info = info;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    private Wrapper(String info, List<ItemStack> input) {
        this.info = info;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    //Items in, item out
    public Wrapper(String info, ItemStack out, ItemStack... input) {
        this(info, input);
        this.outItem = out;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    public Wrapper(String info, ItemStack out, List<ItemStack> input) {
        this(info, input);
        this.outItem = out;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, outItem);
        ingredients.setInputLists(VanillaTypes.ITEM, in);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (info == null) return;

        String text = I18n.format(info);

        int width = minecraft.fontRenderer.getStringWidth(text);
        int x = (recipeWidth - width) / 2;
        int y = 35;

        minecraft.fontRenderer.drawString(text, x, y, Color.GRAY.getRGB());
    }
}
