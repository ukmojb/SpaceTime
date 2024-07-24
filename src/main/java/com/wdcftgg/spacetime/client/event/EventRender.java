package com.wdcftgg.spacetime.client.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntityTimePhantom;
import com.wdcftgg.spacetime.potion.ModPotions;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.ItemPocketWatch;
import lumaceon.mods.clockworkphase.util.InventorySearchHelper;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import lumaceon.mods.clockworkphase.util.TimeSandParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.Display;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
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

    private int ticks = 1;

    @SubscribeEvent
    public void onOverlayRender(final RenderGameOverlayEvent.Post event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();


        ResourceLocation res;
        if (Minecraft.getMinecraft().player != null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (TimeAltarCoreEntity.TIMESAND < 0) return;
            String text = TimeSandParser.getStringForRenderingFromTimeSand(TimeAltarCoreEntity.TIMESAND);
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

            GlStateManager.pushMatrix();

            GlStateManager.disableLighting();
            GlStateManager.enablePolygonOffset();
            GlStateManager.enableBlend(); //开启混合器(使GL支持Alpha透明通道)

            fontRenderer.drawString(text, Config.GUIPOSX, Config.GUIPOSY, 0xFFFFFFFF);

            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getActivePotionEffect(ModPotions.heterospace) != null){

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();

            res = new ResourceLocation(SpaceTime.MODID, "textures/gui/heterospace.png");

            mc.getTextureManager().bindTexture(res);
            GlStateManager.color(148/255f, 22/255f, 232/255f, 0.25f);//RGBA
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(0, mc.displayHeight * 0.5, -90).tex(0, 1).endVertex();
            buffer.pos(mc.displayWidth * 0.5, mc.displayHeight * 0.5, -90).tex(1, 1).endVertex();
            buffer.pos(mc.displayWidth * 0.5, 0, -90).tex(1, 0).endVertex();
            buffer.pos(0, 0, -90).tex(0, 0).endVertex();
            tessellator.draw();
            
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
        EntityPlayer player = Minecraft.getMinecraft().player;
        ItemStack[] pocketWatches = InventorySearchHelper.getPocketWatches(player.inventory);
        if (pocketWatches != null && ItemPocketWatch.doesActiveItemModuleExist(pocketWatches, ModItems.moduleLifeWalk)) {
            ItemStack lifeWalk = ItemPocketWatch.getItemModuleFromMultiple(pocketWatches, ModItems.moduleLifeWalk);
            int lifeModulePower = (int) NBTHelper.getInt(lifeWalk, "module_power");
            for (int i=0;i< lifeModulePower/50;i++) {
                GlStateManager.pushMatrix();

                res = new ResourceLocation(SpaceTime.MODID, "textures/gui/life.png");
                double y1 = mc.displayHeight * 0.02 + i * mc.displayHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? mc.displayHeight * 0.047 : 0);
                double y0 = mc.displayHeight * 0.001 + i * mc.displayHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? mc.displayHeight * 0.047 : 0);


                int scale = (mc.gameSettings.guiScale != 0  ? ((int) mc.displayWidth / mc.gameSettings.guiScale) : 0);
                if (mc.gameSettings.guiScale == 3) scale = mc.displayWidth / 2;
                mc.getTextureManager().bindTexture(res);
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.48 : (mc.displayWidth * 0.48 + scale)/2), (!isFullScreen() ? y1 : y1/2), -90).tex(0, 1).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.49 : (mc.displayWidth * 0.49 + scale)/2), (!isFullScreen() ? y1 : y1/2), -90).tex(1, 1).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.49 : (mc.displayWidth * 0.49 + scale)/2), (!isFullScreen() ? y0 : y0/2), -90).tex(1, 0).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.48 : (mc.displayWidth * 0.48 + scale)/2), (!isFullScreen() ? y0 : y0/2), -90).tex(0, 0).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }
        }
        if (pocketWatches != null && ItemPocketWatch.doesActiveItemModuleExist(pocketWatches, ModItems.moduleDeathWalk)) {
            ItemStack deathWalk = ItemPocketWatch.getItemModuleFromMultiple(pocketWatches, ModItems.moduleDeathWalk);
            int deathModulePower = (int) NBTHelper.getInt(deathWalk, "module_power");
            for (int i=0;i< deathModulePower/50;i++) {
                GlStateManager.pushMatrix();

                res = new ResourceLocation(SpaceTime.MODID, "textures/gui/death.png");
                double y1 = mc.displayHeight * 0.02 + i * mc.displayHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? mc.displayHeight * 0.047 : 0);
                double y0 = mc.displayHeight * 0.001 + i * mc.displayHeight * 0.01 + (!player.getActivePotionMap().isEmpty() ? mc.displayHeight * 0.047 : 0);

                int scale = (mc.gameSettings.guiScale != 0  ? ((int) mc.displayWidth / mc.gameSettings.guiScale) : 0);
                if (mc.gameSettings.guiScale == 3) scale = mc.displayWidth / 2;
                mc.getTextureManager().bindTexture(res);
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.49 : (mc.displayWidth * 0.49 + scale)/2), (!isFullScreen() ? y1 : y1/2), -90).tex(0, 1).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.5 : (mc.displayWidth * 0.5 + scale)/2), (!isFullScreen() ? y1 : y1/2), -90).tex(1, 1).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.5 : (mc.displayWidth * 0.5 + scale)/2), (!isFullScreen() ? y0 : y0/2), -90).tex(1, 0).endVertex();
                buffer.pos((!isFullScreen() ? mc.displayWidth * 0.49 : (mc.displayWidth * 0.49 + scale)/2), (!isFullScreen() ? y0 : y0/2), -90).tex(0, 0).endVertex();
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

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<AbstractClientPlayer> evt) {
        if (evt.getEntity() instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer)evt.getEntity();
            if (player.isHandActive() && SpaceTime.getCanStackBlock(player.getActiveItemStack())) {
                ModelPlayer model = (ModelPlayer)evt.getRenderer().getMainModel();
                boolean left2 = player.getActiveHand() == EnumHand.OFF_HAND && player.getPrimaryHand() == EnumHandSide.RIGHT;
                boolean left1 = player.getActiveHand() == EnumHand.MAIN_HAND && player.getPrimaryHand() == EnumHandSide.LEFT;
                if (!left1 && !left2) {
                    if (model.rightArmPose == ModelBiped.ArmPose.ITEM) {
                        model.rightArmPose = ModelBiped.ArmPose.BLOCK;
                    }
                } else if (model.leftArmPose == ModelBiped.ArmPose.ITEM) {
                    model.leftArmPose = ModelBiped.ArmPose.BLOCK;
                }
            }
        }

    }



    private boolean isFullScreen(){
        boolean full0 = Display.isFullscreen();
        boolean full1 = Minecraft.getMinecraft().isFullScreen();
        boolean full2 = Minecraft.getMinecraft().gameSettings.fullScreen;
        return  full0 && full1 && full2;
    }
}
