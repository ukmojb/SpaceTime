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

public class PotionsTest extends PotionBase {
    public PotionsTest() {
        super("test", false, 0X0036a6, 666);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }
}
