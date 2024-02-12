package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
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
    private static int phase1Tick = -1;
    public static int attackmode = -1;
    public static String sprinting = "";


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
        this.setNoGravity(true);
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
            laterspeak("spacetime.space.say.1", spawnTick, world.getTotalWorldTime(), 60);
            laterspeak("spacetime.space.say.2", spawnTick, world.getTotalWorldTime(), 100);
            laterspeak("spacetime.space.say.3", spawnTick, world.getTotalWorldTime(), 160);
            laterspeak("spacetime.space.say.4", spawnTick, world.getTotalWorldTime(), 240);

            if (world.getTotalWorldTime() == (spawnTick + 240)) {
                mode = "default";
                attackmode = (int) world.getTotalWorldTime();
            }

            int pathhealth = (int) (this.getMaxHealth() / 4);

            if (pathhealth >= (int) this.getHealth()){
                phases = 3;
            } else if (pathhealth * 2 >= (int) this.getHealth()) {
                phases = 2;
            } else if (pathhealth * 3 >= (int) this.getHealth()) {
                phases = 1;
            } else{
                phases = 0;
            }

            if (phases == 1) {
                if (phase1Tick == -1) {
                    phase1Tick = (int) world.getTotalWorldTime();
                }
                laterspeak("spacetime.space.say.5", phase1Tick, world.getTotalWorldTime(), 20);
                laterspeak("spacetime.space.say.6", phase1Tick, world.getTotalWorldTime(), 80);
                laterspeak("spacetime.space.say.7", phase1Tick, world.getTotalWorldTime(), 120);
                laterspeak("spacetime.space.say.8", phase1Tick, world.getTotalWorldTime(), 160);
            }

            if (phases == 2) {
                if (sprinting != "") {
                    String[] posnum = sprinting.split(",");
                    if (posnum.length != 0) {
                        BlockPos pos0 = new BlockPos(Integer.getInteger(posnum[0]), Integer.getInteger(posnum[1]), Integer.getInteger(posnum[2]));
                        if (pos0.equals(Tools.getintpos(this.getPosition()))) {
                            this.setSprinting("");
                        }
                    }
                }
            }


            if (this.getHealth() <= 0) {
                world.removeEntity(this);
            }
        }




    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (mode == "speak") return false;
        if (source != DamageSource.OUT_OF_WORLD) {
            if (phases == 1) return false;
        }


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
        CommonProxy.spacelist.remove((Integer) this.getEntityId());
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        CommonProxy.spacelist.add(this.getEntityId());
        return livingdata;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setString("mode", mode);
        compound.setInteger("phases", phases);
        compound.setInteger("attackTick", attackTick);
        compound.setInteger("spawnTick", spawnTick);
        compound.setInteger("phase1Tick", phase1Tick);
        compound.setString("sprinting", sprinting);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        mode = compound.getString("mode");
        phases = compound.getInteger("phases");
        attackTick = compound.getInteger("attackTick");
        spawnTick = compound.getInteger("spawnTick");
        phase1Tick = compound.getInteger("phase1Tick");
        sprinting = compound.getString("sprinting");
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new SpaceAIAttack(this));
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
        if(mode == "magiccircle") {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.magiccircle", true));
        }
        if(mode == "sprinting") {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.sprinting", true));
        }
        if (event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.speak", true));
        }
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

    public void laterspeak(String str, long oldtime, long time, long latertime) {
        if (time == (oldtime + latertime)) {
            EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 200, false);
            if (player != null) {
                player.sendMessage(new TextComponentTranslation(str));
            }
        }
    }

    public String getSprinting() {
        return sprinting;
    }

    public void setSprinting(String sprinting) {
        EntitySpace.sprinting = sprinting;
    }
}
