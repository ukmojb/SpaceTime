package com.wdcftgg.spacetime.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 8:14
 */
public class EntityTimeCrack extends EntityLiving {
    public  EntityTimeCrack(World worldIn)
    {
        super(worldIn);
        this.setSize(1.2F, 1.8F);
        this.setNoAI(true);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200000000.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
}
