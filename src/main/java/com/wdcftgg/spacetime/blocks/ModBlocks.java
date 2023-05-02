package com.wdcftgg.spacetime.blocks;

import com.wdcftgg.spacetime.blocks.HourGlass.BlockHourGlass;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block TIMEPILLAR = new BlockTimePillar();
	public static final Block SPACETIMETURBULENCE = new BlockSpaceTimeTurbulence();
	public static final Block TIMECOMPRESSOR = new BlockTimeCompressor();
	public static final Block HOURGLASS = new BlockHourGlass();
}
