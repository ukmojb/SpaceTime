package com.wdcftgg.spacetime.proxy;

import com.wdcftgg.spacetime.event.EventModuleXP;
import com.wdcftgg.spacetime.event.EventSword;
import com.wdcftgg.spacetime.event.EventTimeBack;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

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
