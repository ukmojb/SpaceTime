package com.wdcftgg.spacetime.mods.jei;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/19 16:19
 */

import java.util.List;

import com.wdcftgg.spacetime.SpaceTime;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class Category implements IRecipeCategory<Wrapper> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(SpaceTime.MODID, "textures/gui/jei.png");
    public static final ResourceLocation ICON = new ResourceLocation(SpaceTime.MODID, "textures/gui/icon.png");
    private IDrawable background, icon;

    public Category(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(BACKGROUND, 0, 0, 140, 32).addPadding(0, 12, 0, 0).build();
        icon = guiHelper.drawableBuilder(ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public String getUid() {
        return SpaceTime.MODID;
    }

    @Override
    public String getTitle() {
        return I18n.format(SpaceTime.MODID + ".category");
    }

    @Override
    public String getModName() {
        return SpaceTime.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        //Output
        int offset1 = outputs.size() - 1;
        for (int i = 0; i <= offset1; i++) guiItemStacks.init(i, false, 82 + (offset1 - i) * 18, 9);
        //Input
        int offset0 = inputs.size() - 1;
        for (int i = 0; i <= offset0; i++) guiItemStacks.init(i + 5, true, 36 - (offset0 - i) * 18, 9);

        guiItemStacks.set(ingredients);
    }
}
