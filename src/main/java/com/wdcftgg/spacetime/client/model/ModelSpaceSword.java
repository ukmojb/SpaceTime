package com.wdcftgg.spacetime.client.model;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/9 14:23
 */
public class ModelSpaceSword extends AnimatedGeoModel
{
    @Override
    public ResourceLocation getAnimationFileLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "animations/spacesword.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "geo/spacesword.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity)
    {
        return new ResourceLocation(SpaceTime.MODID, "textures/entity/spacesword.png");
    }
}
