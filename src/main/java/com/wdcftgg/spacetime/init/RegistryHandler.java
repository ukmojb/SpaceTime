package com.wdcftgg.spacetime.init;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.blocks.tileEntity.ConcretizationHourGlassEntity;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import com.wdcftgg.spacetime.client.render.*;
import com.wdcftgg.spacetime.client.render.HourGlass.*;
import com.wdcftgg.spacetime.entity.*;
import com.wdcftgg.spacetime.item.ItemModuleXP;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
//		STItems.ITEMS.add(STItems.MODULEXP);
		event.getRegistry().registerAll(STItems.ITEMS.toArray(new Item[0]));

	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(STBlocks.BLOCKS.toArray(new Block[0]));
	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : STItems.ITEMS)
		{
			if (item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : STBlocks.BLOCKS)
		{
			if (block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}


	}

	public static void preInitRegistries(FMLPreInitializationEvent event)
	{



		ModEntityInit.registerEntities();


		RenderingRegistry.registerEntityRenderingHandler(EntityUnstableTimePolymer.class, RenderUnstableTimePolymer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCrack.class, RenderTimeCrack::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTime.class, RenderTime::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySword.class, RenderSword::new);

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



	}

	public static void postInitReg()
	{
		//WorldType TYPE_ONE = new WorldTypeOne();
	}



	public static void serverRegistries(FMLServerStartingEvent event)
    {
        //event.registerServerCommand(new CommandDimTeleport());
    }

	@SubscribeEvent
	public static void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event)
	{
		ResourceLocation location = new ResourceLocation(SpaceTime.MODID, "swordcore_1");
		ResourceLocation location1 = new ResourceLocation(SpaceTime.MODID, "swordcore_2");
		ResourceLocation location2 = new ResourceLocation(SpaceTime.MODID, "swordblocking");
		ResourceLocation location3 = new ResourceLocation(SpaceTime.MODID, "fallsword");

		ModSounds.SWORDCORE_1 = new SoundEvent(location).setRegistryName(location);
		ModSounds.SWORDCORE_2 = new SoundEvent(location1).setRegistryName(location1);
		ModSounds.SWORDBLOCKING = new SoundEvent(location2).setRegistryName(location2);
		ModSounds.FALLSWORD = new SoundEvent(location3).setRegistryName(location3);

		event.getRegistry().register(ModSounds.SWORDCORE_1);
		event.getRegistry().register(ModSounds.SWORDCORE_2);
		event.getRegistry().register(ModSounds.SWORDBLOCKING);
		event.getRegistry().register(ModSounds.FALLSWORD);
	}
}
