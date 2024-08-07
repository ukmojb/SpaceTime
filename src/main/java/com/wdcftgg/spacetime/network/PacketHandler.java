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
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE;
    public static int num = 0;

    public PacketHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(MessageTimeParticle.class, MessageTimeParticle.class, num++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTimeBack.class, MessageTimeBack.class, num++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTimeAltarCore.class, MessageTimeAltarCore.class, num++, Side.CLIENT);
        INSTANCE.registerMessage(MessageParticleBurst.class, MessageParticleBurst.class, num++, Side.CLIENT);


        INSTANCE.registerMessage(MessageSpacePhase0.class, MessageSpacePhase0.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageCustomInstructionKey.class, MessageCustomInstructionKey.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageParticleKey.class, MessageParticleKey.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageSoundKey.class, MessageSoundKey.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpaceCollideWithPlayer.class, MessageSpaceCollideWithPlayer.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageSyncMode.class, MessageSyncMode.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpaceGetProjectile.class, MessageSpaceGetProjectile.class, num++, Side.SERVER);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("spacetime");
    }
}
