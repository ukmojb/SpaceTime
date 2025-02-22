package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {

        registerEntity("UnstableTimePolymer", EntityUnstableTimePolymer.class);
        registerEntity("Sword", EntitySword.class);
        registerEntity("TimePhantom", EntityTimePhantom.class);
        registerEntity("BlackHole", EntityBlackHole.class);
        registerEntity("Rubble", EntityRubble.class);
        registerEntity("Spearsubspace", EntitySpearsubspace.class);
        registerEntity("Spearsubspace1", EntitySpearsubspace1.class);


        registerEntityAndEgg("TimeCrack", EntityTimeCrack.class);
        registerEntityAndEgg("Portal", EntityPortal.class);
        registerEntityAndEgg("Time", EntityTime.class,0x0009c8, 0x660000);
        registerEntityAndEgg("Space", EntitySpace.class,0x0009c8, 0x660000);
        registerEntityAndEgg("Space2", EntitySpace2.class,0x0009c8, 0x660000);
        registerEntityAndEgg("SpaceSword", EntitySpaceSword.class,0x0009c8, 0x660000);

//        this.dataManager.set(RABBIT_TYPE, Integer.valueOf(rabbitTypeId));
    }

    private  static  void registerEntityAndEgg(String name, Class<? extends Entity> entity)
    {
        registerEntityAndEgg(name, entity, ENTITY_NEXT_ID, 200, 0x3786e7, 0x660000);
    }

    private  static  void registerEntityAndEgg(String name, Class<? extends Entity> entity, int color1, int color2)
    {
        registerEntityAndEgg(name, entity, ENTITY_NEXT_ID, 200, color1, color2);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity)
    {
        registerEntityNoEgg(name, entity, ENTITY_NEXT_ID, 200);
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
