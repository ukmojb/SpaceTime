package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.creatures.moroon.EntityMoroonUnitBase;
import com.wdcftgg.spacetime.entity.creatures.render.RenderBullet;
import com.wdcftgg.spacetime.entity.creatures.render.RenderMoroonHumanoid;
import com.wdcftgg.spacetime.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(SpaceTime.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
