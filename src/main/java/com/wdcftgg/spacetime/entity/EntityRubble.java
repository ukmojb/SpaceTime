package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.network.MessageParticleBurst;
import com.wdcftgg.spacetime.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class EntityRubble extends EntityThrowable {

    public static DamageSource rubble = (new DamageSource("rubble")).setProjectile();
    public static DataParameter<Integer> Rubble_Block = EntityDataManager.<Integer>createKey(EntityBlackHole.class, DataSerializers.VARINT);
    public static DataParameter<Integer> Rubble_Meta = EntityDataManager.<Integer>createKey(EntityBlackHole.class, DataSerializers.VARINT);


    public EntityRubble(World world)
    {
        super(world);
    }

    @Override
    public void entityInit() {
        this.dataManager.register(Rubble_Block, 1);
        this.dataManager.register(Rubble_Meta, 0);
    }

    public EntityRubble(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            byte b0 = 15;

            result.entityHit.attackEntityFrom(rubble, b0);
        }

        if(this.ticksExisted > 10) {
            if(this.ticksExisted > 30) {
                this.setDead();
            }

//            world.playSoundAtEntity(this, "hbm:block.debris", 1.5F, 1.0F);
            //worldObj.playAuxSFX(2001, (int)posX, (int)posY, (int)posZ, this.dataWatcher.getWatchableObjectInt(16) + (this.dataWatcher.getWatchableObjectInt(17) << 12));

            if(!world.isRemote)
                PacketHandler.INSTANCE.sendToAllAround(new MessageParticleBurst((int)Math.floor(posX), (int)posY, (int)Math.floor(posZ), this.dataManager.get(Rubble_Block), this.dataManager.get(Rubble_Meta)), new NetworkRegistry.TargetPoint(world.provider.getDimension(), posX, posY, posZ, 50));
        }
    }

    @Override
    public int getAir() {
        return 1;
    }

    public void setMetaBasedOnBlock(Block b, int i) {
        this.dataManager.set(Rubble_Block, Block.getIdFromBlock(b));
        this.dataManager.set(Rubble_Meta, i);

//        this.dataWatcher.updateObject(16, Block.getIdFromBlock(b));
//        this.dataWatcher.updateObject(17, i);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.dataManager.set(Rubble_Block, nbt.getInteger("block"));
        this.dataManager.set(Rubble_Meta, nbt.getInteger("meta"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("block", this.dataManager.get(Rubble_Block));
        nbt.setInteger("meta", this.dataManager.get(Rubble_Meta));
    }


}
