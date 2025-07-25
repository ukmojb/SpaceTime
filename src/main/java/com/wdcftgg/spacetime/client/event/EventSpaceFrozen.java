package com.wdcftgg.spacetime.client.event;


import com.wdcftgg.spacetime.util.SpaceFrozenHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EventSpaceFrozen {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player != null) {
            if (SpaceFrozenHelper.getIsSpaceFrozen(mc.player)) {
                event.getMovementInput().moveForward = 0f;
                event.getMovementInput().moveStrafe = 0f;
                event.getMovementInput().jump = false;
            }
        }
    }
}
