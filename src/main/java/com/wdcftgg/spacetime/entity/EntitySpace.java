package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAILookIdle;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.network.MessageSpaceAnimation;
import com.wdcftgg.spacetime.network.MessageTimeParticle;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import software.bernie.example.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/3 14:13
 */
public class EntitySpace extends EntityMob implements IAnimatable {

    public AnimationFactory factory = new AnimationFactory(this);

    private final BossInfoServer bossInfo;

    private static String mode = "speak";
    private static int phases = 0;
    private static int attackTick = 100;
    private static int spawnTick = -1;
    public static double animationTick = -1;
    public static int attackmode = -1;


    public final BlockPos[] blockPosList1 = new  BlockPos[]{
            new BlockPos(77, 86, 1),
            new BlockPos(60, 86, -16),
            new BlockPos(43, 86, 1),
            new BlockPos(60, 86, 18),
    };

    public EntitySpace(World worldIn)
    {
        super(worldIn);
        this.bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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
            if (spawnTick == -1) {
                spawnTick = (int) world.getTotalWorldTime();
            }
            laterspeak("spacetime.space.say.1", world.getTotalWorldTime(), 60);
            laterspeak("spacetime.space.say.2", world.getTotalWorldTime(), 100);
            laterspeak("spacetime.space.say.3", world.getTotalWorldTime(), 160);
            laterspeak("spacetime.space.say.4", world.getTotalWorldTime(), 240);

            if (world.getTotalWorldTime() == (spawnTick + 240)) {
                mode = "default";
                attackmode = (int) world.getTotalWorldTime();
            }

            int pathhealth = (int) (this.getMaxHealth() / 4);

            if (pathhealth >= (int) this.getHealth()){
                phases = 1;
            } else if (pathhealth * 2 >= (int) this.getHealth()) {
                phases = 2;
            } else if (pathhealth * 3 >= (int) this.getHealth()) {
                phases = 3;
            } else{
                phases = 0;
            }

            //0阶段

            if (phases == 0) {
                this.motionY = 0;
            }

        }




    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (mode == "speak") return false;


        if (phases == 0) {
            Random random = new Random();
            while (true) {
                int num = random.nextInt(4);
                if (blockPosList1[num] != Tools.getintpos(this.getPosition())) {
                    this.attemptTeleport((blockPosList1[num].getX() + 0.5), (blockPosList1[num].getY() + 0.5), (blockPosList1[num].getZ() + 0.5));
                    break;
                }
            }
        }



        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        SpaceAIAttack.attacktime = -1;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setString("mode", mode);
        compound.setInteger("phases", phases);
        compound.setInteger("attackTick", attackTick);
        compound.setInteger("spawnTick", spawnTick);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getString("mode");
        phases = compound.getInteger("phases");
        attackTick = compound.getInteger("attackTick");
        spawnTick = compound.getInteger("spawnTick");
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new SpaceAIAttack(this));
        this.tasks.addTask(2, new SpaceAILookIdle(this));
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(mode == "speak" || mode == "default") {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.speak", true));
        }
        if(mode == "attack_0_0") {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.turbulence", true));
        }
        if (event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.speak", true));
        }
        animationTick = event.getAnimationTick();
        PacketHandler.INSTANCE.sendToAllAround(new MessageSpaceAnimation(animationTick), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), (double)this.getPosition().getX(), (double)this.getPosition().getY(), (double)this.getPosition().getZ(), 256.0D));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
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

    public int getPhases(){
        return phases;
    }

    public int getAttackTick() {
        return attackTick;
    }

    public void setAttackTick(int attackTick) {
        EntitySpace.attackTick = attackTick;
    }

    public void laterspeak(String str, long time, long latertime) {
        if (time == (spawnTick + latertime)) {
            EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 200, false);
            if (player != null) {
                player.sendMessage(new TextComponentTranslation(str));
            }
        }
    }

    public double getAnimationTick() {
        return animationTick;
    }
}
