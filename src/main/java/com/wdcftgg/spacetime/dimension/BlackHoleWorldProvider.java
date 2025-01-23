package com.wdcftgg.spacetime.dimension;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

public class BlackHoleWorldProvider extends WorldProvider {
    @Override
    public final DimensionType getDimensionType() {
        return SpaceTime.BlackHoleDim;
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

    @SideOnly(Side.CLIENT)
    @Override
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
        return new ChunkGeneratorBlackHole(world);
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer()
    {
        if (Config.RENDERBLACKHOLEMODE == 3) return null;
        Tessellator tessellator = Tessellator.getInstance();
        Minecraft mc = Minecraft.getMinecraft();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enablePolygonOffset();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(1.24F * 360.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0, -5, 0);
        GlStateManager.doPolygonOffset(-1, 0);

        ResourceLocation BlackHole_TEXTURES = new ResourceLocation(SpaceTime.MODID, "textures/environment/blackholedim_" + Config.RENDERBLACKHOLEMODE + ".png");;

        float xz = 100.0F;
        float y = 80.F;
        mc.getTextureManager().bindTexture(BlackHole_TEXTURES);
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(-xz), y, (double)(-xz)).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos((double)xz, y, (double)(-xz)).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos((double)xz, y, (double)xz).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)(-xz), y, (double)xz).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();
        GlStateManager.disablePolygonOffset();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.popMatrix();
        return null;
    }
}

