package com.wdcftgg.spacetime.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EntityBlackHole extends EntityLiving {

    Random rand = new Random();
    public static DamageSource blackhole_damagesource = new DamageSource("blockhole").setDamageIsAbsolute().setDamageBypassesArmor();

    public static DataParameter<Float> BlackHole_Size = EntityDataManager.<Float>createKey(EntityBlackHole.class, DataSerializers.FLOAT);


    public EntityBlackHole(World worldIn)
    {
        super(worldIn);
        this.setSize(1, 1);
        this.setNoAI(true);
        this.setNoGravity(true);
    }

    public EntityBlackHole(World world, float size) {
        this(world);
        this.dataManager.set(BlackHole_Size, size);
//        this.dataWatcher.updateObject(16, size);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        float size;

        try {
            size = this.getDataManager().get(EntityBlackHole.BlackHole_Size);
        } catch (Throwable var12)
        {
            size = 0.5f;
        }

        if(!world.isRemote) {
            for(int k = 0; k < size * 2; k++) {
                double phi = rand.nextDouble() * (Math.PI * 2);
                double costheta = rand.nextDouble() * 2 - 1;
                double theta = Math.acos(costheta);
                double x = Math.sin( theta) * Math.cos( phi );
                double y = Math.sin( theta) * Math.sin( phi );
                double z = Math.cos( theta );

                Vec3d vec = new Vec3d(x, y, z);
//                Vec3d vec = Vec3d.createVectorHelper(x, y, z);
                int length = (int)Math.ceil(size * 15);

                for(int i = 0; i < length; i ++) {
                    int x0 = (int)(this.posX + (vec.x * i));
                    int y0 = (int)(this.posY + (vec.y * i));
                    int z0 = (int)(this.posZ + (vec.z * i));

                    if(world.getBlockState(new BlockPos(x0, y0, z0)).getMaterial().isLiquid()) {
                        world.setBlockToAir(new BlockPos(x0, y0, z0));
                    }

                    if(world.getBlockState(new BlockPos(x0, y0, z0)).getBlock() != Blocks.AIR) {
                        EntityRubble rubble = new EntityRubble(world);
                        rubble.posX = x0 + 0.5F;
                        rubble.posY = y0;
                        rubble.posZ = z0 + 0.5F;
                        rubble.setMetaBasedOnBlock(world.getBlockState(new BlockPos(x0, y0, z0)).getBlock(), world.getBlockState(new BlockPos(x0, y0, z0)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(x0, y0, z0))));

                        world.spawnEntity(rubble);

                        world.setBlockToAir(new BlockPos(x0, y0, z0));
                        break;
                    }
                }
            }
        }

        double range = size * 15;

        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(
                posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));

        for(Entity e : entities) {

            if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
                continue;

            if(e instanceof EntityFallingBlock && !world.isRemote && e.ticksExisted > 1) {

                double x = e.posX;
                double y = e.posY;
                double z = e.posZ;
                IBlockState b = ((EntityFallingBlock)e).getBlock();
                int meta = ((EntityFallingBlock)e).getBlock().getBlock().getMetaFromState(((EntityFallingBlock)e).getBlock());

                e.setDead();

                EntityRubble rubble = new EntityRubble(world);
                rubble.setMetaBasedOnBlock(b.getBlock(), meta);
                rubble.setPositionAndRotation(x, y, z, 0, 0);
                rubble.motionX = e.motionX;
                rubble.motionY = e.motionY;
                rubble.motionZ = e.motionZ;
                world.spawnEntity(rubble);
            }

            Vec3d vec = new Vec3d(posX - e.posX, posY - e.posY, posZ - e.posZ);

            double dist = vec.length();

            if(dist > range)
                continue;

            vec = vec.normalize();

            if(!(e instanceof EntityItem))
                vec.rotateYaw((float)Math.toRadians(15));

            double speed = 0.1D;
            e.motionX += vec.x * speed;
            e.motionY += vec.y * speed * 2;
            e.motionZ += vec.z * speed;

            if(e instanceof EntityBlackHole)
                continue;

            if(dist < size * 1.5) {
                e.attackEntityFrom(blackhole_damagesource, 1000);

                if(!(e instanceof EntityLivingBase))
                    e.setDead();
            }
        }

        this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        this.motionX *= 0.99D;
        this.motionY *= 0.99D;
        this.motionZ *= 0.99D;
        this.setPosition(this.posX, 160, this.posZ);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {

        this.setSize(this.dataManager.get(BlackHole_Size));
        return livingdata;
    }

    public void setSize(float size)
    {
        this.dataManager.set(BlackHole_Size, size);
    }

    public Float getSize()
    {
        return this.dataManager.get(BlackHole_Size);
    }


    public void entityInit() {
        super.entityInit();
        this.dataManager.register(BlackHole_Size, 0.5F);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.dataManager.set(BlackHole_Size, nbt.getFloat("size"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("size", this.dataManager.get(BlackHole_Size));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 25000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }
}
