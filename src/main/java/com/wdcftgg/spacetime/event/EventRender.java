package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/23 20:33
 */


@Mod.EventBusSubscriber
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
    }

    @SubscribeEvent
    public void onPreRenderLiving(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            if (entity.getActivePotionEffect(ModPotions.heterospace) != null) {
                GlStateManager.color(148/255f, 22/255f, 232/255f, 0.5f); // 设置透明度
                GlStateManager.enableBlend(); // 启用混合模式
                GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA); // 设置混合模式为半透明
            }
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

    @SubscribeEvent
    public void onRenderHand(RenderSpecificHandEvent evt) {
        EntityPlayerSP player = this.mc.player;
        if (player != null && player.isHandActive() && player.getActiveHand() == evt.getHand()) {
            ItemStack stack = evt.getItemStack();
            if (SpaceTime.getCanStackBlock(stack) && player.getActivePotionEffect(ModPotions.swordcore) != null) {
                GlStateManager.pushMatrix();
                boolean rightHanded = (evt.getHand() == EnumHand.MAIN_HAND ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT;
                this.transformSideFirstPerson(rightHanded ? 1.0F : -1.0F, evt.getEquipProgress(), stack);
                this.mc.getItemRenderer().renderItemSide(player, stack, rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
                GlStateManager.popMatrix();
                evt.setCanceled(true);
            }
        }

    }

    private void transformSideFirstPerson(float side, float equippedProg, ItemStack stack) {
        GlStateManager.translate(side * 0.56F, -0.52F + equippedProg * -0.6F, -0.72F);
        GlStateManager.translate(side * -0.14142136F, 0.08F, 0.14142136F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(side * 78.05F, 0.0F, 0.0F, 1.0F);
    }
}
