package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.util.SpaceFrozenHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpaceFrozen implements IMessageHandler<MessageSpaceFrozen, IMessage>, IMessage  {
    private int id;
    private int num;

    public MessageSpaceFrozen() {
    }

    public MessageSpaceFrozen(int id, int num) {
        this.id = id;
        this.num = num;
    }

    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.num = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeInt(this.num);
    }


    public IMessage onMessage(MessageSpaceFrozen message, MessageContext ctx) {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null) {
            World world = mc.player.world;

            if (world.getEntityByID(message.id) != null) {
                Entity entity = (Entity) world.getEntityByID(message.id);

                if (entity != null) {
                    SpaceFrozenHelper.setSpaceFrozen(entity, message.num);
                }
            }
        }
        return null;
    }
}
