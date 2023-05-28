package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import scala.Int;

import java.util.Random;

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
                timecrack.setPosition(posX, posY , posZ);
                for (String str : this.getTags()) {
                    Double time = (Double) Double.parseDouble(str);
                    Random r = new Random();
                    Double RandomChange = r.nextDouble();
                    Double chance = 200000.0;
                    if (time > 0) {
                        chance = time / chance  ;
                        if (chance > 1D) {
                            chance = 1D;
                        }
                    }
                    Double spownchange = (Double) 1D / chance ;
                    if (chance >= RandomChange){
                        world.spawnEntity(timecrack);
                    }

                }
                this.world.setEntityState(this, (byte)3);
                this.setDead();
            }
        }

    }
}
