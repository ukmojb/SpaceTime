package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.ConcretizationHourGlassEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.EndGatewayImitateEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.ReverseBeaconEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.client.handler.HeldItemHandler;
import com.wdcftgg.spacetime.client.render.HourGlass.*;
import com.wdcftgg.spacetime.client.render.*;
import com.wdcftgg.spacetime.entity.*;
import com.wdcftgg.spacetime.event.EventLossSpatialSense;
import com.wdcftgg.spacetime.event.EventRender;
import com.wdcftgg.spacetime.event.EventTimeBack;
import com.wdcftgg.spacetime.event.EventToolTip;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
	}


	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	public void onInit(){
		super.onInit();
		MinecraftForge.EVENT_BUS.register(new EventLossSpatialSense());
		MinecraftForge.EVENT_BUS.register(new EventRender());
		MinecraftForge.EVENT_BUS.register(new EventToolTip());
		MinecraftForge.EVENT_BUS.register(new EventTimeBack());
	}



	public void onPreInit() {
		super.onPreInit();


		RenderingRegistry.registerEntityRenderingHandler(EntityUnstableTimePolymer.class, RenderUnstableTimePolymer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCrack.class, RenderTimeCrack::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTime.class, RenderTime::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTimePhantom.class, RenderTimePhantom::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySword.class, RenderSword::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, RenderBlackHole::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRubble.class, RenderRubble::new);


		RenderingRegistry.registerEntityRenderingHandler(EntitySpace.class, RenderSpace::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpaceSword.class, RenderSpaceSword::new);

		ClientRegistry.bindTileEntitySpecialRenderer(HourGlassEntity.class, new HourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(AirHourGlassEntity.class, new AirHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(DeathHourGlassEntity.class, new DeathHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(EarthHourGlassEntity.class, new EarthHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(FireHourGlassEntity.class, new FireHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(LifeHourGlassEntity.class, new LifeHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(LightHourGlassEntity.class, new LightHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(MoonHourGlassEntity.class, new MoonHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(WaterHourGlassEntity.class, new WaterHourGrassRender());
		ClientRegistry.bindTileEntitySpecialRenderer(ConcretizationHourGlassEntity.class, new RenderConcretizationHourGlass());
		ClientRegistry.bindTileEntitySpecialRenderer(TimeAltarCoreEntity.class, new RenderTimeAltarCore());
		ClientRegistry.bindTileEntitySpecialRenderer(EndGatewayImitateEntity.class, new RenderEndGatewayImitate());
		ClientRegistry.bindTileEntitySpecialRenderer(ReverseBeaconEntity.class, new RenderReverseBeacon());
	}

	public void onPostInit() {
		super.onPostInit();
		HeldItemHandler.replaceHeldItemLayer();
	}

	public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
		return (List)getPrivateValue(RenderLivingBase.class, instance, "layerRenderers");
	}

	private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
		} catch (Exception var4) {
			return null;
		}
	}

}
