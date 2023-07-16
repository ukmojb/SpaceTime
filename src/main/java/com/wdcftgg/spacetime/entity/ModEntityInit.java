package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.render.RenderUnstableTimePolymer;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import com.wdcftgg.spacetime.item.ModItems;

public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {

        registerEntity("UnstableTimePolymer", EntityUnstableTimePolymer.class);


        registerEntityAndEgg("TimeCrack", EntityTimeCrack.class);
        registerEntityAndEgg("Time", EntityTime.class,0x0009c8, 0x660000);


    }

    private  static  void registerEntityAndEgg(String name, Class<? extends Entity> entity)
    {
        registerEntityAndEgg(name, entity, ENTITY_NEXT_ID, 50, 0x3786e7, 0x660000);
    }

    private  static  void registerEntityAndEgg(String name, Class<? extends Entity> entity, int color1, int color2)
    {
        registerEntityAndEgg(name, entity, ENTITY_NEXT_ID, 50, color1, color2);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity)
    {
        registerEntityNoEgg(name, entity, ENTITY_NEXT_ID, 50);
    }

    private  static  void registerEntityAndEgg(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                SpaceTime.instance,
                range,
                1,
                true,
                color1, color2
                );
        ENTITY_NEXT_ID++;
    }

    private  static  void registerEntityNoEgg(String name, Class<? extends Entity> entity, int id, int range){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                SpaceTime.instance,
                range,
                1,
                true
        );
        ENTITY_NEXT_ID++;
    }






}
