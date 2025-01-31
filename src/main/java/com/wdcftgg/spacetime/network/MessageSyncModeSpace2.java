package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncModeSpace2 implements IMessageHandler<MessageSyncModeSpace2, IMessage>, IMessage  {

    public String mode;


    public MessageSyncModeSpace2() {
    }

    public MessageSyncModeSpace2(String mode) {
        this.mode = mode;
    }

    public void fromBytes(ByteBuf buf) {
        this.mode = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, mode);
    }


    public IMessage onMessage(MessageSyncModeSpace2 message, MessageContext ctx) {
        for (Integer id : CommonProxy.space2list) {
            EntitySpace2 entitySpace2 = (EntitySpace2) ctx.getServerHandler().player.world.getEntityByID(id);
            if (entitySpace2 != null) {
                entitySpace2.setNLMode(message.mode);
            } else {
                CommonProxy.space2list.remove(id);
            }
        }
        return null;
    }
}