package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.event.*;
import com.wdcftgg.spacetime.util.ISidedFunction;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class CommonProxy {

	public static List<Integer> spacelist = new ArrayList<>();
	public static List<Integer> space2list = new ArrayList<>();

	public void registerItemRenderer(Item item, int meta, String id) {
		//Ignored
	}

	public void onPreInit() {
	}

	public void onPostInit() {
	}

	public void onInit(){

		MinecraftForge.EVENT_BUS.register(new EventModuleXP());
		MinecraftForge.EVENT_BUS.register(new EventChanceName());
		MinecraftForge.EVENT_BUS.register(new EventExplosion());
		MinecraftForge.EVENT_BUS.register(new EventHeterospaceLiving());
		MinecraftForge.EVENT_BUS.register(new EventSpace());
		MinecraftForge.EVENT_BUS.register(new EventTime());
		MinecraftForge.EVENT_BUS.register(new EventTimeCrack());
		MinecraftForge.EVENT_BUS.register(new EventWool());
		MinecraftForge.EVENT_BUS.register(new EventSpaceStop());
	}

	public <F, T> T apply(ISidedFunction<F, T> func, F input) {
		return func.applyServer(input);
	}

}
