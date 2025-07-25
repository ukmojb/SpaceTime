package com.wdcftgg.spacetime.potion.potions;

import com.google.common.collect.Lists;
import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class PotionsSpaceStop extends PotionBase {

    public PotionsSpaceStop() {
        super("space_stop", false, 0X0036a6, 666);
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
        if (ent instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) ent;
            if (player.world.getTotalWorldTime() % 20 == 0) {
                player.sendStatusMessage(new TextComponentTranslation("spacetime.space_stop.say"), true);
            }
            player.motionX = 0;
            player.motionY = 0;
            player.motionZ = 0;

            // 取消跳跃和下落
            player.velocityChanged = true;
            player.fallDistance = 0;

            // 让玩家始终停在原地
            player.posX = player.prevPosX;
            player.posY = player.prevPosY;
            player.posZ = player.prevPosZ;
            player.world.updateEntityWithOptionalForce(player, false);
        }
        ent.motionX = 0;
        ent.motionY = 0;
        ent.motionZ = 0;
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
