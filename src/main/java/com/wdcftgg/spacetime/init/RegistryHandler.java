package com.wdcftgg.spacetime.init;

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.blocks.slab.BlockSlabBase;
import com.wdcftgg.spacetime.blocks.slab.BlockSpaceSlab;
import com.wdcftgg.spacetime.blocks.slab.BlockTimeSlab;
import com.wdcftgg.spacetime.blocks.stairs.BlockBaseStairs;
import com.wdcftgg.spacetime.blocks.stairs.BlockSpaceStairs;
import com.wdcftgg.spacetime.blocks.stairs.BlockTimeStairs;
import com.wdcftgg.spacetime.blocks.tileEntity.*;
import com.wdcftgg.spacetime.blocks.tileEntity.HourGlass.*;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.dimension.BiomeNull;
import com.wdcftgg.spacetime.entity.ModEntityInit;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.wdcftgg.spacetime.SpaceTime.MODID;

@EventBusSubscriber
public class RegistryHandler {

	public static final Biome NULL = new BiomeNull().setRegistryName(MODID + ":null");

	public static BlockSlabBase SPACE_SLAB = new BlockSpaceSlab(Material.WOOD);
	public static BlockSlabBase TIME_SLAB = new BlockTimeSlab(Material.WOOD);
	public static BlockBaseStairs SPACE_STAIRS = new BlockSpaceStairs();
	public static BlockBaseStairs TIME_STAIRS = new BlockTimeStairs();

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(NULL);
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		STItems.ITEMS.add(newItemBlock(SPACE_SLAB));
		STItems.ITEMS.add(newItemBlock(TIME_SLAB));
		STItems.ITEMS.add(newItemBlock(SPACE_STAIRS));
		STItems.ITEMS.add(newItemBlock(TIME_STAIRS));

		event.getRegistry().registerAll(STItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		STBlocks.BLOCKS.add(SPACE_SLAB);
		STBlocks.BLOCKS.add(TIME_SLAB);
		STBlocks.BLOCKS.add(TIME_STAIRS);
		STBlocks.BLOCKS.add(SPACE_STAIRS);

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
		ResourceLocation location = new ResourceLocation(MODID, "swordcore_1");
		ResourceLocation location1 = new ResourceLocation(MODID, "swordcore_2");
		ResourceLocation location2 = new ResourceLocation(MODID, "swordblocking");
		ResourceLocation location3 = new ResourceLocation(MODID, "fallsword");

		ModSounds.SWORDCORE_1 = new SoundEvent(location).setRegistryName(location);
		ModSounds.SWORDCORE_2 = new SoundEvent(location1).setRegistryName(location1);
		ModSounds.SWORDBLOCKING = new SoundEvent(location2).setRegistryName(location2);
		ModSounds.FALLSWORD = new SoundEvent(location3).setRegistryName(location3);

		event.getRegistry().register(ModSounds.SWORDCORE_1);
		event.getRegistry().register(ModSounds.SWORDCORE_2);
		event.getRegistry().register(ModSounds.SWORDBLOCKING);
		event.getRegistry().register(ModSounds.FALLSWORD);
	}

	private static Item newItemBlock(Block block){
		return new ItemBlock(block).setRegistryName(block.getRegistryName()).setTranslationKey(block.getTranslationKey());
	}

	public static void RegisterTileEntity() {

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
		GameRegistry.registerTileEntity(TimePillarEntity.class, new ResourceLocation(MODID, "timepillar"));
		GameRegistry.registerTileEntity(SpaceRewordEntity.class, new ResourceLocation(MODID, "space_reword"));
	}

}
