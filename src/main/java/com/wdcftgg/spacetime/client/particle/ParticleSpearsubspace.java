package com.wdcftgg.spacetime.client.particle;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


public class ParticleSpearsubspace extends Particle
{

    private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation(SpaceTime.MODID, "textures/particles/particle_spearsubspace.png");
    private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    private int life;
    /** The Rendering Engine. */
    private final TextureManager textureManager;

    protected ParticleSpearsubspace(TextureManager textureManagerIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.textureManager = textureManagerIn;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.particleMaxAge = 300;
    }

    /**
     * Renders the particle
     */

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        RenderHelper.disableStandardItemLighting();

        GlStateManager.translate(x, y, z);


        float rotationYaw = Minecraft.getMinecraft().getRenderManager().playerViewY;
        float rotationPitch = Minecraft.getMinecraft().getRenderManager().playerViewX;


        GlStateManager.rotate(-rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);

        GlStateManager.scale(0.2, 0.2, 0.2);

        this.textureManager.bindTexture(EXPLOSION_TEXTURE);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(-0.5, -0.5, 0).tex(0, 0).color(particleRed, particleGreen, particleBlue, 1.0F).endVertex();
        buffer.pos(-0.5, 0.5, 0).tex(0, 1).color(particleRed, particleGreen, particleBlue, 1.0F).endVertex();
        buffer.pos(0.5, 0.5, 0).tex(1, 1).color(particleRed, particleGreen, particleBlue, 1.0F).endVertex();
        buffer.pos(0.5, -0.5, 0).tex(1, 0).color(particleRed, particleGreen, particleBlue, 1.0F).endVertex();
        Tessellator.getInstance().draw();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public int getBrightnessForRender(float partialTick)
    {
        return 61680;
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }
    }

    public void move(double x, double y, double z)
    {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    /**
     * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
     * 1 for the main Texture atlas, and 3 for a custom texture
     */
    public int getFXLayer()
    {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleSpearsubspace(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }
}