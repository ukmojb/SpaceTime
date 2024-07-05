package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.event.*;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class ServerProxy extends CommonProxy {

    public static List<Integer> spacelist = new ArrayList<>();

    public ServerProxy() {
    }

    public void onPreInit() {
        super.onPreInit();
    }

    public void onPostInit() {
        super.onPostInit();
    }

    public void onInit(){
        super.onInit();
        MinecraftForge.EVENT_BUS.register(new EventSword());
        MinecraftForge.EVENT_BUS.register(new EventModuleXP());
        MinecraftForge.EVENT_BUS.register(new EventChanceName());
        MinecraftForge.EVENT_BUS.register(new EventExplosion());
        MinecraftForge.EVENT_BUS.register(new EventHeterospaceLiving());
        MinecraftForge.EVENT_BUS.register(new EventSpace());
        MinecraftForge.EVENT_BUS.register(new EventTime());
        MinecraftForge.EVENT_BUS.register(new EventTimeCrack());
        MinecraftForge.EVENT_BUS.register(new EventWool());
    }
}
