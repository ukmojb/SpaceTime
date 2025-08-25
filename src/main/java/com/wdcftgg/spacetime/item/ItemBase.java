package com.wdcftgg.spacetime.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item {
    private String translationKey;

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return "item.spacetime." + this.translationKey;
    }

    @Override
    public Item setTranslationKey(String key)
    {
        this.translationKey = key;
        return this;
    }
}
