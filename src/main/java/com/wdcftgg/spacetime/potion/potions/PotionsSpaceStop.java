package com.wdcftgg.spacetime.potion.potions;

import com.google.common.collect.Lists;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
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
        } else {
            ent.motionX = 0;
            ent.motionY = 0;
            ent.motionZ = 0;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
            player.capabilities.setPlayerWalkSpeed(0.1F);
        }
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
            player.capabilities.setPlayerWalkSpeed(0F);
        }
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}
