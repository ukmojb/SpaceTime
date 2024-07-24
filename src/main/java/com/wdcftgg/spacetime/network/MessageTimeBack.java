package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.client.event.EventTimeBack;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/4 8:46
 */
public class MessageTimeBack implements IMessageHandler<MessageTimeBack, IMessage>, IMessage  {
    public boolean b;
    public boolean clear;

    public MessageTimeBack() {
    }

    public MessageTimeBack(boolean pass, boolean clear) {
        this.clear = clear;
        this.b = pass;
    }

    public void fromBytes(ByteBuf buf) {
        this.clear = buf.readBoolean();
        this.b = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.clear);
        buf.writeBoolean(this.b);
    }


    public IMessage onMessage(MessageTimeBack message, MessageContext ctx) {
        if (message.clear)  {
            EventTimeBack.images.clear();
        }
        EventTimeBack.isBack = message.b;
        return null;
    }
}

