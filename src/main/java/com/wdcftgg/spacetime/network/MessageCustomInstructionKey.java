package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.util.Tools;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

public class MessageCustomInstructionKey implements IMessageHandler<MessageCustomInstructionKey, IMessage>, IMessage  {

    public String animationName;
    public String instructions;


    public MessageCustomInstructionKey() {
    }

    public MessageCustomInstructionKey(String animationName, String instructions) {
        this.animationName = animationName;
        this.instructions = instructions;
    }

    public void fromBytes(ByteBuf buf) {
        this.animationName = ByteBufUtils.readUTF8String(buf);
        this.instructions = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, animationName);
        ByteBufUtils.writeUTF8String(buf, instructions);
    }


    public IMessage onMessage(MessageCustomInstructionKey message, MessageContext ctx) {
        for (Integer id : ServerProxy.space2list) {
            EntitySpace2 entitySpace2 = (EntitySpace2) ctx.getServerHandler().player.world.getEntityByID(id);
            if (entitySpace2 != null) {
                entitySpace2.CommandListener(message.animationName, message.instructions);
            } else {
                ServerProxy.space2list.remove(id);
            }
        }
        return null;
    }
}