package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.entity.ai.time.TimeAIAttackMelee;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIMoveTowardsRestriction;
import com.wdcftgg.spacetime.network.MessageTimeParticle;
import com.wdcftgg.spacetime.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/2 23:47
 */
public class EntityTimePhantom extends EntityMob {
    public EntityTimePhantom(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 1.8F);
    }
    private int x;
    private int y;
    private int z;




    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }


    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        x = this.getPosition().getX();
        y = this.getPosition().getY();
        z = this.getPosition().getZ();
        if (!this.world.isRemote) {
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east().north().down(2), this.getPosition().west().south().up(2)))) {
                if (!(livingbase instanceof EntityTime) && !(livingbase instanceof EntityTimePhantom)) {
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 3);
                }
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (!this.world.isRemote) {
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(3).north(3).down(5), this.getPosition().west(3).south(3).up(5)))) {
                if (!(livingbase instanceof EntityTime) && !(livingbase instanceof EntityTimePhantom)) {
                    livingbase.knockBack(this, 1f, livingbase.getLookVec().x, livingbase.getLookVec().y);
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 5);
                }
            }
        }
        if (source.isMagicDamage()) {
            world.removeEntity(this);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTimeParticle(this.getPosition(), false, true), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("x", x);
        compound.setInteger("y", y);
        compound.setInteger("z", z);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        x = compound.getInteger("x");
        y = compound.getInteger("y");
        z = compound.getInteger("z");
    }


    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new TimeAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new TimeAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.5));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new TimeAIHurtByTarget(this, true, new Class[0]));
    }
}
