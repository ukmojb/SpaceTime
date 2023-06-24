package com.wdcftgg.spacetime.Network;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 22:56
 */
public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SpaceTime.MODID);

    public static void init()
    {
        INSTANCE.registerMessage(MessageTimeAltarCore.class, MessageTimeAltarCore.class, 0, Side.CLIENT);
    }
}
