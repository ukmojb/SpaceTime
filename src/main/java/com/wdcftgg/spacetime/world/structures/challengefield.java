package com.wdcftgg.spacetime.world.structures;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.Config;
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
 * @create 2023/11/11 14:52
 */
public class challengefield implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        Template template = manager.getTemplate(server, new ResourceLocation(SpaceTime.MODID, "challengefield"));

        if (template == null){
            SpaceTime.Log("challengefield template is null!");
            
            return;
        }

        if (chunkX == 0 && chunkZ == 0 && world.provider.getDimension() == Config.SPACEDDIM){
            BlockPos pos = new BlockPos(39, 73, -21);
            template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
            SpaceTime.Log("challengefield saved");
        }
    }
}
