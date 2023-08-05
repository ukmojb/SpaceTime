package com.wdcftgg.spacetime.mods.jei.timealtar;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:26
 */

import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import lumaceon.mods.clockworkphase.util.TimeSandParser;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Wrapper implements IRecipeWrapper {
    protected List<List<ItemStack>> in;
    protected ItemStack outItem;
    protected String info;
    protected int num;

    private Wrapper(int num, String info, ItemStack... input) {
        this.info = info;
        this.num = num;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    private Wrapper(int num, String info, List<ItemStack> input) {
        this.info = info;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    //Items in, item out
    public Wrapper(int num, String info, ItemStack out, ItemStack... input) {
        this(num, info, input);
        this.outItem = out;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    public Wrapper(int num, String info, ItemStack out, List<ItemStack> input) {
        this(num, info, input);
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

        String texttime = TimeSandParser.getStringForRenderingFromTimeSand(num);

        int width = minecraft.fontRenderer.getStringWidth(text);
        int x = (recipeWidth - width) / 2;
        int y = 35;

        minecraft.fontRenderer.drawString(text, x - 30, y, Color.GRAY.getRGB());
        minecraft.fontRenderer.drawString(texttime, x + 12, y, Color.GRAY.getRGB());
    }
}
