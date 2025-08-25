package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncModeSpace implements IMessageHandler<MessageSyncModeSpace, IMessage>, IMessage  {

    public String mode;


    public MessageSyncModeSpace() {
    }

    public MessageSyncModeSpace(String mode) {
        this.mode = mode;
    }

    public void fromBytes(ByteBuf buf) {
        this.mode = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, mode);
    }


    public IMessage onMessage(MessageSyncModeSpace message, MessageContext ctx) {
        for (Integer id : CommonProxy.spacelist) {
            EntitySpace entitySpace = (EntitySpace) ctx.getServerHandler().player.world.getEntityByID(id);
            if (entitySpace != null) {
                entitySpace.setMode(message.mode);
            } else {
                CommonProxy.spacelist.remove(id);
            }
        }
        return null;
    }
}