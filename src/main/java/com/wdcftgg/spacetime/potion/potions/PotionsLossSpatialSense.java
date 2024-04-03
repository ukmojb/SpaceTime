package com.wdcftgg.spacetime.potion.potions;

import com.google.common.collect.Lists;
import com.wdcftgg.spacetime.potion.PotionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/8 16:38
 */
public class PotionsLossSpatialSense extends PotionBase {

    public PotionsLossSpatialSense() {
        super("lossspatialsense", true, 0Xf051ff, 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public List<ItemStack> getCurativeItems(){
        return Lists.newArrayList();
    }

    @Override
    public void performEffect(EntityLivingBase ent, int amplifier)
    {
        if (!ent.isElytraFlying()) {
            if (ent.world.getBlockState(ent.getPosition().up(2)).getBlock() == Blocks.AIR) {
                ent.setNoGravity(false);
                ent.motionY += 0.05D;
            } else {
                ent.setNoGravity(true);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 2 == 0;
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        entityLiving.setNoGravity(false);
    }
}