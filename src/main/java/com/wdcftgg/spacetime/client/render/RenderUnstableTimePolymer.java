package com.wdcftgg.spacetime.client.render;

import com.wdcftgg.spacetime.entity.EntityUnstableTimePolymer;
import com.wdcftgg.spacetime.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 8:41
 */
public class RenderUnstableTimePolymer extends RenderSnowball<EntityUnstableTimePolymer> {
    public RenderUnstableTimePolymer(RenderManager renderManagerIn)
    {
        super(renderManagerIn, ModItems.UNSTABLETIMEPOLYMER, Minecraft.getMinecraft().getRenderItem());
    }
}
