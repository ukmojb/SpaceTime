package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.SpaceFrozenHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventSpaceFrozen {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        World world = event.player.world;
        if (SpaceFrozenHelper.getSpaceFrozen(player) > 0) {
            if (SpaceFrozenHelper.getSpaceFrozen(player) >= 100) {
                SpaceFrozenHelper.setIsSpaceFrozen(player, true);

                SpaceFrozenHelper.removeSpaceFrozen(player, 1);

            } else {
                if (!SpaceFrozenHelper.getIsSpaceFrozen(player)) {
                    if (world.getTotalWorldTime() % 4 == 0) {
                        SpaceFrozenHelper.removeSpaceFrozen(player, 1);
                    }
                } else {
                    SpaceFrozenHelper.removeSpaceFrozen(player, 1);
                }
            }
        } else {
            SpaceFrozenHelper.setIsSpaceFrozen(player, false);
        }

        if (SpaceFrozenHelper.getIsSpaceFrozen(player)) {
            player.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 1, 0, true, true));
        }

    }


}
