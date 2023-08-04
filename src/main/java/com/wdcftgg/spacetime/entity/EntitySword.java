package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.init.ModSounds;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/24 19:43
 */
public class EntitySword extends EntityLiving {

    private static final DataParameter<Integer> SWORD_TYPE = EntityDataManager.<Integer>createKey(EntitySword.class, DataSerializers.VARINT);
    private int sword = 0;
    private boolean fallsound = false;
    private int swordlength = 0;
    private List<Item> swordList = new ArrayList<Item>();


    public EntitySword(World worldIn)
    {
        super(worldIn);
        this.setSize(0.4F, 5F);
        this.setNoAI(true);
        allSword();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("sword", sword);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        sword = compound.getInteger("sword");
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (this.ticksExisted == 1) {
                this.setSwordType(randomSwordId());
            }
            if (world.getBlockState(this.getPosition()).getBlock() == Blocks.AIR) {
                this.setPosition(this.posX, this.posY * 0.985, this.posZ);
                fallsound = false;
            } else {
                if (!fallsound) {
                    world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.FALLSWORD,SoundCategory.NEUTRAL, 10F, 1f);
                    for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east().north().down(), this.getPosition().west().south().up(4)))) {
                        if (livingbase instanceof EntityMob) continue;
                        livingbase.attackEntityFrom(new DamageSource("Sword"), 7);
                    }
                }
                fallsound = true;
            }
            if (this.ticksExisted >= 100) {
                world.removeEntity(this);
            }
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east().north().down(), this.getPosition().west().south().up(4)))) {
                if (livingbase instanceof EntityMob) continue;
                livingbase.attackEntityFrom(new DamageSource("Sword"), 5);
            }
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {

        this.setSwordType(randomSwordId());

        return livingdata;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    public void setSwordType(int SwordTypeId)
    {
        this.dataManager.set(SWORD_TYPE, Integer.valueOf(SwordTypeId));
    }

    public int getSwordType()
    {
        return ((Integer)this.dataManager.get(SWORD_TYPE)).intValue();
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SWORD_TYPE, Integer.valueOf(0));
    }

    private int randomSwordId(){
        Random r = new Random();
        int rr = r.nextInt(swordlength);
        return Item.getIdFromItem(swordList.get(rr));
    }

    private List<Item> allSword(){
        int i = 0;
        List<Item> swordList1 = new ArrayList<Item>();
        while (i <= 32627) {
            if (Item.REGISTRY.getObjectById(i) != null) {
                if (Item.REGISTRY.getObjectById(i).getTranslationKey().contains("sword")){
                    swordlength += 1;
                    swordList1.add(Item.REGISTRY.getObjectById(i));
                }
            }
            i += 1;
        }
        swordList.addAll(swordList1);
        return swordList1;
    }
}
