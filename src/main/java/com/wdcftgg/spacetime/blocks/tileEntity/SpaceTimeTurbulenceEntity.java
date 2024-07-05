package com.wdcftgg.spacetime.blocks.tileEntity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 18:21
 */
public class SpaceTimeTurbulenceEntity extends TileEntity implements ITickable {

    @SuppressWarnings ("rawtypes")
    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 10 == 0) {
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.pos))) {
                if (livingbase.isDead) continue;
                if (!(livingbase instanceof EntityPlayer)) continue;
                if (livingbase.getHealth() >= livingbase.getMaxHealth() * 0.01f){
                    livingbase.setHealth(livingbase.getHealth() * 0.97f);
                }
                if (livingbase.getHealth() <= livingbase.getMaxHealth() * 0.01f){
                    PotionEffect wk = new PotionEffect(MobEffects.WEAKNESS, 100,  0, false, false);
                    PotionEffect mf = new PotionEffect(MobEffects.MINING_FATIGUE, 100,  0, false, false);
                    PotionEffect sn = new PotionEffect(MobEffects.SLOWNESS, 100,  0, false, false);
                    livingbase.addPotionEffect(wk);
                    livingbase.addPotionEffect(mf);
                    livingbase.addPotionEffect(sn);
                }
            }
        }
    }
}
