package com.wdcftgg.spacetime.entity.ai.space;

import com.wdcftgg.spacetime.entity.EntitySpace2;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class Space2AILookIdle  extends EntityAIBase
{
    /** The entity that is looking idle. */
    private final EntitySpace2 entitySpace2;
    /** X offset to look at */
    private double lookX;
    /** Z offset to look at */
    private double lookZ;
    /** A decrementing tick that stops the entity from being idle once it reaches 0. */
    private int idleTime;

    public Space2AILookIdle(EntitySpace2 entitySpace2)
    {
        this.entitySpace2 = entitySpace2;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.entitySpace2.getRNG().nextFloat() < 0.02F;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.idleTime >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        double d0 = (Math.PI * 2D) * this.entitySpace2.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + this.entitySpace2.getRNG().nextInt(20);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        --this.idleTime;
        this.entitySpace2.getLookHelper().setLookPosition(this.entitySpace2.posX + this.lookX, this.entitySpace2.posY + (double)this.entitySpace2.getEyeHeight(), this.entitySpace2.posZ + this.lookZ, (float)this.entitySpace2.getHorizontalFaceSpeed(), (float)this.entitySpace2.getVerticalFaceSpeed());
    }
}