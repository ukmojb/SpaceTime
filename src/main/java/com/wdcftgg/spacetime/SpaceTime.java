package com.wdcftgg.spacetime;



import com.wdcftgg.spacetime.blocks.tileEntity.*;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.client.handler.HeldItemHandler;
import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.event.EventRender;
import com.wdcftgg.spacetime.event.EventSword;
import com.wdcftgg.spacetime.event.EventToolTip;
import com.wdcftgg.spacetime.gui.GuiElementLoader;
import com.wdcftgg.spacetime.init.RegistryHandler;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.ProxyBase;
import com.wdcftgg.spacetime.recipe.CraftingLoader;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.File;
import java.util.List;
import java.util.Set;

@Mod(modid = SpaceTime.MODID, name = SpaceTime.NAME, version = SpaceTime.VERSION, dependencies="required-after:clockworkphase;required-after:geckolib3")
public class SpaceTime {
    public static final String MODID = "spacetime";
    public static final String NAME = "SpaceTime";
    public static final String VERSION = "1.0.0";
    public static File mp4 = new File("spacetime", "textures\\v\\aaa.mp4");

    public static Logger logger;

    public static final boolean SHOW_WARN = true;



    @Mod.Instance
    public static SpaceTime instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);

        config.init(event.getSuggestedConfigurationFile());

    }


    @EventHandler
    public static void Init(FMLInitializationEvent event) {

        GeckoLib.initialize();
        RegisterTileEntity();
        CraftingLoader.init();
        new GuiElementLoader();

        MinecraftForge.EVENT_BUS.register(new EventSword());
        MinecraftForge.EVENT_BUS.register(new EventRender());
        MinecraftForge.EVENT_BUS.register(new EventToolTip());

        PacketHandler.init();
//        ModAdvancements.init();

	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Moved Spawning registry to last since forge doesn't auto-generate sub
        // "M' biomes until late
        RegistryHandler.postInitReg();
        HeldItemHandler.replaceHeldItemLayer();
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

    public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
        return (List)getPrivateValue(RenderLivingBase.class, instance, "layerRenderers");
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

    public void damageSword(EntityPlayer player, float damage) {
        if (damage >= 3.0F) {
            ItemStack stack = player.getActiveItemStack();
            ItemStack copy = stack.copy();
            int i = 1 + MathHelper.floor(damage);
            stack.damageItem(i, player);
            if (stack.isEmpty()) {
                EnumHand enumhand = player.getActiveHand();
                ForgeEventFactory.onPlayerDestroyItem(player, copy, enumhand);
                if (enumhand == EnumHand.MAIN_HAND) {
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }

                player.resetActiveHand();
                player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
            }
        }
    }

    public boolean getIsBlocking(EntityPlayer player) {
        this.getClass();
        boolean ready = 72000 - player.getItemInUseCount() >= 0;
        return ready && getCanStackBlock(player.getActiveItemStack());
    }

    private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {
        try {
            return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
        } catch (Exception var4) {
            return null;
        }
    }
}