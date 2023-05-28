package com.wdcftgg.spacetime.potion.potions;

import com.wdcftgg.spacetime.init.ModSounds;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/20 22:29
 */
public class PotionsSwordcore extends PotionBase {
    public PotionsSwordcore() {
        super("swordcore", false, 0X0036a6, 1);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }

    @Override
    public void performEffect(EntityLivingBase ent, int amplifier)
    {
        if (ent.getActivePotionEffect(ModPotions.swordcore).getDuration() == 300){
            ent.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 1, true,  false));
            ent.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 0, true,  false));
            ent.addPotionEffect(new PotionEffect(MobEffects.HASTE, 300, 1, true,  false));
            ent.playSound(ModSounds.SWORDCORE_1, 1, 1);
            Timer timer = new Timer();
            TimerTask task = new TimerTask(){
                public void run(){
                    ent.playSound(ModSounds.SWORDCORE_2, 1, 1);
                }
            };
            timer.schedule(task,15000);
        }

    }

    @Override
    public boolean isInstant()
    {
        return true;
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 1 == 0;
    }
}
