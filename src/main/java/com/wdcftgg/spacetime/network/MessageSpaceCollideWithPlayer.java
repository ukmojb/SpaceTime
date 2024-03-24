package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.event.EventTimeBack;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/14 19:16
 */
public class MessageSpaceCollideWithPlayer implements IMessageHandler<MessageSpaceCollideWithPlayer, IMessage>, IMessage  {

    public double num;
    public long uuid0;
    public long uuid1;

    public MessageSpaceCollideWithPlayer() {

    }

    public MessageSpaceCollideWithPlayer(double num, EntityPlayer player) {
        this.num = num;
        this.uuid0 = player.getUniqueID().getMostSignificantBits();
        this.uuid1 = player.getUniqueID().getLeastSignificantBits();
    }

    public void fromBytes(ByteBuf buf) {
        this.num = buf.readDouble();
        this.uuid0 = buf.readLong();
        this.uuid1 = buf.readLong();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.num);
        buf.writeLong(this.uuid0);
        buf.writeLong(this.uuid1);
    }


    public IMessage onMessage(MessageSpaceCollideWithPlayer message, MessageContext ctx) {
        UUID uuid = new UUID(message.uuid0, message.uuid1);

        if (ctx.side.isServer() && ctx.getServerHandler().player.world.getPlayerEntityByUUID(uuid) != null) {
            ctx.getServerHandler().player.world.getPlayerEntityByUUID(uuid).attackEntityFrom(DamageSource.GENERIC, (float) message.num);
        }
        return null;
    }
}
