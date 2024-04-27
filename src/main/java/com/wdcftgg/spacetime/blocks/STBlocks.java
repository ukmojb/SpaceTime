package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.blocks.HourGlass.*;
import com.wdcftgg.spacetime.blocks.fence.BlockSpaceFence;
import com.wdcftgg.spacetime.blocks.fence.BlockTimeFence;
import com.wdcftgg.spacetime.blocks.stextractor.*;
import com.wdcftgg.spacetime.init.RegistryHandler;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class STBlocks {
	public static List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block TIMEPILLAR = new BlockTimePillar();
	public static final Block SPACETIMETURBULENCE = new BlockSpaceTimeTurbulence();
	public static final Block TIMECOMPRESSOR = new BlockTimeCompressor();
	public static final Block CONCRETIZATIONHOURGLASS = new BlockConcretizationHourGlass();
	public static final Block TIMEALTARCORE = new BlockTimeAltarCore();
	public static final Block AIR = new BlockAir();

	public static final Block HOURGLASS = new BlockHourGlass();
	public static final Block AIRHOURGLASS = new BlockAirHourGlass();
	public static final Block DEATHHOURGLASS = new BlockDeathHourGlass();
	public static final Block EARTHHOURGLASS = new BlockEarthHourGlass();
	public static final Block FIREHOURGLASS = new BlockFireHourGlass();
	public static final Block LIFEHOURGLASS = new BlockLifeHourGlass();
	public static final Block LIGHTHOURGLASS = new BlockLightHourGlass();
	public static final Block MOONHOURGLASS = new BlockMoonHourGlass();
	public static final Block WATERHOURGLASS = new BlockWaterHourGlass();

	public static final Block AirSTExtractor = new AirSTExtractor();
	public static final Block DeathSTExtractor = new DeathSTExtractor();
	public static final Block EarthSTExtractor = new EarthSTExtractor();
	public static final Block FireSTExtractor = new FireSTExtractor();
	public static final Block LifeSTExtractor = new LifeSTExtractor();
	public static final Block LightSTExtractor = new LightSTExtractor();
	public static final Block MoonSTExtractor = new MoonSTExtractor();
	public static final Block WaterSTExtractor = new WaterSTExtractor();
	public static final Block SpaceBrick = new BlockSpaceBrick();
	public static final Block TimeBrick = new BlockTimeBrick();
	public static final Block SpaceFence = new BlockSpaceFence();
	public static final Block TimeFence = new BlockTimeFence();
	public static final Block EndGatewayImitate = new BlockEndGatewayImitate();
	public static final Block SpaceStairs = RegistryHandler.SPACE_STAIRS;
	public static final Block TimeStairs = RegistryHandler.TIME_STAIRS;
	public static final Block SpaceSlab = RegistryHandler.SPACE_SLAB;
	public static final Block TimeSlab = RegistryHandler.TIME_SLAB;
	public static final Block ReverseBeacon = new BlockReverseBeacon();

}
