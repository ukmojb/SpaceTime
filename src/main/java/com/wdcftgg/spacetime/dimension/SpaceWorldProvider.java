package com.wdcftgg.spacetime.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/17 14:36
 */
public final class SpaceWorldProvider extends WorldProvider {
    @Override
    public final DimensionType getDimensionType() {
        return DimensionType.THE_END;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public boolean isDaytime()
    {
        return false;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
        return false;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorSpace(world);
    }
}
