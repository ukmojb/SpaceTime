package com.wdcftgg.spacetime.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySpearsubspace1 extends EntityLiving {

    public EntitySpearsubspace1(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 4F);
        this.isImmuneToFire = true;
        this.setNoAI(true);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(3).north(3).down(3), this.getPosition().west(3).south(3).up(3)))) {
                if (livingbase instanceof EntitySpearsubspace1) continue;
                if (livingbase instanceof EntitySpace2) continue;
                if (livingbase instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) livingbase;
                    if (player.isCreative() || player.isSpectator()) continue;
                }
                if (world.getTotalWorldTime() % 15 == 0) {
                    world.addWeatherEffect(new EntityLightningBolt(world, livingbase.posX, livingbase.posY, livingbase.posZ, false));
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 10);
                    livingbase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 5);
                }
            }

            if (world.getTotalWorldTime() % 30 == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, this.posX, this.posY + 3, this.posZ, true));
            }
            if (world.getTotalWorldTime() % 100 == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, this.posX, this.posY + 3, this.posZ, true));
            }

            if (this.ticksExisted >= 600) this.setDead();
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }


}
