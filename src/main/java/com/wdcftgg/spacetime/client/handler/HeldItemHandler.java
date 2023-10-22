package com.wdcftgg.spacetime.client.handler;


import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Quaternion;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/19 22:36
 */
@SideOnly(Side.CLIENT)
public class HeldItemHandler {
    public HeldItemHandler() {
    }

    public static void replaceHeldItemLayer() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        Iterator var1 = skinMap.values().iterator();

        while(var1.hasNext()) {
            RenderPlayer renderPlayer = (RenderPlayer)var1.next();
            List<LayerRenderer<EntityLivingBase>> layers = ClientProxy.getLayerRenderers(renderPlayer);
            if (layers != null) {
                layers.removeIf((it) -> it instanceof LayerHeldItem);
                renderPlayer.addLayer(new SwordBlockingLayer(renderPlayer));
            }
        }

    }

    public static void applyTransformReverse(ItemTransformVec3f vec, boolean leftHand) {
        if (vec != ItemTransformVec3f.DEFAULT) {
            int i = leftHand ? -1 : 1;
            GlStateManager.scale(1.0F / vec.scale.x, 1.0F / vec.scale.y, 1.0F / vec.scale.z);
            float x = vec.rotation.x;
            float y = vec.rotation.y;
            float z = vec.rotation.z;
            if (leftHand) {
                y = -y;
                z = -z;
            }

            Quaternion quat = makeQuaternion(x, y, z);
            GlStateManager.rotate(quat.negate(quat));
            GlStateManager.translate((float)i * -vec.translation.x, -vec.translation.y, -vec.translation.z);
        }

    }

    private static Quaternion makeQuaternion(float p_188035_0_, float p_188035_1_, float p_188035_2_) {
        float f = p_188035_0_ * 0.017453292F;
        float f1 = p_188035_1_ * 0.017453292F;
        float f2 = p_188035_2_ * 0.017453292F;
        float f3 = MathHelper.sin(0.5F * f);
        float f4 = MathHelper.cos(0.5F * f);
        float f5 = MathHelper.sin(0.5F * f1);
        float f6 = MathHelper.cos(0.5F * f1);
        float f7 = MathHelper.sin(0.5F * f2);
        float f8 = MathHelper.cos(0.5F * f2);
        return new Quaternion(f3 * f6 * f8 + f4 * f5 * f7, f4 * f5 * f8 - f3 * f6 * f7, f3 * f5 * f8 + f4 * f6 * f7, f4 * f6 * f8 - f3 * f5 * f7);
    }
}
