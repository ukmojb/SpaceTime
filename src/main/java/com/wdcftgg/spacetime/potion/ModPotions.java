package com.wdcftgg.spacetime.potion;

import com.wdcftgg.spacetime.potion.potions.*;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {


    public static final Potion heterospace = new PotionsHeterospace();
    public static final Potion LossSpatialSense = new PotionsLossSpatialSense();
    public static final Potion MoveToPlayer = new PotionsMovetoplayer();
    public static final Potion SpaceStop = new PotionsSpaceStop().registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "e27019d3-b606-439b-829c-2791a655d2b3", -50F, 0);
    public static final Potion test = new PotionsTest();



    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt) {
        evt.getRegistry().register(heterospace);
        evt.getRegistry().register(LossSpatialSense);
        evt.getRegistry().register(MoveToPlayer);
        evt.getRegistry().register(SpaceStop);
        evt.getRegistry().register(test);
    }
}
