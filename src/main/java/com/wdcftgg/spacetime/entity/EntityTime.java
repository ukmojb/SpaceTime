package com.wdcftgg.spacetime.entity;


import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.entity.ai.TimeAIAttackMelee;
import com.wdcftgg.spacetime.entity.ai.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.entity.ai.TimeAIMoveTowardsRestriction;
import com.wdcftgg.spacetime.event.EventTimeBack;
import com.wdcftgg.spacetime.network.MessageTimeBack;
import com.wdcftgg.spacetime.network.MessageTimeParticle;
import com.wdcftgg.spacetime.network.PacketHandler;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.Random;
import java.util.UUID;

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
        this.bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
        this.setSize(1F, 1.8F);
    }

    private static final IAttribute LIFE_POWER = new RangedAttribute(null, "spacetime.attribute.life", 6.0, 0.0, 6.0).setShouldWatch(false);

    private String playeruuid;
    private int challengetime = 3600;
    private int timeback;
    private int time = 1;
    private int x;
    private int y;
    private int z;

    private final BossInfoServer bossInfo;

    private float[] timenum = new float[]{
            2.0f,
            6.0f,
            10.0f,
            30.0f,
            50.0f,
            60.0f
    };

    public void setCustomNameTag(String p_setCustomNameTag_1_) {
        super.setCustomNameTag(p_setCustomNameTag_1_);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(config.TIMEMAXHEALTH);
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
            if (this.ticksExisted == 5) {
                Random r = new Random();
                CommandSenderWrapper commandSenderWrapper = CommandSenderWrapper.create(this).withSendCommandFeedback(false);
                this.getServer().commandManager.executeCommand(commandSenderWrapper, "/tickratechanger " + timenum[r.nextInt(6)]);
                EventTimeBack.isBack = true;
            }
            if (this.getEntityAttribute(LIFE_POWER).getBaseValue() != 1.0D && this.getHealth() <= 0){
                Random r = new Random();
                challengetime += 1200;

                this.setHealth(this.getMaxHealth());
                if (r.nextBoolean()) {
                    this.getEntityAttribute(LIFE_POWER).setBaseValue(this.getEntityAttribute(LIFE_POWER).getBaseValue()-1.0D);
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTimeParticle(this.getPosition(), true, false), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
                } else {
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTimeBack(false, false), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
                }

                CommandSenderWrapper commandSenderWrapper = CommandSenderWrapper.create(this).withSendCommandFeedback(false);
                this.getServer().commandManager.executeCommand(commandSenderWrapper, "/tickratechanger " + timenum[r.nextInt(6)]);

            }
            if (this.getEntityAttribute(LIFE_POWER).getBaseValue() == 1.0D && this.getHealth() <= 0){
                CommandSenderWrapper commandSenderWrapper = CommandSenderWrapper.create(this).withSendCommandFeedback(false);
                this.getServer().commandManager.executeCommand(commandSenderWrapper, "/tickratechanger 20");
                PacketHandler.INSTANCE.sendToAllAround(new MessageTimeParticle(this.getPosition(), false, false), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
                PacketHandler.INSTANCE.sendToAllAround(new MessageTimeBack(false, true), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
            }
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east().north().down(2), this.getPosition().west().south().up(2)))) {
                if (!(livingbase instanceof EntityTime) && !(livingbase instanceof EntityTimePhantom)) {
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 3);
                }
            }
            if (this.time != this.challengetime) {
                time += 1;
            } else {
                for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(5).north(5).down(5), this.getPosition().west(5).south(5).up(5)))) {
                    if (!(livingbase instanceof EntityTime) && !(livingbase instanceof EntityTimePhantom)) {
                        world.addWeatherEffect(new EntityLightningBolt(world, livingbase.getPosition().getX(), livingbase.getPosition().getY(), livingbase.getPosition().getZ(), true));
                        livingbase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 100);
                    }
                }
                world.removeEntity(this);
                challengefailure(this.getPosition().east(8).north(8).down(5), this.getPosition().west(8).south(8).up(5), this);
            }
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(10).north(10).down(5), this.getPosition().west(10).south(10).up(5)))) {
                if (livingbase instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) livingbase;
                    if (TimeSandHelper.getTimeSandFromInventory(player.inventory) >= 0) {
                        TimeSandHelper.removeTimeSandFromInventory(player.inventory, 90);
                    } else {
                        world.addWeatherEffect(new EntityLightningBolt(world, livingbase.getPosition().getX(), livingbase.getPosition().getY(), livingbase.getPosition().getZ(), true));
                        livingbase.attackEntityFrom(DamageSource.GENERIC, 50);
                        world.removeEntity(this);
                        playerShowTitle(player);
                    }
                }
            }
            if (playeruuid != null && playeruuid.length() == 36) {
                if (world.getPlayerEntityByUUID(UUID.fromString(playeruuid)) != null) {
                    EntityPlayer player = (EntityPlayer) world.getPlayerEntityByUUID(UUID.fromString(playeruuid));
                    if (player != null) {
                        if (player.isDead) {
                            world.removeEntity(this);
                            playerShowTitle(player);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (!this.world.isRemote) {
            if (source.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) source.getTrueSource();
                playeruuid = player.getUniqueID().toString();
            }
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(3).north(3).down(5), this.getPosition().west(3).south(3).up(5)))) {
                if (!(livingbase instanceof EntityTime)) {
                    livingbase.knockBack(this, 1f, livingbase.getLookVec().x, livingbase.getLookVec().y);
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 5);
                }
            }
        }
        return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        if (playeruuid != null) {
            compound.setString("playeruuid", playeruuid);
        }
        compound.setInteger("challengetime", challengetime);
        compound.setInteger("timeback", timeback);
        compound.setInteger("time", time);
        compound.setInteger("x", x);
        compound.setInteger("y", y);
        compound.setInteger("z", z);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        playeruuid = compound.getString("playeruuid");
        challengetime = compound.getInteger("challengetime");
        timeback = compound.getInteger("timeback");
        time = compound.getInteger("time");
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

    @Override
    public boolean isNonBoss() {
        return false;
    }

    private void challengefailure(BlockPos pos1, BlockPos pos2, Entity ent) {
        for (EntityLivingBase livingbase : ent.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos1, pos2))) {
            playerShowTitle(livingbase);
        }
    }

    private void playerShowTitle(EntityLivingBase living){
        if (living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            if (player.getServer() != null) {
                CommandSenderWrapper commandSenderWrapper = CommandSenderWrapper.create(player).withSendCommandFeedback(false);
                player.getServer().commandManager.executeCommand(commandSenderWrapper, "/title " + player.getName() + " title [{\"text\":\"Challenge failure\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false}]");
            }
        }
    }
}
