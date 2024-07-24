package com.wdcftgg.spacetime.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/4 9:33
 */
@Mod.EventBusSubscriber
public class EventTimeBack {

    public static final List<BufferedImage> images = new ArrayList<>();
    public static boolean isBack = false;

    @SubscribeEvent
    public void onOverlayRender(final RenderGameOverlayEvent.Post event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET)
            return;

        Minecraft mc = Minecraft.getMinecraft();

        ResourceLocation res;
        if (Minecraft.getMinecraft().player != null) {
            BufferedImage image;
            if (!isBack) {
                if (!images.isEmpty()) {
                    image = (BufferedImage)images.get(images.size() - 1);
                    images.remove(images.size() - 1);
                    int zLevel = 0;
                    int texture = TextureUtil.glGenTextures();

                    int scalex = (mc.gameSettings.guiScale != 0  ? ((int) mc.displayWidth / mc.gameSettings.guiScale) : 0);
                    if (mc.gameSettings.guiScale == 3) scalex = mc.displayWidth / 2;

                    int scaley = (mc.gameSettings.guiScale != 0  ? ((int) mc.displayHeight / mc.gameSettings.guiScale) : 0);
                    if (mc.gameSettings.guiScale == 3) scaley = mc.displayHeight / 2;

                    TextureUtil.uploadTextureImage(texture, image);
                    GlStateManager.color(1f, 1f, 1f, 0.7f);
                    GlStateManager.bindTexture(texture);
                    Tessellator tessellator = Tessellator.getInstance();

                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

                    bufferbuilder.pos(0, (float) (!mc.isFullScreen() ? mc.displayHeight * 0.5 : (mc.displayHeight * 0.5 + scaley)/2), -90).tex(0, 1).endVertex();
                    bufferbuilder.pos((float) (!mc.isFullScreen() ? mc.displayWidth * 0.5 : (mc.displayWidth * 0.5 + scalex)/2), (float) (!mc.isFullScreen() ? mc.displayHeight * 0.5 : (mc.displayHeight * 0.5 + scaley)/2), -90).tex(1, 1).endVertex();
                    bufferbuilder.pos((float) (!mc.isFullScreen() ? mc.displayWidth * 0.5 : (mc.displayWidth * 0.5 + scalex)/2), 0, -90).tex(1, 0).endVertex();
                    bufferbuilder.pos(0, 0, -90).tex(0, 0).endVertex();
                    tessellator.draw();
                    TextureUtil.deleteTexture(texture);
                }
            } else {
                if (mc.world.getTotalWorldTime() % 2 == 0){
                    image = ScreenShotHelper.createScreenshot(mc.displayWidth / 2, mc.displayHeight / 2, mc.getFramebuffer());
                    images.add(image);
                    if (images.size() > 200) {
                        BufferedImage lastimage = (BufferedImage) images.get(0);
                        images.remove(lastimage);
                    }
                }
            }
        }
    }
}
