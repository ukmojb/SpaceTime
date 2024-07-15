package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.init.ModSounds;
import com.wdcftgg.spacetime.potion.potions.PotionsMovetoplayer;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import net.minecraft.command.CommandException;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityPortal extends EntityLiving {

    private static final DataParameter<Integer> mode_type = EntityDataManager.<Integer>createKey(EntitySword.class, DataSerializers.VARINT);
    private int mode = 0;
    private int cooling = 0;
    private boolean canSummon = false;


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

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("mode", mode);
        compound.setInteger("cooling", cooling);
        compound.setBoolean("canSummon", canSummon);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getInteger("mode");
        cooling = compound.getInteger("cooling");
        canSummon = compound.getBoolean("canSummon");
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
            }
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        Random random = new Random();
        this.setCooling(random.nextInt(50));

        return livingdata;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    @Override
    protected void onDeathUpdate()
    {
        ++this.deathTime;

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
}
