package com.wdcftgg.spacetime.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;

import javax.annotation.Nullable;
import java.util.Arrays;

public class STBiomeProvider extends BiomeProviderSingle {
    public STBiomeProvider() {
        super(Biomes.DESERT);
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
        if (biomes == null || biomes.length < width * height) {
            biomes = new Biome[width * height];
        }

        Arrays.fill(biomes, Biomes.DESERT);
        return biomes;
    }

    @Override
    public Biome getBiome(BlockPos pos, Biome defaultBiome) {
        return Biomes.DESERT;
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int height, boolean cacheFlag) {
        if (listToReuse == null || listToReuse.length < width * height) {
            listToReuse = new Biome[width * height];
        }

        Arrays.fill(listToReuse, Biomes.DESERT);
        return listToReuse;
    }
}