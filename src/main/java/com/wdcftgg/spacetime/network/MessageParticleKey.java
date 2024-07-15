package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageParticleKey implements IMessageHandler<MessageParticleKey, IMessage>, IMessage  {

    public String animationName;
    public String instructions;


    public MessageParticleKey() {
    }

    public MessageParticleKey(String animationName, String instructions) {
        this.animationName = animationName;
        this.instructions = instructions;
    }

    public void fromBytes(ByteBuf buf) {
        this.animationName = ByteBufUtils.readUTF8String(buf);
        this.instructions = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, animationName);
        ByteBufUtils.writeUTF8String(buf, instructions);
    }


    public IMessage onMessage(MessageParticleKey message, MessageContext ctx) {
        for (Integer id : ServerProxy.space2list) {
            EntitySpace2 entitySpace2 = (EntitySpace2) ctx.getServerHandler().player.world.getEntityByID(id);
            if (entitySpace2 != null) {
                entitySpace2.ParticleListener(message.animationName, message.instructions);
            } else {
                ServerProxy.space2list.remove(id);
            }
        }
        return null;
    }
}
