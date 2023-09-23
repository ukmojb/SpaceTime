package com.wdcftgg.spacetime.mods.jei.timealtar;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:26
 */

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

    protected List<Integer> energy;
    protected int num;

    private Wrapper(int num, String info, ItemStack... input) {
        this.info = info;
        this.num = num;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    private Wrapper(int num, String info, List<ItemStack> input) {
        this.info = info;
        this.num = num;
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

    public Wrapper(int num, List<Integer> energy, String info, ItemStack out, ItemStack... input) {
        this(num, info, input);
        this.outItem = out;
        this.energy = energy;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    public Wrapper(int num, List<Integer> energy, String info, ItemStack out, List<ItemStack> input) {
        this(num, info, input);
        this.info = info;
        this.outItem = out;
        this.energy = energy;
        in = new ArrayList<>();
        for (ItemStack stack : input) in.add(Collections.singletonList(stack));
    }

    public Wrapper(int num,  String info, ItemStack out, List<ItemStack> input) {
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


        for (int i=0;i<4;i++) {
            minecraft.fontRenderer.drawString(hourglassstr[i] + "：" + (energy == null ? 0 : energy.get(i)), x - 45, y + 30 + 15 * i, Color.GRAY.getRGB());
        }
        for (int i=0;i<4;i++) {
            minecraft.fontRenderer.drawString(hourglassstr[i + 4] + "：" + (energy == null ? 0 : energy.get(i + 4)), x + 22, y + 30 + 15 * i, Color.GRAY.getRGB());
        }
    }

    private final String[] hourglassstr = new String[]{
            I18n.format("spacetime.altar.air"),
            I18n.format("spacetime.altar.death"),
            I18n.format("spacetime.altar.earth"),
            I18n.format("spacetime.altar.fire"),
            I18n.format("spacetime.altar.life"),
            I18n.format("spacetime.altar.light"),
            I18n.format("spacetime.altar.moon"),
            I18n.format("spacetime.altar.water")
    };
}
