package com.wdcftgg.spacetime.network;

import io.netty.buffer.ByteBuf;
import lumaceon.mods.clockworkphase.ClockworkPhase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/13 23:25
 */
public class MessageTimeParticle implements IMessageHandler<MessageTimeParticle, IMessage>, IMessage  {
    int x;
    int y;
    int z;
    boolean life;
    boolean isPhantom;

    public MessageTimeParticle() {
    }



    public MessageTimeParticle(BlockPos capacitor, boolean life, boolean isPhantom) {
        this.x = capacitor.getX();
        this.y = capacitor.getY();
        this.z = capacitor.getZ();
        this.life = life;
        this.isPhantom = isPhantom;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeBoolean(this.life);
        buf.writeBoolean(this.isPhantom);
    }

    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.life = buf.readBoolean();
        this.isPhantom = buf.readBoolean();
    }

    public IMessage onMessage(MessageTimeParticle message, MessageContext ctx) {
        Random r = new Random();
        if (message.isPhantom) {
            ClockworkPhase.proxy.getStaticWorld().spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, message.x, message.y + 1, message.z, r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5, 1);
            return null;
        }
        if (message.life) {
            for (int i = 0; i <= 25; i++) {
                ClockworkPhase.proxy.getStaticWorld().spawnParticle(EnumParticleTypes.TOTEM, message.x, message.y + 1, message.z, r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5, 1);
            }
        } else {
            ClockworkPhase.proxy.getStaticWorld().spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, message.x, message.y + 1, message.z, r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5, 1);
        }
        return null;
    }
}
