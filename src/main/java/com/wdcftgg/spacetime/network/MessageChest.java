package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.BlockTimeAltarCore;
import com.wdcftgg.spacetime.event.EventTimeBack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lumaceon.mods.clockworkphase.ClockworkPhase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/8 20:05
 */
public class MessageChest implements IMessageHandler<MessageChest, IMessage>, IMessage  {

    public int x;

    public int y;

    public int z;

    public ItemStack itemStack;

    public MessageChest() {
    }

    public MessageChest(BlockPos pos, ItemStack itemStack) {
        this.x = pos.getX();
        this.y = pos.getX();
        this.z = pos.getX();
        this.itemStack = itemStack;
    }

    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.itemStack = ByteBufUtils.readItemStack(buf);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        ByteBufUtils.writeItemStack(buf, this.itemStack);

    }


    public IMessage onMessage(MessageChest message, MessageContext ctx) {
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        World world = ClockworkPhase.proxy.getStaticWorld();
                SpaceTime.Log("www");
        if (world.getTileEntity(pos) != null) {
                SpaceTime.Log("www0");
            IInventory iinventory = null;
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof IInventory) {
                SpaceTime.Log("www1");
                iinventory = (IInventory)tileentity;
                for (int i=0;i<iinventory.getSizeInventory();i++) {
                    if (iinventory.getStackInSlot(i) == ItemStack.EMPTY) {
                        SpaceTime.Log("www2");
                        iinventory.setInventorySlotContents(i, message.itemStack);
//                        BlockTimeAltarCore.output = false;
                        break;
                    }
                }
            }
        }
        return null;
    }
}

