package com.wdcftgg.spacetime.network;

import io.netty.buffer.ByteBuf;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpaceTimePocketWatchGui implements IMessageHandler<MessageSpaceTimePocketWatchGui, IMessage>, IMessage {
    public int buttonId;

    public MessageSpaceTimePocketWatchGui() {
    }

    public MessageSpaceTimePocketWatchGui(int buttonId) {
        this.buttonId = buttonId;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.buttonId);
    }

    public void fromBytes(ByteBuf buf) {
        this.buttonId = buf.readInt();
    }

    public IMessage onMessage(MessageSpaceTimePocketWatchGui message, MessageContext ctx) {
        if (ctx.side.isServer() && ctx.getServerHandler().player != null) {
            EntityPlayer player = ctx.getServerHandler().player;
            ItemStack is = player.inventory.getCurrentItem();
            if (!is.isEmpty() && NBTHelper.hasTag(is, "pw_modules")) {
                ItemStack[] inventory = NBTHelper.getInventoryFromNBTTag(is, "pw_modules");
                if (message.buttonId >= 0 && message.buttonId < inventory.length) {
                    NBTHelper.setBoolean(inventory[message.buttonId], "cp_active", !NBTHelper.getBoolean(inventory[message.buttonId], "cp_active"));
                    NBTHelper.setNBTTagListFromInventory(is, "pw_modules", inventory);
                }
            }
        }

        return null;
    }
}
