package com.wdcftgg.spacetime.network;

import com.wdcftgg.spacetime.client.render.block.RenderTimeAltarCore;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/7 6:44
 */
public class MessageTimeAltarCore implements IMessageHandler<MessageTimeAltarCore, IMessage>, IMessage  {
    private float p;
    private float a;
    private float g;

    public MessageTimeAltarCore() {
    }

    public MessageTimeAltarCore(float p, float a, float g) {
        this.p = p;
        this.a = a;
        this.g = g;
    }

    public void fromBytes(ByteBuf buf) {
        this.p = buf.readFloat();
        this.a = buf.readFloat();
        this.g = buf.readFloat();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.p);
        buf.writeFloat(this.a);
        buf.writeFloat(this.g);
    }


    public IMessage onMessage(MessageTimeAltarCore message, MessageContext ctx) {
        RenderTimeAltarCore.p = partial(message.p);
        RenderTimeAltarCore.a = alpha(message.a);
        RenderTimeAltarCore.g = angle(message.g);
        return null;
    }

    float partial(float p){
        float x = p;
        x += 0.5f;
        return x;
    }

    float alpha(float a){
        float x = a;
        x += 0.01f;
        if (x >= 0.9f){
            return 0.9f;
        }
        return x;
    }

    float angle(float g){
        float x = g;
        if (RenderTimeAltarCore.a == 0.9f){
            x += (float) 1;
            if (x > 360.0f){
                return 0f;
            }
            return x;
        } else {
            return 0f;
        }
    }
}
