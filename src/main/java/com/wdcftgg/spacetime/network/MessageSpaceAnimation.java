package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.event.EventTimeBack;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/5 18:27
 */
public class MessageSpaceAnimation implements IMessageHandler<MessageSpaceAnimation, IMessage>, IMessage  {

    public double time;

    public MessageSpaceAnimation() {
    }

    public MessageSpaceAnimation(double num) {
        this.time = num;
    }

    public void fromBytes(ByteBuf buf) {
        this.time = buf.readDouble();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.time);
    }


    public IMessage onMessage(MessageSpaceAnimation message, MessageContext ctx) {
        EntitySpace.animationTick = message.time;
        return null;
    }
}