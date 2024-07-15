package com.wdcftgg.spacetime.potion.potions;

import com.google.common.collect.Lists;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class PotionsMovetoplayer extends PotionBase {

    public PotionsMovetoplayer() {
        super("move_to_player", false, 0X0036a6, 0);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }

    @Override
    public List<ItemStack> getCurativeItems(){
        return Lists.newArrayList();
    }

    @Override
    public void performEffect(EntityLivingBase ent, int amplifier)
    {
        EntityPlayer player = ent.world.getClosestPlayerToEntity(ent, 50);
        if (player != null) {
            double px = player.posX;
            double py = player.posY;
            double pz = player.posZ;
            double ex = ent.posX;
            double ey = ent.posY;
            double ez = ent.posZ;

            Vec3d vec3d = new Vec3d(px - ex, py - ey, pz - ez).normalize();
//            double speed = 0.5D;
            double speed = Config.MOBMOVESPEED;

            ent.motionX = vec3d.x * speed;
            ent.motionY = vec3d.y * speed * 0.5;
            ent.motionZ = vec3d.z * speed;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
}
