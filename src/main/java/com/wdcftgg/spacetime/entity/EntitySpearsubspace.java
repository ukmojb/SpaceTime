package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntitySpearsubspace extends EntityThrowableCopy {

    private int mode = 0;

    public EntitySpearsubspace(World worldIn)
    {
        super(worldIn);
        this.isImmuneToFire = true;
        this.setSize(1.5F, 1.5F);
    }

    public EntitySpearsubspace(World worldIn, EntityLivingBase throwerIn, int mode)
    {
        super(worldIn, throwerIn);
        this.isImmuneToFire = true;
        this.setSize(1.5F, 1.5F);
        this.setMode(mode);
    }

    public EntitySpearsubspace(World worldIn, double x, double y, double z, int mode)
    {
        super(worldIn, x, y, z);
        this.isImmuneToFire = true;
        this.setSize(1.5F, 1.5F);
        this.setMode(mode);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("mode", mode);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getInteger("mode");
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (world.isRemote) return;
        if (this.ticksExisted <= 3) return;
        if (mode == 0) {
            if (result.entityHit != null) {
                Entity entity = result.entityHit;
                if (entity instanceof EntitySpace2) return;
                if (entity instanceof EntityPortal) return;
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase livingBase = (EntityLivingBase) entity;
                    livingBase.attackEntityFrom(DamageSource.GENERIC, 30);
                    livingBase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 10);
                    livingBase.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 5 * 20, 0, true, true));

                    this.setDead();
                }
            }
            if (result.hitVec != null) {
                if (!world.getBlockState(new BlockPos(result.hitVec)).isFullBlock()) return;
                List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPosition().down().east().south(), this.getPosition().up().west().north()));

                for (Entity entity : entityList) {
                    if (entity instanceof EntitySpace2) continue;
                    if (entity instanceof EntityPortal) return;
                    if (entity instanceof EntityLivingBase) {
                        EntityLivingBase livingBase = (EntityLivingBase) entity;
                        livingBase.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 3 * 20, 0, true, true));
                    }
                }

                this.setDead();
            }
        }
        if (mode == 1) {
            if (result.entityHit != null) {
                Entity entity = result.entityHit;
                if (entity instanceof EntitySpace2) return;
                if (entity instanceof EntityPortal) return;
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase livingBase = (EntityLivingBase) entity;

                    world.createExplosion(this, this.posX, this.posY, this.posZ, 3, true);
                    livingBase.attackEntityFrom(DamageSource.GENERIC, 15);
                    livingBase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 5);
                    livingBase.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 2 * 20, 0, true, true));

                    this.setDead();
                }
            }
            if (result.hitVec != null) {
                if (!world.getBlockState(new BlockPos(result.hitVec)).isFullBlock()) return;
                world.createExplosion(this, this.posX, this.posY, this.posZ, 3, true);

                List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPosition().down().east().south(), this.getPosition().up().west().north()));

                for (Entity entity : entityList) {
                    if (entity instanceof EntitySpace2) continue;
                    if (entity instanceof EntityPortal) return;
                    if (entity instanceof EntityLivingBase) {
                        EntityLivingBase livingBase = (EntityLivingBase) entity;
                        livingBase.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 1 * 20, 0, true, true));
                    }
                }

                this.setDead();
            }
        }
    }


    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (mode == 0) {
                if (this.ticksExisted >= 250) this.setDead();
            }
            if (mode == 1) {
                if (world.getWorldTime() % 3 == 0) {

                }
            }
        }
    }
}
