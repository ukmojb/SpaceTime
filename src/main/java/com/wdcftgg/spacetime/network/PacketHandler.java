package com.wdcftgg.spacetime.network;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/13 23:29
 */
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE;

    public PacketHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(MessageTimeParticle.class, MessageTimeParticle.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageTimeBack.class, MessageTimeBack.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageTimeAltarCore.class, MessageTimeAltarCore.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageSpaceCollideWithPlayer.class, MessageSpaceCollideWithPlayer.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MessageParticleBurst.class, MessageParticleBurst.class, 4, Side.CLIENT);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("spacetime");
    }
}
