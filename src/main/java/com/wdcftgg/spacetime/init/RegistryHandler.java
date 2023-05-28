package com.wdcftgg.spacetime.init;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.ModBlocks;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.HourGlassEntity;
import com.wdcftgg.spacetime.client.render.RenderTimeCrack;
import com.wdcftgg.spacetime.client.render.RenderUnstableTimePolymer;
import com.wdcftgg.spacetime.client.renderer.HourGlass.HourGrassRenderer;
import com.wdcftgg.spacetime.entity.EntityTimeCrack;
import com.wdcftgg.spacetime.entity.EntityUnstableTimePolymer;
import com.wdcftgg.spacetime.entity.ModEntityInit;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
import software.bernie.example.registry.SoundRegistry;
import software.bernie.geckolib3.GeckoLib;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if (item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
				SpaceTime.Log(item.toString());
			}
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			if (block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}

	}

	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		//GameRegistry.registerWorldGenerator(new ModWorldGenOld(), 100);
		//GameRegistry.registerWorldGenerator(new ModWorldGenNew(), 120);

		InitBiome.registerBiomes();
		InitDimension.registerDimensions();

		ModEntityInit.registerEntities();


		RenderingRegistry.registerEntityRenderingHandler(EntityUnstableTimePolymer.class, RenderUnstableTimePolymer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCrack.class, RenderTimeCrack::new);

		ClientRegistry.bindTileEntitySpecialRenderer(HourGlassEntity.class, new HourGrassRenderer());

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

		ModSounds.SWORDCORE_1 = new SoundEvent(location).setRegistryName(location);
		ModSounds.SWORDCORE_2 = new SoundEvent(location1).setRegistryName(location1);
		ModSounds.SWORDBLOCKING = new SoundEvent(location2).setRegistryName(location2);

		event.getRegistry().register(ModSounds.SWORDCORE_1);
		event.getRegistry().register(ModSounds.SWORDCORE_2);
		event.getRegistry().register(ModSounds.SWORDBLOCKING);
	}
}
