package com.wdcftgg.spacetime.dimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;

public class ChunkGeneratorBlackHole implements IChunkGenerator {

    private final World world;
    public ChunkGeneratorBlackHole(World world) {
        this.world = world;
    }

    /**
     * Generates the chunk at the specified position, from scratch
     *
     * @param x
     * @param z
     */
    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < 5; ++i)
        {
            IBlockState iblockstate = Blocks.BARRIER.getDefaultState();

            if (iblockstate != null)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        return chunk;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     *
     * @param x Chunk x coordinate
     * @param z Chunk z coordinate
     */
    @Override
    public void populate(int x, int z) {

    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     *
     * @param chunkIn
     * @param x
     * @param z
     */
    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return true;
    }

    /**
     * @param creatureType
     * @param pos
     * @return
     */
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    /**
     * @param worldIn
     * @param structureName
     * @param position
     * @param findUnexplored
     * @return
     */
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     *
     * @param chunkIn
     * @param x
     * @param z
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    /**
     * @param worldIn
     * @param structureName
     * @param pos
     * @return
     */
    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}

