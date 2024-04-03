package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.entity.ai.space.SpaceAIAttack;
import com.wdcftgg.spacetime.entity.ai.time.TimeAIHurtByTarget;
import com.wdcftgg.spacetime.network.MessageSpaceCollideWithPlayer;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
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
                    List<String> poslist = Arrays.asList(sprinting.split("/"));
                    if (poslist.size() > 1) {
                        List<String> posnum = Arrays.asList(poslist.get(1).split(","));
                        if (posnum.size() > 2) {
                            if ((Integer.valueOf(posnum.get(0)) == (int) this.posX) && (Integer.valueOf(posnum.get(2)) == (int) this.posZ)) {
                                sprinting = "";
                                SpaceAIAttack.attacktime = 30;
                                System.out.println("sad");
                            }
                        }
                    }
                }
                if (world.getEntitiesWithinAABB(EntitySpace.class, new AxisAlignedBB(new BlockPos(76, 150, -16), new BlockPos(44, 153, 16))).isEmpty()) {
                    if (world.getTotalWorldTime() % 10 == 0)
                        sprinting = "";
                }
                for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east(2).north(2).down(), this.getPosition().west(2).south(2).up()))) {
                    if (livingbase instanceof EntitySpace) continue;
                    livingbase.attackEntityFrom(DamageSource.GENERIC, 3);
                }
            }

            if (!Tools.getSpaceChallengefieldPlayer(world).isEmpty()) {
                this.setAttackTarget(Tools.getSpaceChallengefieldPlayer(world).get(0));
                this.getLookHelper().setLookPositionWithEntity(Tools.getSpaceChallengefieldPlayer(world).get(0), 30.0F, 30.0F);
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

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
//        if (entityIn instanceof EntityLivingBase) {

//        }
        System.out.println("EntityCollision");
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if (phases == 2) {
            PacketHandler.INSTANCE.sendToServer(new MessageSpaceCollideWithPlayer(5, player));
        }
    }

    @Override
    public void move(MoverType type, double x, double y, double z)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;

        // 移动生物
        super.move(type, x, y, z);

        // 检查生物是否与方块相交，如果相交则重新设置位置
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        this.posX = (d0 + this.posX) / 2.0D;
        this.posY = (d1 + this.posY) / 2.0D;
        this.posZ = (d2 + this.posZ) / 2.0D;
        this.setEntityBoundingBox(axisalignedbb);
        this.resetPositionToBB();
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

    public void setAttackTick(int num) {
        EntitySpace.attackTick = num;
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

    public void setSprinting(String sss) {
        EntitySpace.sprinting = sss;
    }
}
