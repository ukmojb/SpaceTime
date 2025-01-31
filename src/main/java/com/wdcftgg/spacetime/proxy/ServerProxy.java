package com.wdcftgg.spacetime.proxy;


import com.wdcftgg.spacetime.event.*;
import com.wdcftgg.spacetime.util.ISidedFunction;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {

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
