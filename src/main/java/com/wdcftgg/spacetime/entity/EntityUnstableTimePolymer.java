package com.wdcftgg.spacetime.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 8:10
 */
public class EntityUnstableTimePolymer extends EntityThrowable {
    public EntityUnstableTimePolymer(World worldIn)
    {
        super(worldIn);
    }

    public EntityUnstableTimePolymer(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityUnstableTimePolymer(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.hitVec != null)
        {
            if (!this.world.isRemote)
            {
                BlockPos pos = new BlockPos(result.hitVec);
                EntityTimeCrack timecrack = new EntityTimeCrack(world);
                timecrack.setPosition(posX, posY, posZ);
                world.spawnEntity(timecrack);
                this.world.setEntityState(this, (byte)3);
                this.setDead();
            }
        }

    }
}
