package com.wdcftgg.spacetime.client.model;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/22 21:11
 */
public class ModelConcretizationHourGlass extends AnimatedGeoModel {

    @Override
    public ResourceLocation getAnimationFileLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "animations/concretizationhourglass.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(Object animatable)
    {
        return new ResourceLocation(SpaceTime.MODID, "geo/concretization_hourglass.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "textures/block/concretization_hourglass.png");
    }
}
