package com.wdcftgg.spacetime.dimension;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.File;
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
    public final DimensionType getDimensionType() {
        return DimensionType.register("space", "_space", Config.SPACEDDIM, SpaceWorldProvider.class, false);
    }

    private static int num = 1;

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
    public boolean canDoRainSnowIce(net.minecraft.world.chunk.Chunk chunk)
    {
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
    public void resetRainAndThunder()
    {
        WorldInfo worldinfo = world.getWorldInfo();

        worldinfo.setCleanWeatherTime(20000);
        worldinfo.setRainTime(0);
        worldinfo.setThunderTime(0);
        worldinfo.setRaining(false);
        worldinfo.setThundering(false);
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
