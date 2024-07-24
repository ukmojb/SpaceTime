package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.init.ModSounds;
import com.wdcftgg.spacetime.potion.potions.PotionsMovetoplayer;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.command.CommandException;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityPortal extends EntityLiving {

    private int mode = 0;
    private int cooling = 0;
    private boolean canSummon = false;
    private boolean canAttack = false;
    private NBTTagCompound nbtent = new NBTTagCompound();


    public EntityPortal(World worldIn)
    {
        super(worldIn);
        this.setSize(2F, 2F);
        this.setNoGravity(true);
        this.setNoAI(true);
    }
    public EntityPortal(World worldIn, int mode)
    {
        super(worldIn);
        this.setSize(2F, 2F);
        this.setMode(mode);
        this.setNoGravity(true);
        this.setNoAI(true);
    }
    public EntityPortal(World worldIn, int mode, NBTTagCompound nbtent)
    {
        super(worldIn);
        this.setSize(2F, 2F);
        this.setMode(mode);
        this.setNoGravity(true);
        this.setNoAI(true);
        this.setNbtent(nbtent);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("mode", mode);
        compound.setInteger("cooling", cooling);
        compound.setBoolean("canSummon", canSummon);
        compound.setBoolean("canAttack", canAttack);
        compound.setTag("nbtent", nbtent);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getInteger("mode");
        cooling = compound.getInteger("cooling");
        canSummon = compound.getBoolean("canSummon");
        canAttack = compound.getBoolean("canAttack");
        nbtent = (NBTTagCompound) compound.getTag("nbtent");
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (mode == 1) {
                if (!ServerProxy.space2list.isEmpty() && world.getTotalWorldTime() % 20 == 0) {
                    for (Integer id : ServerProxy.space2list) {
                        EntitySpace2 entitySpace2 = (EntitySpace2) world.getEntityByID(id);
                        if (entitySpace2 != null) {
                            this.rotationYaw = entitySpace2.rotationYaw;
                            this.rotationPitch = entitySpace2.rotationPitch;
                        } else {
                            ServerProxy.space2list.remove(id);
                        }
                    }
                }

                if (cooling > 0) cooling--;

                if (isCanSummon() && getCooling() <= 0) {
                    Random random = new Random();
                    int rand = random.nextInt(Config.CANSUMMONMOB.length);

                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setString("id", Config.CANSUMMONMOB[rand]);
                    Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, this.posX, this.posY, this.posZ, true);

                    if (entity == null)
                    {
                        SpaceTime.Log("The entity summoned by the portal does not exist, please check your config file");
                    } else {
                        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, entity.rotationYaw, entity.rotationPitch);
                        ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
                        ((EntityLiving) entity).addPotionEffect(new PotionEffect(new PotionsMovetoplayer(), 100, 0));
                        setCooling(Config.SUMMONMOBCOOLING + random.nextInt(50));
                    }
                }
                if (this.ticksExisted >= 100) this.setDead();
            }
            if (mode == 2) {
                if (this.ticksExisted >= 15) this.setDead();
            }

            if (mode == 3) {
                if (!ServerProxy.space2list.isEmpty() && world.getTotalWorldTime() % 20 == 0) {
                    for (Integer id : ServerProxy.space2list) {
                        EntitySpace2 entitySpace2 = (EntitySpace2) world.getEntityByID(id);
                        if (entitySpace2 != null) {
                            this.rotationYaw = entitySpace2.rotationYaw;
                            this.rotationPitch = entitySpace2.rotationPitch;
                        } else {
                            ServerProxy.space2list.remove(id);
                        }
                    }
                }

                Entity entity = Tools.getEntityFromNbt(nbtent, world);
                if (entity != null) {
                    this.shoot(entity, this, this.rotationPitch, this.rotationYaw, 0.0F, 1.5F, 1.0F);
                }
                if (this.ticksExisted >= 20) this.setDead();
            }
            if (mode == 4) {
                if (!ServerProxy.space2list.isEmpty() && world.getTotalWorldTime() % 20 == 0) {
                    for (Integer id : ServerProxy.space2list) {
                        EntitySpace2 entitySpace2 = (EntitySpace2) world.getEntityByID(id);
                        if (entitySpace2 != null) {
                            this.rotationYaw = entitySpace2.rotationYaw;
                            this.rotationPitch = entitySpace2.rotationPitch;
                        } else {
                            ServerProxy.space2list.remove(id);
                        }
                    }
                }

                if (cooling > 0) cooling--;

                if (isCanAttack() && getCooling() <= 0) {
                    Random random = new Random();

                    EntitySpearsubspace spearsubspace = new EntitySpearsubspace(world, this.posX, this.posY, this.posZ, 1);
                    spearsubspace.shoot(this, this.prevRotationPitch, this.getRotationYawHead(), 0.0F, 1.5F, 1.0F);
                    world.spawnEntity(spearsubspace);

                    setCooling(Config.SUMMONMOBCOOLING + random.nextInt(50));
                }
                if (this.ticksExisted >= 100) this.setDead();
            }
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        Random random = new Random();
        this.setCooling(random.nextInt(80));

        return livingdata;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }


    protected void onDeathUpdate()
    {
        super.onDeathUpdate();
        if (this.deathTime == 20)
        {
            this.setDead();
        }
    }

    public boolean isCanSummon() {
        return canSummon;
    }

    public void setCanSummon(boolean canSummon) {
        this.canSummon = canSummon;
    }

    public int getCooling() {
        return cooling;
    }

    public void setCooling(int cooling) {
        this.cooling = cooling;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }

    public int getMode()
    {
        return this.mode;
    }

    public NBTTagCompound getNbtent() {
        return nbtent;
    }

    public void setNbtent(NBTTagCompound nbtent) {
        this.nbtent = nbtent;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    private void shoot(Entity entity, Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy){
        if (entity instanceof IProjectile) {
            IProjectile projectile = (IProjectile) entity;
            float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
            float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
            float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
            projectile.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
            entity.motionX += entityThrower.motionX;
            entity.motionZ += entityThrower.motionZ;

            if (!entityThrower.onGround) {
                this.motionY += entityThrower.motionY;
            }
        }
    }
}
