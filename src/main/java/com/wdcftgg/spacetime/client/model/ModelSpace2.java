package com.wdcftgg.spacetime.client.model;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSpace2 extends AnimatedGeoModel
{
    @Override
    public ResourceLocation getAnimationFileLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "animations/space2.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "geo/space2.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "textures/entity/space2.png");
    }
}
