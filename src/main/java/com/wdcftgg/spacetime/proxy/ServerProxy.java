package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

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
        MinecraftForge.EVENT_BUS.register(new EventSword());
        MinecraftForge.EVENT_BUS.register(new EventModuleXP());
        MinecraftForge.EVENT_BUS.register(new EventTimeBack());
    }
}
