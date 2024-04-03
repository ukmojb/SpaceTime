package com.wdcftgg.spacetime.client.render;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.model.BlackHoleModel;
import com.wdcftgg.spacetime.entity.EntityBlackHole;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderBlackHole extends RenderLiving<EntityBlackHole> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(SpaceTime.MODID, "textures/entity/blackhole.png");
    public ResourceLocation hole = new ResourceLocation(SpaceTime.MODID, "textures/entity/blackhole.png");
    public ResourceLocation swirl = new ResourceLocation(SpaceTime.MODID, "textures/entity/bhole.png");
    public ResourceLocation disc = new ResourceLocation(SpaceTime.MODID, "textures/entity/bholedisc.png");

    public RenderBlackHole(RenderManager renderManager)
    {
        super(renderManager, new BlackHoleModel(), 0.5F);
    }


    protected ResourceLocation getEntityTexture(EntityBlackHole entity) {
        return TEXTURES;
    }

    @Override
    public void doRender(EntityBlackHole entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

        float size;

        size = entity.getDataManager().get(EntityBlackHole.BlackHole_Size);

//        System.out.println(size + "--");

        GL11.glScalef(size, size, size);

//        bindTexture(hole);
//        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//        Tessellator.getInstance().getBuffer().pos(-1, -1, 0).tex(0, 0).endVertex();
//        Tessellator.getInstance().getBuffer().pos(1, -1, 0).tex(1, 0).endVertex();
//        Tessellator.getInstance().getBuffer().pos(1, 1, 0).tex(1, 1).endVertex();
//        Tessellator.getInstance().getBuffer().pos(-1, 1, 0).tex(0, 1).endVertex();
//        Tessellator.getInstance().draw();

        renderDisc(entity, partialTicks);
        renderJets(entity, partialTicks);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

    protected ResourceLocation discTex() {
        return this.disc;
    }

    protected void renderDisc(Entity entity, float partialTicks) {

        float glow = 0.75F;

        bindTexture(discTex());

        GL11.glPushMatrix();
        GL11.glRotatef(entity.getEntityId() % 90 - 45, 1, 0, 0);
        GL11.glRotatef(entity.getEntityId() % 360, 0, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        Tessellator tess = Tessellator.getInstance();

        int count = 16;

        Vec3d vec = new Vec3d(1, 0, 0);

        for (int k = 0; k < steps(); k++) {

            GL11.glPushMatrix();
            GL11.glRotatef((entity.ticksExisted + partialTicks) * -((float) Math.pow(k + 1, 1.25)), 0, 1, 0);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            double s = 3 - k * 0.175D;

            for (int j = 0; j < 2; j++) {

                tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                for (int i = 0; i < count; i++) {

                    if (j == 0)
                        setColorFromIteration(tess, k, 1F);
                    else
                        tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                    tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();
                    setColorFromIteration(tess, k, 0F);
                    tess.getBuffer().pos(vec.x * s * 2, 0, vec.z * s * 2).tex(0.5 + vec.x * 0.5, 0.5 + vec.z * 0.5).endVertex();

                    vec = vec.rotateYaw((float) Math.PI * 2 / count);
                    setColorFromIteration(tess, k, 0F);
                    tess.getBuffer().pos(vec.x * s * 2, 0, vec.z * s * 2).tex(0.5 + vec.x * 0.5, 0.5 + vec.z * 0.5).endVertex();

                    if (j == 0)
                        setColorFromIteration(tess, k, 1F);
                    else
                        tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                    tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();
                }
                tess.draw();

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            }

            GL11.glPopMatrix();
        }

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
    }

    protected int steps() {
        return 15;
    }

    protected void setColorFromIteration(Tessellator tess, int iteration, float alpha) {

        if (iteration < 5) {
            float g = 0.125F + iteration * (1F / 10F);
            tess.getBuffer().color(1.0F, g, 0.0F, alpha);
            return;
        }

        if (iteration == 5) {
            tess.getBuffer().color(1.0F, 1.0F, 0.0F, alpha);
            return;
        }

        if (iteration > 5) {

            int i = iteration - 6;
            float r = 1.0F - i * (1F / 9F);
            float g = 1F - i * (1F / 9F);
            float b = i * (1F / 5F);
            tess.getBuffer().color(r, g, b, alpha);
        }
    }

    protected void renderSwirl(Entity entity, float interp) {

        float glow = 0.75F;

        bindTexture(swirl);

        GL11.glPushMatrix();
        GL11.glRotatef(entity.getEntityId() % 90 - 45, 1, 0, 0);
        GL11.glRotatef(entity.getEntityId() % 360, 0, 1, 0);
        GL11.glRotatef((entity.ticksExisted + interp % 360) * -5, 0, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Vec3d vec = new Vec3d(1, 0, 0);

        Tessellator tess = Tessellator.getInstance();

        double s = 3;
        int count = 16;

        // swirl, inner part (solid)
        for (int j = 0; j < 2; j++) {
            tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            for (int i = 0; i < count; i++) {

                tess.getBuffer().color(0.0F, 0.0F, 0.0F, 1.0F);
                tess.getBuffer().pos(vec.x * 0.9, 0, vec.z * 0.9).tex(0.5 + vec.x * 0.25 / s * 0.9, 0.5 + vec.z * 0.25 / s * 0.9).endVertex();

                if (j == 0)
                    setColorFull(entity, tess);
                else
                    tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();

                vec = vec.rotateYaw((float) Math.PI * 2 / count);

                if (j == 0)
                    setColorFull(entity, tess);
                else
                    tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();

                tess.getBuffer().color(0.0F, 0.0F, 0.0F, 1.0F);
                tess.getBuffer().pos(vec.x * 0.9, 0, vec.z * 0.9).
                        tex(0.5 + vec.x * 0.25 / s * 0.9, 0.5 + vec.z * 0.25 / s * 0.9).endVertex();
            }
            tess.draw();

            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }

        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        // swirl, outer part (fade)
        for (int j = 0; j < 2; j++) {

            tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            for (int i = 0; i < count; i++) {

                if (j == 0)
                    setColorFull(entity, tess);
                else
                    tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();
                setColorNone(entity, tess);
                tess.getBuffer().pos(vec.x * s * 2, 0, vec.z * s * 2).tex(0.5 + vec.x * 0.5, 0.5 + vec.z * 0.5).endVertex();

                vec = vec.rotateYaw((float) Math.PI * 2 / count);
                setColorNone(entity, tess);
                tess.getBuffer().pos(vec.x * s * 2, 0, vec.z * s * 2).tex(0.5 + vec.x * 0.5, 0.5 + vec.z * 0.5).endVertex();

                if (j == 0)
                    setColorFull(entity, tess);
                else
                    tess.getBuffer().color(1.0F, 1.0F, 1.0F, glow);

                tess.getBuffer().pos(vec.x * s, 0, vec.z * s).tex(0.5 + vec.x * 0.25, 0.5 + vec.z * 0.25).endVertex();
            }
            tess.draw();

            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        GL11.glPopMatrix();
    }

    protected void renderJets(Entity entity, float interp) {

        Tessellator tess = Tessellator.getInstance();

        GL11.glPushMatrix();
        GL11.glRotatef(entity.getEntityId() % 90 - 45, 1, 0, 0);
        GL11.glRotatef(entity.getEntityId() % 360, 0, 1, 0);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        for (int j = -1; j <= 1; j += 2) {
            tess.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

            tess.getBuffer().pos(0, 0, 0).color(1.0F, 1.0F, 1.0F, 0.35F).endVertex();

            Vec3d jet = new Vec3d(0.5, 0, 0);

            for (int i = 0; i <= 12; i++) {

                tess.getBuffer().pos(jet.x, 10 * j, jet.z).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
                jet = jet.rotateYaw((float) Math.PI / 6 * -j);
            }

            tess.draw();
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
    }

    protected void renderFlare(Entity entity) {
        GL11.glPushMatrix();
        GL11.glScalef(0.2F, 0.2F, 0.2F);

        Tessellator tessellator = Tessellator.getInstance();
        RenderHelper.disableStandardItemLighting();
        int j = 75;
        float f1 = (j + 2.0F) / 200.0F;
        float f2 = 0.0F;
        int count = 250;

        count = j;

        if (f1 > 0.8F) {
            f2 = (f1 - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);

        for (int i = 0; i < count; i++) {
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
            tessellator.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
            setColorFull(entity, tessellator);
            tessellator.getBuffer().pos(0.0D, 0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            setColorNone(entity, tessellator);
            tessellator.getBuffer().pos(-0.866D * f4, f3, -0.5F * f4).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.getBuffer().pos(0.866D * f4, f3, -0.5F * f4).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.getBuffer().pos(0.0D, f3, 1.0F * f4).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.getBuffer().pos(-0.866D * f4, f3, -0.5F * f4).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

    protected void setColorFull(Entity e, Tessellator tessellator) {
            tessellator.getBuffer().color(1.0F, 0.725F, 0.0F, 1.0F);
    }

    protected void setColorNone(Entity e, Tessellator tessellator) {
            tessellator.getBuffer().color(1.0F, 0.725F, 0.0F, 0.0F);
    }
}
