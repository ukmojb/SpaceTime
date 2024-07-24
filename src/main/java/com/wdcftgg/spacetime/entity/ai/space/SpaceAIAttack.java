package com.wdcftgg.spacetime.entity.ai.space;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        if (entitySpace.getMode() == "speak") return;

        this.attackTick = Math.max(entitySpace.getAttackTick() - 1, 0);
        if (entitySpace.getAttackTick() <= 0) {
            if (phases == 0) {
                entitySpace.setMode("turbulence");
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
                    for (EntityPlayer player : SpaceWorldProvider.getPlayerList()) {
                        if (!player.isCreative())
                            player.addPotionEffect(new PotionEffect(ModPotions.LossSpatialSense, 99999, 0, true, false));
                    }

                }
            } else if (phases == 2) {
                if (entitySpace.getMode() != "sprinting" && entitySpace.getMode() != "weakness") {
                    entitySpace.setMode("sprinting");

                    MinecraftServer server = world.getMinecraftServer();
                    TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
                    Template template = manager.getTemplate(server, new ResourceLocation(SpaceTime.MODID, "challengefield2"));

                    BlockPos pos = new BlockPos(46, 150, -14);
                    template.addBlocksToWorld(world, pos, new PlacementSettings(), 2|4|16);
                    SpaceTime.Log("challengefield2 saved");

                    Tools.setBlockAABB(new BlockPos(74, 79, 14), new BlockPos(45, 82, -14), Blocks.AIR, entitySpace);

                    for (EntityPlayer player : SpaceWorldProvider.getPlayerList()) {
                        EntityPlayerMP playermp = (EntityPlayerMP) player;
                        playermp.connection.setPlayerLocation(60.5, 151, 0.5, playermp.rotationYaw, playermp.rotationPitch);
                        player.removeActivePotionEffect(ModPotions.LossSpatialSense);
                    }

                    world.addWeatherEffect(new EntityLightningBolt(world, 46, 152, -14, false));
                    world.addWeatherEffect(new EntityLightningBolt(world, 46, 152, 14, false));
                    world.addWeatherEffect(new EntityLightningBolt(world, 74, 152, -14, false));
                    world.addWeatherEffect(new EntityLightningBolt(world, 74, 152, -14, false));

                    Tools.setPosition(entitySpace, new BlockPos(entitySpace.posX, 151, entitySpace.posZ));

                }

                CollidedTimePillarInAABB(entitySpace, entitySpace.getEntityBoundingBox());

                if (target != null && entitySpace.getMode() == "sprinting") {
                    setSprintingPos(entitySpace, target);
                }
            } else {
                if (entitySpace.getMode() == "wait") {
                    entitySpace.setPosition(60, 200, 0);
                }
            }
        }
    }

    private void CollidedTimePillarInAABB(EntitySpace entitySpace, AxisAlignedBB AABB) {
        int minX = MathHelper.floor(AABB.minX);
        int minY = MathHelper.floor(AABB.minY);
        int minZ = MathHelper.floor(AABB.minZ);
        int maxX = MathHelper.floor(AABB.maxX);
        int maxY = MathHelper.floor(AABB.maxY);
        int maxZ = MathHelper.floor(AABB.maxZ);

        int pillarNum = 0;

        for (int k1 = minX; k1 <= maxX; ++k1) {
            for (int l1 = minY; l1 <= maxY; ++l1) {
                for (int i2 = minZ; i2 <= maxZ; ++i2) {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();
                    if (block == STBlocks.TIMEPILLAR) pillarNum++;
                }
            }
        }

        if (pillarNum >= 2) {
            entitySpace.setMode("weakness");
        }
    }

    private void setSprintingPos(EntitySpace entitySpace, EntityLivingBase target) {
        if (!entitySpace.world.isRemote && target != null) {
            int spaceposx = (int) entitySpace.posX;
            int spaceposz = (int) entitySpace.posZ;
            int targetposx = (int) target.posX;
            int targetposz = (int) target.posZ;

            int newposx;
            int newposz;

            //x = 74,46
            //z = 14,-14

            //过两点的方程式
            if (entitySpace.getAdjustedHorizontalFacing() == EnumFacing.NORTH || entitySpace.getAdjustedHorizontalFacing() == EnumFacing.SOUTH) {
                if (entitySpace.getDistanceSq(new BlockPos(entitySpace.posX, entitySpace.posY, 14)) > entitySpace.getDistanceSq(new BlockPos(entitySpace.posX, entitySpace.posY, -14))) {
                    if (targetposz != spaceposz) {
                        newposx = ((15 - spaceposz) / (targetposz - spaceposz)) * (targetposx - spaceposx) + spaceposx;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (newposx + "," + (int) entitySpace.posY + "," + 15 + "/" + 1) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + ((int) entitySpace.posX + "," + (int) entitySpace.posY + "," + 15 + "/" + 1) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                } else {
                    if (targetposz != spaceposz) {
                        newposx = ((-15 - spaceposz) / (targetposz - spaceposz)) * (targetposx - spaceposx) + spaceposx;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (newposx + "," + (int) entitySpace.posY + "," + -15 + "/" + 1) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + ((int) entitySpace.posX + "," + (int) entitySpace.posY + "," + -15 + "/" + 1) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                }
            } else {
                if (entitySpace.getDistanceSq(new BlockPos(74, entitySpace.posY, entitySpace.posZ)) > entitySpace.getDistanceSq(new BlockPos(46, entitySpace.posY, entitySpace.posZ))) {
//                    newposz = ((75 - spaceposx) / (targetposx - spaceposx)) + spaceposx;
                    if (targetposx != spaceposx) {
                        newposz = ((75 - spaceposx) / (targetposx - spaceposx)) * (targetposz - spaceposz) + spaceposz;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (75 + "," + (int) entitySpace.posY + "," + newposz + "/" + 2) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (75 + "," + (int) entitySpace.posY + "," + (int) entitySpace.posZ + "/" + 2) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                } else  {
                    if (targetposx != spaceposx) {
                        newposz = ((45 - spaceposx) / (targetposx - spaceposx)) * (targetposz - spaceposz) + spaceposz;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (45 + "," + (int) entitySpace.posY + "," + newposz + "/" + 2) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (45 + "," + (int) entitySpace.posY + "," + (int) entitySpace.posZ + "/" + 2) + "/" + (entitySpace.rotationPitch + "," + entitySpace.rotationYaw);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                }
            }
        }
    }

    private void entMove(EntitySpace entitySpace) {

        float speed = 80;

        if (entitySpace.getSprinting().split("/").length > 1){
            List<String> stringList0 = Arrays.asList(entitySpace.getSprinting().split("/")[0].split(","));
            List<String> stringList = Arrays.asList(entitySpace.getSprinting().split("/")[1].split(","));
            List<String> stringList3 = Arrays.asList(entitySpace.getSprinting().split("/")[3].split(","));
            List<String> stringList2 = Arrays.asList(entitySpace.getSprinting().split("/")[2]);
            int targetX = Integer.valueOf(stringList.get(0));
            int targetY = Integer.valueOf(stringList.get(1));
            int targetZ = Integer.valueOf(stringList.get(2));
            int oldposX = Integer.valueOf(stringList0.get(0));
            int oldposZ = Integer.valueOf(stringList0.get(2));
            int num = Integer.valueOf(stringList2.get(0));
            int oldposY = Integer.valueOf(stringList0.get(1));
            float rotationPitch = Float.valueOf(stringList3.get(0));
            float rotationYaw = Float.valueOf(stringList3.get(1));
            double posX = entitySpace.posX;
            double posY = entitySpace.posY;
            double posZ = entitySpace.posZ;

            if (targetX == oldposX) {
                entitySpace.setPosition(posX, posY, (targetZ > oldposZ ? posZ + 0.1 * speed : posZ - 0.1 * speed));
            } else if (targetZ == oldposZ) {
                entitySpace.setPosition((targetX > oldposX ? posX + 0.1 * speed : posX - 0.1 * speed), posY, posZ);
            } else {
                setAndfindPos(entitySpace, targetX, targetY, targetZ, posX, posY, posZ, oldposX, oldposY, oldposZ, speed, num);
            }
            entitySpace.rotationYaw = rotationYaw;
            entitySpace.rotationPitch = rotationPitch;
        }
    }

    private void setAndfindPos(EntitySpace entitySpace, int targetX, int targetY, int targetZ, double posX, double posY, double posZ, int oldposX, int oldposY, int oldposZ, float speed, int num) {

        if (num == 2) {
            double x = targetX > oldposX ? posX + 0.1 * speed : posX - 0.1 * speed;
            double newZ = (((x - targetX) / (posX - targetX)) * (posZ - targetZ)) + targetZ;

            entitySpace.setPosition(x, (double) posY, newZ);
        } else if (num == 1) {
            double z = targetZ > oldposZ ? posZ + 0.1 * speed : posZ - 0.1 * speed;
            double newX = (((z - targetZ) / (posZ - targetZ)) * (posX - targetX)) + targetX;

            entitySpace.setPosition(newX, (double) posY, z);
        }
    }
}
