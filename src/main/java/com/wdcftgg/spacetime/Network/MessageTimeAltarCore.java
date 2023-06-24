package com.wdcftgg.spacetime.Network;

import com.wdcftgg.spacetime.blocks.tileEntity.TimeAltarCoreEntity;
import io.netty.buffer.ByteBuf;
import lumaceon.mods.clockworkphase.ClockworkPhase;
import lumaceon.mods.clockworkphase.block.tileentity.TileEntityTimeSandCapacitor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/6/24 22:46
 */
public class MessageTimeAltarCore implements IMessageHandler<MessageTimeAltarCore, IMessage>, IMessage {
    int x, y, z, id;

    public MessageTimeAltarCore() {}

    public MessageTimeAltarCore(TimeAltarCoreEntity capacitor)
    {
        x = capacitor.getPos().getX();
        y = capacitor.getPos().getY();
        z = capacitor.getPos().getZ();
        id = capacitor.serializeNBT().getInteger("output");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
    }

    @Override
    public IMessage onMessage(MessageTimeAltarCore message, MessageContext ctx)
    {
        TileEntity te = ClockworkPhase.proxy.getStaticWorld().getTileEntity(new BlockPos(message.x, message.y, message.z));

        if(te instanceof TimeAltarCoreEntity)
        {
            TimeAltarCoreEntity altarCore = (TimeAltarCoreEntity)te;
            altarCore.serializeNBT().setInteger("output", message.id);
        }
        return null;
    }
}
