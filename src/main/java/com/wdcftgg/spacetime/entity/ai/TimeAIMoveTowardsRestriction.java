package com.wdcftgg.spacetime.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 0:39
 */
public class TimeAIMoveTowardsRestriction extends EntityAIBase
{
    private final EntityCreature creature;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private final double movementSpeed;

    public TimeAIMoveTowardsRestriction(EntityCreature creatureIn, double speedIn)
    {
        this.creature = creatureIn;
        this.movementSpeed = speedIn;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.creature.isWithinHomeDistanceCurrentPosition())
        {
            return false;
        }
        else
        {
            BlockPos blockpos = this.creature.getHomePosition();
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.creature, 16, 7, new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()));

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                if (creature.isInWater() || creature.isInLava()) {
                    this.movePosX = vec3d.x * 1.2d;
                    this.movePosY = vec3d.y * 1.2d;
                    this.movePosZ = vec3d.z * 1.2d;

                } else  {
                    this.movePosX = vec3d.x;
                    this.movePosY = vec3d.y;
                    this.movePosZ = vec3d.z;
                }
                return true;
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        return !this.creature.getNavigator().noPath();
    }

    public void startExecuting()
    {
        this.creature.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}
