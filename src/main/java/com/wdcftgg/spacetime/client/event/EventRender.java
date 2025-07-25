package com.wdcftgg.spacetime.client.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntityTimePhantom;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.SpaceFrozenHelper;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.util.InventorySearchHelper;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import lumaceon.mods.clockworkphase.util.TimeSandParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/23 20:33
 */


@Mod.EventBusSubscriber(Side.CLIENT)
public class EventRender {

    private final Minecraft mc = Minecraft.getMinecraft();

    public static RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

    @SubscribeEvent
    public void onOverlayRender(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET) return;


        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        EntityPlayer player = Minecraft.getMinecraft().player;

        //时间祭坛的显示
        if (player != null) {
            Vec3d start  = player.getPositionEyes(event.getPartialTicks());
            Vec3d vec31 = player.getLook(event.getPartialTicks());
            Vec3d end = start.add(vec31.x * 6, vec31.y * 6, vec31.z * 6);

            RayTraceResult result = player.getEntityWorld().rayTraceBlocks(start, end, false);

            if (result != null && result.getBlockPos() != null) {

                TileEntity te = player.world.getTileEntity(result.getBlockPos());

                if (te != null) {
                    if (te instanceof TimeAltarCoreEntity) {
                        TimeAltarCoreEntity timeAltarCore = (TimeAltarCoreEntity) te;
                        if (timeAltarCore.getTimeSand() >= 0) {
                            GlStateManager.pushMatrix();
                            String text = TimeSandParser.getStringForRenderingFromTimeSand(timeAltarCore.getTimeSand());
                            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;


                            GlStateManager.disableLighting();
                            GlStateManager.enablePolygonOffset();

                            fontRenderer.drawString(text, Config.GUIPOSX, Config.GUIPOSY, 0xFFFFFFFF);

                            GlStateManager.enableLighting();
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }

            //时宗抹杀
            if (Minecraft.getMinecraft().player.getActivePotionEffect(ModPotions.heterospace) != null) {

                GlStateManager.pushMatrix();

                ScaledResolution resolution = new ScaledResolution(mc);

                int screenWidth = resolution.getScaledWidth();
                int screenHeight = resolution.getScaledHeight();

                ResourceLocation res = new ResourceLocation(SpaceTime.MODID, "textures/gui/heterospace.png");

                mc.getTextureManager().bindTexture(res);
                GlStateManager.color(148 / 255f, 22 / 255f, 232 / 255f, 0.25f);//RGBA
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(0, screenHeight, -90).tex(0, 1).endVertex();
                buffer.pos(screenWidth, screenHeight, -90).tex(1, 1).endVertex();
                buffer.pos(screenWidth, 0, -90).tex(1, 0).endVertex();
                buffer.pos(0, 0, -90).tex(0, 0).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }

            //生命和死亡模块
            ItemStack[] pocketWatches = InventorySearchHelper.getPocketWatches(player.inventory);
            if (pocketWatches != null && ItemPocketWatch.doesActiveItemModuleExist(pocketWatches, ModItems.moduleLifeWalk)) {
                ItemStack lifeWalk = ItemPocketWatch.getItemModuleFromMultiple(pocketWatches, ModItems.moduleLifeWalk);
                int lifeModulePower = (int) NBTHelper.getInt(lifeWalk, "module_power");
                for (int i = 0; i < lifeModulePower / 50; i++) {
                    GlStateManager.pushMatrix();
                    ScaledResolution resolution = new ScaledResolution(mc);

                    int screenWidth = resolution.getScaledWidth();
                    int screenHeight = resolution.getScaledHeight();

                    ResourceLocation res = new ResourceLocation(SpaceTime.MODID, "textures/gui/life.png");
                    double y1 = screenHeight * 0.02 + i * screenHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? screenHeight * 0.047 : 0);
                    double y0 = screenHeight * 0.001 + i * screenHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? screenHeight * 0.047 : 0);

//                int scale = (mc.gameSettings.guiScale != 0  ? ((int) mc.displayWidth / mc.gameSettings.guiScale) : 0);
//                if (mc.gameSettings.guiScale == 3) scale = mc.displayWidth / 2;

                    mc.getTextureManager().bindTexture(res);
                    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(screenWidth * 0.98, y1, -90).tex(0, 1).endVertex();
                    buffer.pos(screenWidth * 0.99, y1, -90).tex(1, 1).endVertex();
                    buffer.pos(screenWidth * 0.99, y0, -90).tex(1, 0).endVertex();
                    buffer.pos(screenWidth * 0.98, y0, -90).tex(0, 0).endVertex();
                    tessellator.draw();

                    GlStateManager.popMatrix();
                }
            }
            if (pocketWatches != null && ItemPocketWatch.doesActiveItemModuleExist(pocketWatches, ModItems.moduleDeathWalk)) {
                ItemStack deathWalk = ItemPocketWatch.getItemModuleFromMultiple(pocketWatches, ModItems.moduleDeathWalk);
                int deathModulePower = (int) NBTHelper.getInt(deathWalk, "module_power");
                for (int i = 0; i < deathModulePower / 50; i++) {
                    GlStateManager.pushMatrix();
                    ScaledResolution resolution = new ScaledResolution(mc);

                    int screenWidth = resolution.getScaledWidth();
                    int screenHeight = resolution.getScaledHeight();

                    ResourceLocation res = new ResourceLocation(SpaceTime.MODID, "textures/gui/death.png");
                    double y1 = screenHeight * 0.02 + i * screenHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? screenHeight * 0.047 : 0);
                    double y0 = screenHeight * 0.001 + i * screenHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? screenHeight * 0.047 : 0);

                    mc.getTextureManager().bindTexture(res);
                    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(screenWidth * 0.99, y1, -90).tex(0, 1).endVertex();
                    buffer.pos(screenWidth, y1, -90).tex(1, 1).endVertex();
                    buffer.pos(screenWidth, y0, -90).tex(1, 0).endVertex();
                    buffer.pos(screenWidth * 0.99, y0, -90).tex(0, 0).endVertex();
                    tessellator.draw();

                    GlStateManager.popMatrix();
                }
            }

            if (SpaceFrozenHelper.getSpaceFrozen(player) > 0) {
                GlStateManager.pushMatrix();

                ScaledResolution resolution = new ScaledResolution(mc);

                int screenWidth = resolution.getScaledWidth();
                int screenHeight = resolution.getScaledHeight();

                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(I18n.format(SpaceTime.MODID + ".spacefrozen"), (float) (screenWidth * 0.455), (float) (screenHeight * 0.75), 0x6c00ec);

                ResourceLocation res = new ResourceLocation(SpaceTime.MODID, "textures/gui/spacefrozen.png");

                mc.getTextureManager().bindTexture(res);
//                  GlStateManager.color(148 / 255f, 22 / 255f, 232 / 255f, 0.25f);//RGBA
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(screenWidth * 0.4, screenHeight * 0.85, -90).tex(0, 1).endVertex();
                buffer.pos(screenWidth * 0.6, screenHeight * 0.85, -90).tex(1, 1).endVertex();
                buffer.pos(screenWidth * 0.6, screenHeight * 0.8, -90).tex(1, 0).endVertex();
                buffer.pos(screenWidth * 0.4, screenHeight * 0.8, -90).tex(0, 0).endVertex();
                tessellator.draw();

                ResourceLocation res1 = new ResourceLocation(SpaceTime.MODID, "textures/gui/spacefrozen_1.png");

                mc.getTextureManager().bindTexture(res1);
//                  GlStateManager.color(148 / 255f, 22 / 255f, 232 / 255f, 0.25f);//RGBA
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(screenWidth * 0.4, screenHeight * 0.85, -90).tex(0, 1).endVertex();
                buffer.pos(screenWidth * 0.4 + screenWidth * 0.2 * Math.min((double) SpaceFrozenHelper.getSpaceFrozen(player) / 100, 1d), screenHeight * 0.85, -90).tex(Math.min((double) SpaceFrozenHelper.getSpaceFrozen(player) / 100, 1d), 1).endVertex();
                buffer.pos(screenWidth * 0.4 + screenWidth * 0.2 * Math.min((double) SpaceFrozenHelper.getSpaceFrozen(player) / 100, 1d), screenHeight * 0.8, -90).tex(Math.min((double) SpaceFrozenHelper.getSpaceFrozen(player) / 100, 1d), 0).endVertex();
                buffer.pos(screenWidth * 0.4, screenHeight * 0.8, -90).tex(0, 0).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }
        }

    }



    @SubscribeEvent
    public void onPreRenderLiving(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.color(148/255f, 22/255f, 232/255f, 0.5f);
                GlStateManager.enableBlend(); // 启用混合模式
                GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }
        }
        if (event.getEntity() instanceof EntityTimePhantom) {
            GlStateManager.color(1f, 1f, 1f, 0.4f);
            GlStateManager.enableBlend(); // 启用混合模式
            GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }


    @SubscribeEvent
    public void onPostRenderLiving(RenderLivingEvent.Post event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.disableBlend();
            }
        }
        if (event.getEntity() instanceof EntityTimePhantom) {
            GlStateManager.disableBlend();
        }
    }
}
