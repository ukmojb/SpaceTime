package com.wdcftgg.spacetime.world.structures;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.config;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/11/11 13:45
 */
public class pillar implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        Template template = manager.getTemplate(server, new ResourceLocation(SpaceTime.MODID, "pillar"));

        if (template == null){
            SpaceTime.Log("pillar template is null!");
            return;
        }

        if (chunkX == 0 && chunkZ == 0 && world.provider.getDimension() == config.SPACEDIMID){
            BlockPos pos;
            pos = new BlockPos(-7, 26, -7);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(113, 26, -7);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(53, 26, 53);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(53, 26, -67);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);

            pos = new BlockPos(13, 26, -47);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(13, 26, 33);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(93, 26, -47);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            pos = new BlockPos(93, 26, 33);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            SpaceTime.Log("pillar saved");
        }
    }
}
