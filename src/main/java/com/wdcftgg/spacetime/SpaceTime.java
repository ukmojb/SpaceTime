package com.wdcftgg.spacetime;


import com.wdcftgg.spacetime.blocks.tileEntity.*;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.dimension.BlackHoleWorldProvider;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.gui.GuiElementLoader;
import com.wdcftgg.spacetime.init.RegistryHandler;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.recipe.CraftingLoader;
import com.wdcftgg.spacetime.world.structures.challengefield;
import com.wdcftgg.spacetime.world.structures.pillar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.Set;

@Mod(modid = SpaceTime.MODID, name = SpaceTime.NAME, version = SpaceTime.VERSION, dependencies="required-after:clockworkphase;required-after:geckolib3")
public class SpaceTime {
    public static final String MODID = "spacetime";
    public static final String NAME = "SpaceTime";
    public static final String VERSION = "1.0.0";
    public static Logger logger;
    public static final boolean SHOW_WARN = true;

    public static DimensionType SpaceDim;
    public static DimensionType BlackHoleDim;

    public static final String CLIENT_PROXY_CLASS = "com.wdcftgg.spacetime.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.spacetime.proxy.CommonProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static ServerProxy serverProxy = new ServerProxy();

    @Mod.Instance
    public static SpaceTime instance;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);

        Config.init(event.getSuggestedConfigurationFile());

        SpaceDim = DimensionType.register("space_dimension", "_spacedim", Config.SPACEDDIM, SpaceWorldProvider.class, false);
        DimensionManager.registerDimension(Config.SPACEDDIM, SpaceDim);
        BlackHoleDim = DimensionType.register("blackhole_dimension", "_blackholedim", Config.BLACKHOLEDIM, BlackHoleWorldProvider.class, false);
        DimensionManager.registerDimension(Config.BLACKHOLEDIM, BlackHoleDim);

        proxy.onPreInit();
        serverProxy.onPreInit();

    }


    @EventHandler
    public void Init(FMLInitializationEvent event) {

        GeckoLib.initialize();
        RegisterTileEntity();
        RegisterWorldGen();
        CraftingLoader.init();
        new GuiElementLoader();

        proxy.onInit();
        serverProxy.onInit();

        PacketHandler.init();


	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Moved Spawning registry to last since forge doesn't auto-generate sub
        // "M' biomes until late
        RegistryHandler.postInitReg();
        proxy.onPostInit();
        serverProxy.onPostInit();
    }


    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries(event);
    }


    private static void RegisterTileEntity() {
//        GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(MODID, "deboom_orb_basic"));

        GameRegistry.registerTileEntity(TimeCompressorEntity.class, new ResourceLocation(MODID, "CompressorEntity"));
        GameRegistry.registerTileEntity(SpaceTimeTurbulenceEntity.class, new ResourceLocation(MODID, "TimeTurbulenceEntity"));
        GameRegistry.registerTileEntity(ReverseBeaconEntity.class, new ResourceLocation(MODID, "ReverseBeaconEntity"));

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

    private static void RegisterWorldGen() {
//        GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(MODID, "deboom_orb_basic"));

        GameRegistry.registerWorldGenerator(new pillar(), 666);
        GameRegistry.registerWorldGenerator(new challengefield(), 666);
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