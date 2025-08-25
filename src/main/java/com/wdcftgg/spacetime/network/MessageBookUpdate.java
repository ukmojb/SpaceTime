package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.item.BookBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBookUpdate implements IMessageHandler<MessageBookUpdate, IMessage>, IMessage  {
    private ItemStack itemStack;
    private int num;

    public MessageBookUpdate() {
    }

    public MessageBookUpdate(ItemStack itemStack, int num) {
        this.itemStack = itemStack;
        this.num = num;
    }

    public void fromBytes(ByteBuf buf) {
        this.itemStack = ByteBufUtils.readItemStack(buf);
        this.num = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.itemStack);
        buf.writeInt(this.num);
    }


    public IMessage onMessage(MessageBookUpdate message, MessageContext ctx) {

        if (message.itemStack.getItem() instanceof BookBase) {
            BookBase book = (BookBase) message.itemStack.getItem();
            book.setPageNum(message.itemStack, message.num);
        }
        return null;
    }
}
