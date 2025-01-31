package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.entity.EntityPortal;
import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.util.Tools;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpaceGetProjectile implements IMessageHandler<MessageSpaceGetProjectile, IMessage>, IMessage  {

    public int ent;


    public MessageSpaceGetProjectile() {
    }

    public MessageSpaceGetProjectile(int ent) {
        this.ent = ent;
    }

    public void fromBytes(ByteBuf buf) {
        this.ent = buf.getInt(ent);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(ent);
    }


    public IMessage onMessage(MessageSpaceGetProjectile message, MessageContext ctx) {
        World world = ctx.getServerHandler().player.world;
        Entity entity = world.getEntityByID(message.ent);
        if (entity != null) {
            for (Integer id : CommonProxy.space2list) {
                EntitySpace2 entitySpace2 = (EntitySpace2) ctx.getServerHandler().player.world.getEntityByID(id);
                if (entitySpace2 != null) {
                    entitySpace2.addProjectileNBTList(Tools.saveEntityToNbt(entity));
                    EntityPortal portal = new EntityPortal(world, 2);
                    Tools.setPosition(portal, entity.getPosition());
                    entitySpace2.world.spawnEntity(portal);
                    entity.setDead();
                } else {
                    CommonProxy.space2list.remove(id);
                }
            }
        }
        return null;
    }
}