package com.wdcftgg.spacetime.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpaceReward implements IMessageHandler<MessageSpaceReward, IMessage>, IMessage  {

    public int rewardNum;


    public MessageSpaceReward() {
    }

    public MessageSpaceReward(int rewardNum) {
        this.rewardNum = rewardNum;
    }

    public void fromBytes(ByteBuf buf) {
        this.rewardNum = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(rewardNum);
    }


    public IMessage onMessage(MessageSpaceReward message, MessageContext ctx) {

        return null;
    }
}