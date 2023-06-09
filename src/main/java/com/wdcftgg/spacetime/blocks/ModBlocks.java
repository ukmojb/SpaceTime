package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.blocks.HourGlass.*;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block TIMEPILLAR = new BlockTimePillar();
	public static final Block SPACETIMETURBULENCE = new BlockSpaceTimeTurbulence();
	public static final Block TIMECOMPRESSOR = new BlockTimeCompressor();
	public static final Block CONCRETIZATIONHOURGLASS = new BlockConcretizationHourGlass();
	public static final Block TIMEALTARCORE = new BlockTimeAltarCore();

	public static final Block HOURGLASS = new BlockHourGlass();
	public static final Block AIRHOURGLASS = new BlockAirHourGlass();
	public static final Block DEATHHOURGLASS = new BlockDeathHourGlass();
	public static final Block EARTHHOURGLASS = new BlockEarthHourGlass();
	public static final Block FIREHOURGLASS = new BlockFireHourGlass();
	public static final Block LIFEHOURGLASS = new BlockLifeHourGlass();
	public static final Block LIGHTHOURGLASS = new BlockLightHourGlass();
	public static final Block MOONHOURGLASS = new BlockMoonHourGlass();
	public static final Block WATERHOURGLASS = new BlockWaterHourGlass();
}
