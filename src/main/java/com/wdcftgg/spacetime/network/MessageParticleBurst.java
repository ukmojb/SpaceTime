package com.wdcftgg.spacetime.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageParticleBurst implements IMessageHandler<MessageParticleBurst, IMessage>, IMessage  {
    public int x;
    public int y;
    public int z;
    public int block;
    public int meta;

    public MessageParticleBurst()
    {

    }

    public MessageParticleBurst(int x, int y, int z, int block, int meta)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
        this.meta = meta;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        block = buf.readInt();
        meta = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(block);
        buf.writeInt(meta);
    }

    @Override
    public IMessage onMessage(MessageParticleBurst m, MessageContext ctx) {

        try {
            Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(new BlockPos(m.x, m.y, m.z), Block.getBlockById(m.block).getStateFromMeta(m.meta));
        } catch(Exception x) { }

        return null;
    }
}
