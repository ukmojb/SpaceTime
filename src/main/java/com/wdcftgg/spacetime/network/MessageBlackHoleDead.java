package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.event.EventTimeBack;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBlackHoleDead implements IMessageHandler<MessageBlackHoleDead, IMessage>, IMessage  {
    public boolean b;
    public boolean clear;

    public MessageBlackHoleDead() {
    }


    public void fromBytes(ByteBuf buf) {
    }

    public void toBytes(ByteBuf buf) {
    }


    public IMessage onMessage(MessageBlackHoleDead message, MessageContext ctx) {
        if (!ServerProxy.spacelist.isEmpty()) {
            EntitySpace entitySpace = (EntitySpace) ctx.getServerHandler().player.world.getEntityByID(ServerProxy.spacelist.get(0));
            if (entitySpace != null) {
                entitySpace.blackHoleDead();
            }
        }
        return null;
    }
}

