package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.util.ISidedFunction;
import net.minecraft.item.Item;

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

	}

	public <F, T> T apply(ISidedFunction<F, T> func, F input) {
		return func.applyServer(input);
	}

}
