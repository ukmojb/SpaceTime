package com.wdcftgg.spacetime.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

    private String translationKey;

    public BlockBase(Material materialIn) {
        super(materialIn);
    }

    public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public String getTranslationKey()
    {
        return "tile.spacetime." + this.translationKey;
    }

    @Override
    public Block setTranslationKey(String key)
    {
        this.translationKey = key;
        return this;
    }
}
