package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.util.Tools;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

public class MessageSpacePhase0 implements IMessageHandler<MessageSpacePhase0, IMessage>, IMessage  {

    public int entid;

    public final BlockPos[] blockPosList1 = new  BlockPos[]{
            new BlockPos(47, 80, -13),
            new BlockPos(47, 80, -4),
            new BlockPos(47, 80, 5),
            new BlockPos(56, 80, -13),
            new BlockPos(56, 80, -4),
            new BlockPos(56, 80, 5),
            new BlockPos(65, 80, -13),
            new BlockPos(65, 80, -4),
            new BlockPos(65, 80, 5),

    };
    public final BlockPos[] blockPosList2 = new  BlockPos[]{
            new BlockPos(55, 82, -5),
            new BlockPos(55, 82, 4),
            new BlockPos(55, 82, 13),
            new BlockPos(64, 82, -5),
            new BlockPos(64, 82, 4),
            new BlockPos(64, 82, 13),
            new BlockPos(73, 82, -5),
            new BlockPos(73, 82, 4),
            new BlockPos(73, 82, 13),

    };

    public MessageSpacePhase0() {
    }

    public MessageSpacePhase0(int entid) {
        this.entid = entid;
    }

    public void fromBytes(ByteBuf buf) {
        this.entid = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entid);
    }


    public IMessage onMessage(MessageSpacePhase0 message, MessageContext ctx) {

        World world = ctx.getServerHandler().player.world;
        EntitySpace entitySpace = (EntitySpace) world.getEntityByID(message.entid);
        Random random = new Random();
        int truenum;
        boolean pass = true;
        if (entitySpace.getAttackTick() <= 0) {
            while (true) {
                int num = random.nextInt(9);
                if (entitySpace != null && entitySpace.world.getBlockState(blockPosList1[num]).getBlock() != STBlocks.SPACETIMETURBULENCE) {
                    truenum = num;
                    break;
                }
            }

            entitySpace.setAttackTick(100);
            Tools.setBlockAABB(blockPosList1[truenum], blockPosList2[truenum], STBlocks.SPACETIMETURBULENCE, entitySpace);

            for (BlockPos pos : blockPosList1) {
                if (world.getBlockState(pos).getBlock() != STBlocks.SPACETIMETURBULENCE) pass = false;
            }
            if (pass) {
                Tools.setBlockAABB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13), Blocks.AIR, entitySpace);
                for (BlockPos pos : blockPosList1) {
                    world.createExplosion(entitySpace, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4, 6, false);
                }

                EntityPlayer player = world.getClosestPlayer(entitySpace.posX, entitySpace.posY, entitySpace.posZ, 200, false);

                if (player != null) {
                    world.addWeatherEffect(new EntityLightningBolt(world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), true));
                }
            }
        }
        return null;
    }
}

