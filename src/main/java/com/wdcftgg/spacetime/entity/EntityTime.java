package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.Network.MessageTimeParticle;
import com.wdcftgg.spacetime.Network.PacketHandler;
import com.wdcftgg.spacetime.SpaceTime;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Random;
/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/11 16:37
 */
public class EntityTime extends EntityMob {
    public EntityTime(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 1.8F);
    }

    private static final IAttribute LIFE_POWER = new RangedAttribute(null, "spacetime.attribute.life", 6.0, 0.0, 6.0).setShouldWatch(false);

    private String player;
    private int x;
    private int y;
    private int z;

    private float[] timenum = new float[]{
            2.0f,
            6.0f,
            18.0f,
            24.0f,
            50.0f,
            60.0f
    };

    public IMessage getCustomMessage() {
        return new MessageTimeParticle(this.getPosition());
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttributeMap().registerAttribute(LIFE_POWER);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        x = this.getPosition().getX();
        y = this.getPosition().getY();
        z = this.getPosition().getZ();
        if (!this.world.isRemote){
            if (this.getEntityAttribute(LIFE_POWER).getBaseValue() != 1.0D && this.getHealth() <= 0){
                this.setHealth(this.getMaxHealth());
                this.getEntityAttribute(LIFE_POWER).setBaseValue(this.getEntityAttribute(LIFE_POWER).getBaseValue()-1.0D);
                PacketHandler.INSTANCE.sendToAllAround(this.getCustomMessage(), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setString("player", "");
        compound.setInteger("x", x);
        compound.setInteger("y", y);
        compound.setInteger("z", z);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        player = compound.getString("player");
        x = compound.getInteger("x");
        y = compound.getInteger("y");
        z = compound.getInteger("z");
    }


    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.5));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }
}
