package com.wdcftgg.spacetime.potion.potions;

import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/29 12:21
 */
public class PotionsHeterospace extends PotionBase {

    public PotionsHeterospace() {
        super("heterospace", false, 0X0036a6, 0);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }

}
