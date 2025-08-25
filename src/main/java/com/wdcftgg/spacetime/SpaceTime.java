package com.wdcftgg.spacetime;


import com.wdcftgg.spacetime.blocks.tileEntity.*;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.command.CommandSpaceTime;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.dimension.BlackHoleWorldProvider;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.gui.GuiElementLoader;
import com.wdcftgg.spacetime.gui.book.BookRegistry;
import com.wdcftgg.spacetime.gui.book.json.JsonFileLoader;
import com.wdcftgg.spacetime.init.RegistryHandler;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.recipe.CraftingLoader;
import com.wdcftgg.spacetime.world.structures.challengefield;
import com.wdcftgg.spacetime.world.structures.pillar;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.spacetime.proxy.ServerProxy";

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
        RegistryHandler.RegisterTileEntity();
        RegisterWorldGen();
        CraftingLoader.init();
        new GuiElementLoader();

        proxy.onInit();
        serverProxy.onInit();

        PacketHandler.init();

        BookRegistry.initBook();

//        JsonFileLoader.loadAllJsons("books");
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
        event.registerServerCommand(new CommandSpaceTime());
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