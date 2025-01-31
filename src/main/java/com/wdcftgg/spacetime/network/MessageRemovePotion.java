package com.wdcftgg.spacetime.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRemovePotion implements IMessageHandler<MessageRemovePotion, IMessage>, IMessage  {

    public int potid;


    public MessageRemovePotion() {
    }

    public MessageRemovePotion(int potid) {
        this.potid = potid;
    }

    public void fromBytes(ByteBuf buf) {
        this.potid = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.potid);
    }


    public IMessage onMessage(MessageRemovePotion message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().player;
        if (player != null) {
            Potion potion = Potion.getPotionById(message.potid);
            player.removePotionEffect(potion);
        }
        return null;
    }
}