package com.wdcftgg.spacetime.client.model.HourGlass;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 10:41
 */
public class ModelLifeHourGlass extends AnimatedGeoModel {

    @Override
    public ResourceLocation getAnimationFileLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "animations/modelhourglass.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(Object animatable)
    {
        return new ResourceLocation(SpaceTime.MODID, "geo/modelhourglass.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "textures/block/blocklifehourglass.png");
    }
}