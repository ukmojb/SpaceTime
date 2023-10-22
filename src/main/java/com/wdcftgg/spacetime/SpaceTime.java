package com.wdcftgg.spacetime;


import com.wdcftgg.spacetime.blocks.tileEntity.*;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.client.handler.HeldItemHandler;
import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.event.*;
import com.wdcftgg.spacetime.gui.GuiElementLoader;
import com.wdcftgg.spacetime.init.RegistryHandler;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.recipe.CraftingLoader;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.List;
import java.util.Set;

@Mod(modid = SpaceTime.MODID, name = SpaceTime.NAME, version = SpaceTime.VERSION, dependencies="required-after:clockworkphase;required-after:geckolib3")
public class SpaceTime {
    public static final String MODID = "spacetime";
    public static final String NAME = "SpaceTime";
    public static final String VERSION = "1.0.0";
    public static Logger logger;
    public static final boolean SHOW_WARN = true;
    public static int spacedimID = 253;
    public static DimensionType myDim;

    public static final String CLIENT_PROXY_CLASS = "com.wdcftgg.spacetime.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.spacetime.proxy.ServerProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    @Mod.Instance
    public static SpaceTime instance;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);

        config.init(event.getSuggestedConfigurationFile());

        myDim = DimensionType.register("space_dimension", "_spacedim", spacedimID, SpaceWorldProvider.class, false);
        DimensionManager.registerDimension(spacedimID, myDim);

        proxy.onPreInit();

    }


    @EventHandler
    public static void Init(FMLInitializationEvent event) {

        GeckoLib.initialize();
        RegisterTileEntity();
        CraftingLoader.init();
        new GuiElementLoader();

        proxy.onInit();

        PacketHandler.init();


	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Moved Spawning registry to last since forge doesn't auto-generate sub
        // "M' biomes until late
        RegistryHandler.postInitReg();
        proxy.onPostInit();
    }


    @EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries(event);
    }


    private static void RegisterTileEntity() {
//        GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(MODID, "deboom_orb_basic"));

        GameRegistry.registerTileEntity(TimeCompressorEntity.class, new ResourceLocation(MODID, "CompressorEntity"));
        GameRegistry.registerTileEntity(SpaceTimeTurbulenceEntity.class, new ResourceLocation(MODID, "TimeTurbulenceEntity"));

        GameRegistry.registerTileEntity(HourGlassEntity.class, new ResourceLocation(MODID, "hourglass"));
        GameRegistry.registerTileEntity(AirHourGlassEntity.class, new ResourceLocation(MODID, "airhourglass"));
        GameRegistry.registerTileEntity(DeathHourGlassEntity.class, new ResourceLocation(MODID, "deathhourglass"));
        GameRegistry.registerTileEntity(EarthHourGlassEntity.class, new ResourceLocation(MODID, "earthhourglass"));
        GameRegistry.registerTileEntity(FireHourGlassEntity.class, new ResourceLocation(MODID, "firehourglass"));
        GameRegistry.registerTileEntity(LifeHourGlassEntity.class, new ResourceLocation(MODID, "lifehourglass"));
        GameRegistry.registerTileEntity(LightHourGlassEntity.class, new ResourceLocation(MODID, "lighthourglass"));
        GameRegistry.registerTileEntity(MoonHourGlassEntity.class, new ResourceLocation(MODID, "moonhourglass"));
        GameRegistry.registerTileEntity(WaterHourGlassEntity.class, new ResourceLocation(MODID, "waterhourglass"));

        GameRegistry.registerTileEntity(ConcretizationHourGlassEntity.class, new ResourceLocation(MODID, "concretizationhourglass"));
        GameRegistry.registerTileEntity(TimeAltarCoreEntity.class, new ResourceLocation(MODID, "timealtarcore"));
        GameRegistry.registerTileEntity(SpaceTimeAirEntity.class, new ResourceLocation(MODID, "spacetimeair"));
        GameRegistry.registerTileEntity(STExtractorEntity.class, new ResourceLocation(MODID, "spacetime_extractor"));
        GameRegistry.registerTileEntity(EndGatewayImitateEntity.class, new ResourceLocation(MODID, "endgatewayimitate"));
    }

    public static void LogWarning(String str, Object... args) {
        if (SHOW_WARN) {
            logger.warn(String.format(str, args));
        }
    }

    public static void LogWarning(String str) {
        if (SHOW_WARN) {
            logger.warn(str);
        }
    }

    public static void Log(String str) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(str);
//        }
    }

    public static void Log(String str, Object... args) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(String.format(str, args));
//        }
    }



    private static Set<Item> exclude;
    private static Set<Item> include;

    public static boolean getCanStackBlock(ItemStack stack) {
        Item item = stack.getItem();
        if (exclude != null && exclude.contains(item)) {
            return false;
        } else if (item instanceof ItemSword) {
            return true;
        } else {
            return include != null && include.contains(item);
        }
    }


}