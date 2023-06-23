package com.wdcftgg.spacetime.client.handler;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/20 3:30
 */
@SideOnly(Side.CLIENT)
public class RenderBlockingHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    public RenderBlockingHandler() {
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
