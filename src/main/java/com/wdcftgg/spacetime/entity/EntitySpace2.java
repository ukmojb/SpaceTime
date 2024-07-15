package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.entity.ai.space.Space2AIAttack;
import com.wdcftgg.spacetime.entity.ai.space.Space2AILookIdle;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.network.*;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
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

import java.util.*;

public class EntitySpace2 extends EntityMob implements IAnimatable {

    public AnimationFactory factory = new AnimationFactory(this);

    private final BossInfoServer bossInfo;

    private static int attackTick = 0;

    private static String mode = "speak";

    private List<AnimationBuilder> animationList = new ArrayList<>();

    public EntitySpace2(World worldIn)
    {
        super(worldIn);
        this.bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
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

        }

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getTrueSource() != null) {
            this.getLookHelper().setLookPositionWithEntity(source.getTrueSource(), 0, 0);
            this.setMode("summon");
        }
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
//        compound.setBoolean("canattack", canattack);
        compound.setString("mode", mode);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
//        canattack = compound.getBoolean("canattack");
        mode = compound.getString("mode");
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new Space2AIAttack(this));
        this.targetTasks.addTask(1, new TimeAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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
        if (mode == "summon") {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.summon", false));
            return PlayState.CONTINUE;
        }
        if (event.getController().getAnimationState() == AnimationState.Stopped || event.getController().getAnimationState() == AnimationState.Transitioning) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.space.default", false));
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
        if (animationName.contains("animation.space.summon")) {
            if (instructions.contains("0")) {
                for (int i = 1; i < 4; i++) {
                    EntityPortal portal = new EntityPortal(world, 1);
                    EntityPortal portal1 = new EntityPortal(world, 1);
                    Tools.setPosition(portal, Tools.getLeftPosition(this, 4 * i).up(4));
                    Tools.setPosition(portal1, Tools.getRightPosition(this, 4 * i).up(4));
                    world.spawnEntity(portal);
                    world.spawnEntity(portal1);
                }
            }
            if (instructions.contains("1")) {
                for (int i = 1; i < 5; i++) {
                    EntityPortal portal = new EntityPortal(world, 1);
                    EntityPortal portal1 = new EntityPortal(world, 1);
                    Tools.setPosition(portal, Tools.getLeftPosition(this, 5 * i).up(8));
                    Tools.setPosition(portal1, Tools.getRightPosition(this, 5 * i).up(8));
                    world.spawnEntity(portal);
                    world.spawnEntity(portal1);
                }
            }
            if (instructions.contains("2")) {
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

    public List<AnimationBuilder> getAnimationList() {
        return animationList;
    }

    public void addAnimation(AnimationBuilder animation) {
        this.animationList.add(animation);
    }

    public void removeAnimation(AnimationBuilder animation) {
        this.animationList.remove(animation);
    }

    private void laterspeak(String str, long oldtime, long time, long latertime) {
        if (time == (oldtime + latertime)) {
            EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 200, false);
            if (player != null) {
                player.sendMessage(new TextComponentTranslation(str));
            }
        }
    }
}

