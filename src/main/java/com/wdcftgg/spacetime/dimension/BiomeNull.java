package com.wdcftgg.spacetime.dimension;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.world.biome.Biome;

public class BiomeNull extends Biome {
    public BiomeNull() {
        super(new BiomeProperties(SpaceTime.MODID + "_null").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
    }
    
}