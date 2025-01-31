package com.wdcftgg.spacetime.dimension;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/9/17 14:36
 */
public final class SpaceWorldProvider extends WorldProvider {

    public static List<EntityPlayerMP> playerList = new ArrayList<>();

    @Override
    public DimensionType getDimensionType() {
        return SpaceTime.SpaceDim;
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

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return false;
    }

    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        super.setAllowedSpawnTypes(false, false);
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        int i = 10518688;
        float f = MathHelper.cos(p_76562_1_ * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.627451F;
        float f2 = 0.5019608F;
        float f3 = 0.627451F;
        f1 = f1 * (f * 0.0F + 0.15F);
        f2 = f2 * (f * 0.0F + 0.15F);
        f3 = f3 * (f * 0.0F + 0.15F);
        return new Vec3d((double)f1, (double)f2, (double)f3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(net.minecraft.entity.Entity cameraEntity, float partialTicks)
    {
        return new Vec3d(0,0,0);
    }

    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1)
    {
        return 8;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return new BiomeProviderSingle(Biomes.VOID);
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }


    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer()
    {
        return null;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorSpace(world);
    }

    @Override
    public void onPlayerAdded(EntityPlayerMP player)
    {
        playerList.add(player);
    }

    @Override
    public void onPlayerRemoved(EntityPlayerMP player)
    {
        playerList.remove(player);
    }

    public static List<EntityPlayerMP> getPlayerList() {
        return playerList;
    }
}
