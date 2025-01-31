package com.wdcftgg.spacetime.potion.potions;

import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraftforge.common.MinecraftForge;

public class PotionsTest extends PotionBase {
    public PotionsTest() {
        super("test", false, 0X0036a6, 666);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }
}
