package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntitySpace;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

public class MessageSpaceWeakness implements IMessageHandler<MessageSpaceWeakness, IMessage>, IMessage  {

    public int entid;

    public MessageSpaceWeakness() {
    }

    public MessageSpaceWeakness(int entid) {
        this.entid = entid;
    }

    public void fromBytes(ByteBuf buf) {
        this.entid = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entid);
    }


    public IMessage onMessage(MessageSpaceWeakness message, MessageContext ctx) {

        World world = ctx.getServerHandler().player.world;
        EntitySpace entitySpace = (EntitySpace) world.getEntityByID(message.entid);
        if (!world.isRemote) {
            BlockPos newpos = getRandomOutsidePosition();
            if (entitySpace != null) {
                entitySpace.attemptTeleport(newpos.getX(), entitySpace.posY, newpos.getZ());
                entitySpace.setMode("sprinting");
            }
        }

        return null;
    }

    public BlockPos getRandomOutsidePosition() {
        AxisAlignedBB box = new AxisAlignedBB(new BlockPos(44, 150, -16), new BlockPos(76, 153, 16));
        Random random = new Random();

        int minX = (int) box.minX;
        int maxX = (int) box.maxX;
        int minZ = (int) box.minZ;
        int maxZ = (int) box.maxZ;

        int side = random.nextInt(4);

        int x = 0;
        int z = 0;
        int y = 150;

        switch (side) {
            case 0:
                x = random.nextInt(maxX - minX + 1) + minX;
                z = maxZ + 1;
                break;
            case 1:
                x = random.nextInt(maxX - minX + 1) + minX;
                z = minZ - 1; // 低于 z 最小值一格
                break;
            case 2:
                x = minX - 1;
                z = random.nextInt(maxZ - minZ + 1) + minZ;
                break;
            case 3:
                x = maxX + 1;
                z = random.nextInt(maxZ - minZ + 1) + minZ;
                break;
        }

        return new BlockPos(x, y, z);
    }
}