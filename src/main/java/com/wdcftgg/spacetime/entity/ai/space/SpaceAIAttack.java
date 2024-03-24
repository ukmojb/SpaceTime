package com.wdcftgg.spacetime.entity.ai.space;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import com.wdcftgg.spacetime.entity.EntitySword;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import software.bernie.geckolib3.core.AnimationState;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/3 21:59
 */
public class SpaceAIAttack extends EntityAIBase
{
    private World world;
    protected EntitySpace attacker;
    protected int attackTick;

    public static long attacktime = -1;

    public final BlockPos[] blockPosList1 = new  BlockPos[]{
            new BlockPos(47, 80, -13),
            new BlockPos(47, 80, -4),
            new BlockPos(47, 80, 5),
            new BlockPos(56, 80, -13),
            new BlockPos(56, 80, -4),
            new BlockPos(56, 80, 5),
            new BlockPos(65, 80, -13),
            new BlockPos(65, 80, -4),
            new BlockPos(65, 80, 5),

    };
    public final BlockPos[] blockPosList2 = new  BlockPos[]{
            new BlockPos(55, 82, -5),
            new BlockPos(55, 82, 4),
            new BlockPos(55, 82, 13),
            new BlockPos(64, 82, -5),
            new BlockPos(64, 82, 4),
            new BlockPos(64, 82, 13),
            new BlockPos(73, 82, -5),
            new BlockPos(73, 82, 4),
            new BlockPos(73, 82, 13),

    };
    


    public final BlockPos[] swordspownposlist = new  BlockPos[]{
            new BlockPos(69.0, 80.0, 9.0),
            new BlockPos(51.0, 80.0, 9.0),
            new BlockPos(60.0, 80.0, 9.0),
            new BlockPos(69.0, 80.0, 0.0),
            new BlockPos(60.0, 80.0, 0.0),
            new BlockPos(51.0, 80.0, 0.0),
            new BlockPos(51.0, 80.0, -9.0),
            new BlockPos(60.0, 80.0, -9.0),
            new BlockPos(69.0, 80.0, -9.0),

    };

    public SpaceAIAttack(EntitySpace creature)
    {
        this.attacker = creature;
        this.world = creature.world;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        return attacker.getMode() != "speak";
    }

    public boolean shouldContinueExecuting()
    {
        return attackTick > 0;
    }

    public void startExecuting()
    {

    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        attackWithPhases(attacker, entitylivingbase, attacker.getPhases());
        attacker.setAttackTick(this.attackTick);
    }

    protected void attackWithPhases(EntitySpace entitySpace, EntityLivingBase target, int phases)
    {

        if (attacker.getMode() == "speak") return;

        this.attackTick = Math.max(entitySpace.getAttackTick() - 1, 0);
        if (attacktime == -1) {
            attacktime = entitySpace.world.getTotalWorldTime();
        }
        if (entitySpace.getAttackTick() <= 0) {
            if (phases == 0) {
                entitySpace.setMode("attack_0_0");
                Random random = new Random();
                int truenum;
                boolean pass = true;
                if (attacktime == -1) attacktime = entitySpace.world.getTotalWorldTime();


                while (true) {
                    int num = random.nextInt(9);
                    if (entitySpace.world.getBlockState(blockPosList1[num]).getBlock() != STBlocks.SPACETIMETURBULENCE) {
                        truenum = num;
                        break;
                    }
                }
                int num2 = (int) ((entitySpace.world.getTotalWorldTime() - attacktime) % 300);

                if (num2 >= 130 && num2 <= 140) {
                    this.attackTick = 100;
                    Tools.setBlockAABB(blockPosList1[truenum], blockPosList2[truenum], STBlocks.SPACETIMETURBULENCE, entitySpace);
                }

                for (BlockPos pos : blockPosList1) {
                    if (world.getBlockState(pos).getBlock() != STBlocks.SPACETIMETURBULENCE) pass = false;
                }
                if (pass) {
                    Tools.setBlockAABB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13), Blocks.AIR, entitySpace);
                    for (BlockPos pos : blockPosList1) {
                        world.createExplosion(entitySpace, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4, 6, false);
                    }

                    EntityPlayer player = this.world.getClosestPlayer(entitySpace.posX, entitySpace.posY, entitySpace.posZ, 200, false);

                    if (player != null) {
                        world.addWeatherEffect(new EntityLightningBolt(world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), true));
                    }
                }
            } else if (phases == 1) {
                if (entitySpace.getMode() != "magiccircle") {
                    entitySpace.setMode("magiccircle");
                    Tools.setBlockAABB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13), Blocks.AIR, entitySpace);
                    if (Tools.getSpaceChallengefieldSword(world).size() < 5) {
                        int spawnnum = 5;
                        while (spawnnum != 0) {
                            Random random = new Random();
                            int randomint = random.nextInt(9);
                            if (world.getEntitiesWithinAABB(EntitySpaceSword.class, new AxisAlignedBB(swordspownposlist[randomint])).isEmpty()) {
                                EntitySpaceSword spaceSword = new EntitySpaceSword(world);
                                Tools.setPosition(spaceSword, swordspownposlist[randomint]);
                                world.spawnEntity(spaceSword);
                                spawnnum -= 1;
                            }
                        }
                    }
                    for (EntityPlayer player : Tools.getSpaceChallengefieldPlayer(world)) {
                        if (!player.isCreative())
                            player.addPotionEffect(new PotionEffect(ModPotions.LossSpatialSense, 99999, 0, true, false));
                    }

                }
            } else if (phases == 2) {
                if (entitySpace.getMode() != "sprinting") {
                    entitySpace.setMode("sprinting");

                    MinecraftServer server = world.getMinecraftServer();
                    TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
                    Template template = manager.getTemplate(server, new ResourceLocation(SpaceTime.MODID, "challengefield2"));

                    BlockPos pos = new BlockPos(46, 150, -14);
                    template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
                    SpaceTime.Log("challengefield2 saved");

                    Tools.setBlockAABB(new BlockPos(74, 79, 14), new BlockPos(45, 82, -14), Blocks.AIR, entitySpace);

                    for (EntityPlayer player : Tools.getSpaceChallengefieldPlayer(world)) {
                        EntityPlayerMP playermp = (EntityPlayerMP) player;
                        playermp.connection.setPlayerLocation(60.5, 151, 0.5, playermp.rotationYaw, playermp.rotationPitch);
                        player.removeActivePotionEffect(ModPotions.LossSpatialSense);
                    }

                    world.addWeatherEffect(new EntityLightningBolt(world, 46, 152, -14, true));
                    world.addWeatherEffect(new EntityLightningBolt(world, 46, 152, 14, true));
                    world.addWeatherEffect(new EntityLightningBolt(world, 74, 152, -14, true));
                    world.addWeatherEffect(new EntityLightningBolt(world, 74, 152, -14, true));

                    Tools.setPosition(entitySpace, new BlockPos(entitySpace.posX, 151, entitySpace.posZ));
                }
                if (target != null) {
                    Tools.setSprintingPos(entitySpace, target);

                }
            } else {

            }
        }
    }
}
