package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.ai.space.Space2AIAttack;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.network.*;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.*;

public class EntitySpace2 extends EntityMob implements IAnimatable {

    public AnimationFactory factory = new AnimationFactory(this);

    private final BossInfoServer bossInfo;

    private static int attackTick = 0;
    private static boolean attractPlayer = false;

    private static String mode = "default";

    //正常重力为0.03F
    private double gravityVelocity = 0.0D;
    private int spearhitnum = 0;
    private int invinciblenum = 0;
    private int invincibletime = 10 * 20;

    private NBTTagList projectileNBTList = new NBTTagList();

    public EntitySpace2(World worldIn)
    {
        super(worldIn);
        this.bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
        this.setNoGravity(true);
        this.isImmuneToFire = true;
        this.setSize(1F, 1.8F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10D);
    }

    @Override
    public void setCustomNameTag(String p_setCustomNameTag_1_) {
        super.setCustomNameTag(p_setCustomNameTag_1_);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!world.isRemote) {
            if (!ServerProxy.space2list.contains(this.getEntityId()) && world.getTotalWorldTime() % 20 == 0) {
                ServerProxy.space2list.add(this.getEntityId());
            }

            if (this.getProjectileNBTList().tagCount() >= 10 && this.getMode().equals("default")) {
                this.setMode("counterattack");
            }

            updateTarget();
            updateLook();

            if (spearhitnum >= 20) {
                spearhitnum = 0;
                invinciblenum += 1;
                invincibletime = 10 * 20;
                this.bossInfo.setColor(BossInfo.Color.YELLOW);
            }

            if (invinciblenum >= 2) {
                this.setMode("attack3");
            }

            if (invincibletime > 0) {
                invincibletime -= 1;
            } else {
                this.bossInfo.setColor(BossInfo.Color.PURPLE);
            }

            if (isAttractPlayer()) {
                List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(
                        posX - 100, posY - 100, posZ - 100, posX + 100, posY + 100, posZ + 100));

                for (Entity e : entities) {

                    Vec3d vec = new Vec3d(posX - e.posX, posY - e.posY, posZ - e.posZ).normalize();

                    double speed = 0.4;
                    e.motionX += vec.x * speed;
                    if (Math.abs(this.posY - e.posY) > 100) {
                        e.motionY += vec.y * speed;
                    }
                    e.motionZ += vec.z * speed;
                }
            }
        } else {
            List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPosition().down(5).east(5).north(5), this.getPosition().up(5).west(5).north(5)));
            for (Entity entity : entityList) {
                if (entity instanceof EntitySpearsubspace) continue;
                if (entity instanceof IProjectile) {
                    PacketHandler.INSTANCE.sendToServer(new MessageSpaceGetProjectile(entity.getEntityId()));
                }
            }
        }
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getTrueSource() != null) {
            this.setAttackTarget((EntityLivingBase) source.getTrueSource());
        }
        if (source == DamageSource.FALL) return false;
        if (invincibletime > 0) return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        SpaceAIAttack.attacktime = -1;
        ServerProxy.spacelist.remove((Integer) this.getEntityId());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setString("mode", mode);
        compound.setTag("projectileNBTList", projectileNBTList);
        compound.setDouble("gravityVelocity", gravityVelocity);
        compound.setInteger("spearhitnum", spearhitnum);
        compound.setInteger("invinciblenum", invinciblenum);
        compound.setInteger("invincibletime", invincibletime);
        compound.setBoolean("attractPlayer", attractPlayer);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getString("mode");
        projectileNBTList = (NBTTagList) compound.getTag("projectileNBTList");
        gravityVelocity = compound.getDouble("gravityVelocity");
        spearhitnum = compound.getInteger("spearhitnum");
        invinciblenum = compound.getInteger("invinciblenum");
        invincibletime = compound.getInteger("invincibletime");
        invincibletime = compound.getInteger("invincibletime");
        attractPlayer = compound.getBoolean("attractPlayer");
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new Space2AIAttack(this));
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public boolean canBePushed() {
        return false;
    }


    @Override
    public void applyEntityCollision(Entity entityIn) {
//        if (entityIn instanceof EntityLivingBase) {

//        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (mode.equals("default") || !(event.getController().getAnimationState() == AnimationState.Running)) {
            PacketHandler.INSTANCE.sendToServer(new MessageSyncMode("default"));
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.default", false));
            return PlayState.CONTINUE;
        }
        if (mode.equals("summon")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.summon", false));
            return PlayState.CONTINUE;
        }
        if (mode.equals("attack1")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.attack1", false));
            return PlayState.CONTINUE;
        }
        if (mode.equals("attack2")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.attack2", false));
            return PlayState.CONTINUE;
        }
        if (mode.equals("walk")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.walk", false));
            return PlayState.CONTINUE;
        }
        if (mode.equals("counterattack")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.counterattack", false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0, this::predicate);
        controller.registerCustomInstructionListener(this::CommandListener);
        controller.registerParticleListener(this::ParticleListener);
        controller.registerSoundListener(this::SoundListener);
        data.addAnimationController(controller);
    }

    private <T extends IAnimatable> void CommandListener(CustomInstructionKeyframeEvent<T> event)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageCustomInstructionKey(event.getController().getCurrentAnimation().animationName, event.instructions.replace(";", "")));
    }

    private <T extends IAnimatable> void ParticleListener(ParticleKeyFrameEvent<T> event)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageParticleKey(event.getController().getCurrentAnimation().animationName, event.effect));
    }

    private <T extends IAnimatable> void SoundListener(SoundKeyframeEvent<T> event)
    {

        PacketHandler.INSTANCE.sendToServer(new MessageSoundKey(event.getController().getCurrentAnimation().animationName, event.sound));
    }

    public void CommandListener(String animationName, String instructions) {
        if (animationName.equals("animation.space.summon")) {
            if (instructions.equals("0")) {
                for (int i = 1; i < 4; i++) {
                    EntityPortal portal = new EntityPortal(world, 1);
                    EntityPortal portal1 = new EntityPortal(world, 1);
                    Tools.setPosition(portal, Tools.getLeftPosition(this, 4 * i).up(4));
                    Tools.setPosition(portal1, Tools.getRightPosition(this, 4 * i).up(4));
                    world.spawnEntity(portal);
                    world.spawnEntity(portal1);
                }
            }
            if (instructions.equals("1")) {
                for (int i = 1; i < 5; i++) {
                    EntityPortal portal = new EntityPortal(world, 1);
                    EntityPortal portal1 = new EntityPortal(world, 1);
                    Tools.setPosition(portal, Tools.getLeftPosition(this, 5 * i).up(8));
                    Tools.setPosition(portal1, Tools.getRightPosition(this, 5 * i).up(8));
                    world.spawnEntity(portal);
                    world.spawnEntity(portal1);
                }
            }
            if (instructions.equals("2")) {
                for (int i = 1; i < 4; i++) {
                    EntityPortal portal = new EntityPortal(world, 1);
                    EntityPortal portal1 = new EntityPortal(world, 1);
                    Tools.setPosition(portal, Tools.getLeftPosition(this, 4 * i).up(12));
                    Tools.setPosition(portal1, Tools.getRightPosition(this, 4 * i).up(12));
                    world.spawnEntity(portal);
                    world.spawnEntity(portal1);
                }
            }
        }
        if (animationName.equals("animation.space.attack1")) {
            if (instructions.equals("0")) {
                BlockPos left = Tools.getLeftPosition(this, 1F);
                BlockPos right = Tools.getRightPosition(this, 1F);
                EntitySpearsubspace spearsubspace = new EntitySpearsubspace(world, left.getX(), left.getY() + 0.5, left.getZ(), 0);
                EntitySpearsubspace spearsubspace1 = new EntitySpearsubspace(world, right.getX(), right.getY() + 0.5, right.getZ(), 0);
                spearsubspace.shoot(this, this.prevRotationPitch, this.getRotationYawHead(), 0.0F, 1.5F, 1.0F);
                spearsubspace1.shoot(this, this.prevRotationPitch, this.getRotationYawHead(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(spearsubspace);
                world.spawnEntity(spearsubspace1);
            }
        }
        if (animationName.equals("animation.space.walk")) {
            if (instructions.equals("0")) {
                if (this.getAttackTarget() != null) {
                    EntityLivingBase entityLivingBase = this.getAttackTarget();
                    Random random = new Random();
                    double ex = entityLivingBase.posX;
                    double ey = entityLivingBase.posY;
                    double ez = entityLivingBase.posZ;
                    Vec3d vec3d = new Vec3d(ex - this.posX, ey - this.posY, ez - this.posZ).normalize().scale(6 + random.nextInt(10));

                    this.attemptTeleport(this.posX + vec3d.x, this.posY + vec3d.y, this.posZ + vec3d.z);
                }
            }
        }
        if (animationName.equals("animation.space.counterattack")) {
            if (instructions.equals("0")) {
                int leftnum = 0;
                int rightnum = 0;
                int upnum = 0;
                for (NBTBase nbtTagCompound : this.getProjectileNBTList()) {
                    NBTTagCompound nbt = (NBTTagCompound) nbtTagCompound;
                    Random random = new Random();
                    boolean leftorright = random.nextBoolean();
                    int offset = leftorright ? leftnum++ : rightnum++;
                    EntityPortal portal = new EntityPortal(world, 3, nbt);
                    Tools.setPosition(portal, leftorright ? Tools.getLeftPosition(this, 5 * offset).up(5 * (upnum++)) : Tools.getRightPosition(this, 5 * offset).up(5 * (upnum++)));
                    world.spawnEntity(portal);
                }
            }
        }
        if (animationName.equals("animation.space.attack2")) {
            if (instructions.equals("0")) {
                if (this.getAttackTarget() != null) {
                    EntityLivingBase livingBase = this.getAttackTarget();
                    Tools.setPosition(this, livingBase.getPosition().up(12));
                    this.setNoGravity(false);
                }
            }
            if (instructions.equals("1")) {
                this.setNoGravity(true);
                world.createExplosion(this, this.posX, this.posY, this.posZ, 3, true);

                EntitySpearsubspace1 spearsubspace1 = new EntitySpearsubspace1(world);
                Tools.setPosition(spearsubspace1, this.getPosition());
                world.spawnEntity(spearsubspace1);
            }
        }
        if (animationName.equals("animation.space.attack3")) {
            if (instructions.equals("0")) {
                //举起双手
            } else if (instructions.equals("6")) {
                List<Entity> list = world.getLoadedEntityList();
                for (Entity entity : list) {
                    if (entity instanceof EntityPortal) {
                        EntityPortal portal = (EntityPortal) entity;
                        portal.setCanAttack(true);
                    }
                }
            } else if (instructions.equals("stopped")) {
                List<Entity> list = world.getLoadedEntityList();
                for (Entity entity : list) {
                    if (entity instanceof EntityPortal) {
                        EntityPortal portal = (EntityPortal) entity;
                        portal.setCanAttack(false);
                    }
                }
            } else {
                attack3();
            }
        }
        if (instructions.equals("stopped")) {
            this.setMode("default");
        }
    }
    public void ParticleListener(String animationName, String instructions) {
        if (animationName.contains("animation.space.summon")) {
            if (instructions.contains("0")) {
                List<Entity> list = world.getLoadedEntityList();
                for (Entity entity : list) {
                    if (entity instanceof EntityPortal) {
                        EntityPortal portal = (EntityPortal) entity;
                        portal.setCanSummon(true);
                    }
                }
            }
        }
    }
    public void SoundListener(String animationName, String instructions) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public String getMode(){
        return mode;
    }

    public void setMode(String str){
        mode = str;
    }

    public int getAttackTick() {
        return attackTick;
    }

    public void setAttackTick(int attackTick) {
        EntitySpace2.attackTick = attackTick;
    }

    public double getGravityVelocity() {
        return gravityVelocity;
    }

    public void setGravityVelocity(double gravityVelocity) {
        this.gravityVelocity = gravityVelocity;
    }

    private void laterspeak(String str, long oldtime, long time, long latertime) {
        if (time == (oldtime + latertime)) {
            EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 200, false);
            if (player != null) {
                player.sendMessage(new TextComponentTranslation(str));
            }
        }
    }

    public void addProjectileNBTList(NBTTagCompound nbt) {
        projectileNBTList.appendTag(nbt);
    }

    public NBTTagList getProjectileNBTList() {
        return projectileNBTList;
    }

    public void clearProjectileNBTList() {
        this.projectileNBTList = new NBTTagList();
    }

    public boolean isAttractPlayer() {
        return attractPlayer;
    }

    public void setAttractPlayer(boolean attractPlayer) {
        this.attractPlayer = attractPlayer;
    }

    private void updateTarget() {
        EntityPlayer player = Tools.getClosestPlayer(world, this.posX, this.posY, this.posZ, 500);
        if (player != null) {
            this.setAttackTarget(player);
        }
    }

    private void updateLook() {
        Entity entity = this.getAttackTarget();
        if (entity != null) {
            this.getLookHelper().setLookPositionWithEntity(entity, 360, 360);
        }
    }

    private void attack3() {
        Random random = new Random();
        for (int i = 1; i < 3 + random.nextInt(); i++) {
            double x = random.nextInt(7) + random.nextDouble() + 3;
            double y = random.nextInt(7) + random.nextDouble() + 3;
            double z = random.nextInt(7) + random.nextDouble() + 3;

            EntityPortal portal = new EntityPortal(world, 4);
            portal.setPosition(x, y, z);
            world.spawnEntity(portal);
        }
    }

}

