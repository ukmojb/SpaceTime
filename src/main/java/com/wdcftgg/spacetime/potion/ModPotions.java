package com.wdcftgg.spacetime.potion;

import com.wdcftgg.spacetime.potion.potions.PotionsHeterospace;
import com.wdcftgg.spacetime.potion.potions.PotionsSwordcore;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {


    public static final Potion heterospace = new PotionsHeterospace();
    public static final Potion swordcore = new PotionsSwordcore();
//    public static final PotionZenHeart ZEN_HEART = new PotionZenHeart(false, 0xcccc00, "zen_heart", 1);



    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt) {
        evt.getRegistry().register(heterospace);
        evt.getRegistry().register(swordcore);
    }
}
