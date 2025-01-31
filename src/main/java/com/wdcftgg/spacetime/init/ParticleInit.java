package com.wdcftgg.spacetime.init;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.particle.ParticleSpearsubspace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleInit {

    private static Minecraft mc = Minecraft.getMinecraft();
    private final static Class<?>[] argTypes = {String.class, int.class, boolean.class, int.class};
    public static EnumParticleTypes particleSpearsubspace;

    public static void registerParticle() {
        particleSpearsubspace = registerParticle("particle_spearsubspace", EnumParticleTypes.values().length, true, new ParticleSpearsubspace.Factory());

        SpaceTime.Log("SpaceTime ParticleInit, now particles have %s", EnumParticleTypes.values().length);
    }

    private static EnumParticleTypes registerParticle(String name, int value, boolean out32, IParticleFactory factory) {
        EnumParticleTypes enumParticleTypes = EnumHelper.addEnum(EnumParticleTypes.class, name, argTypes, name, value, out32, 0);
        Minecraft.getMinecraft().effectRenderer.registerParticle(value, factory);

        return enumParticleTypes;
    }
}
