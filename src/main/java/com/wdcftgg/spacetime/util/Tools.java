package com.wdcftgg.spacetime.util;

import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/4 23:09
 */
public class Tools {
    public static void setBlockAABB(BlockPos pos1, BlockPos pos2, Block block, EntityLivingBase livingBase) {
        Objects.requireNonNull(livingBase.getServer()).commandManager.executeCommand(livingBase, "/fill " + pos1.getX() + " " + pos1.getY() + " " + pos1.getZ() + " " + pos2.getX() + " " + pos2.getY() + " " + pos2.getZ() + " " + block.getRegistryName());
    }

    public static BlockPos getintpos(BlockPos blockPos) {
        return new BlockPos((int) blockPos.getX(), (int) blockPos.getY(), (int) blockPos.getZ());
    }

    public static void setPosition(EntityLivingBase livingBase, BlockPos blockPos) {
        livingBase.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static List<EntityPlayer> getSpaceChallengefieldPlayer(World world) {
        if (!world.isRemote && world.provider.getDimension() == Config.SPACEDIMID) {
            List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(new BlockPos(73, 80, -13), new BlockPos(47, 180, 13)));
            return players;
        }
        return new ArrayList<EntityPlayer>();
    }

    public static List<EntitySpaceSword> getSpaceChallengefieldSword(World world) {
        if (!world.isRemote && world.provider.getDimension() == Config.SPACEDIMID) {
            List<EntitySpaceSword> entitySpaceSwordList = world.getEntitiesWithinAABB(EntitySpaceSword.class, new AxisAlignedBB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13)));
            return entitySpaceSwordList;
        }
        return new ArrayList<EntitySpaceSword>();
    }

    public static void setSprintingPos(EntitySpace entitySpace, EntityLivingBase target) {
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
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (newposx + "," + (int) entitySpace.posY + "," + 15 + "/" + 1);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + ((int) entitySpace.posX + "," + (int) entitySpace.posY + "," + 15 + "/" + 1);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                } else {
                    if (targetposz != spaceposz) {
                        newposx = ((-15 - spaceposz) / (targetposz - spaceposz)) * (targetposx - spaceposx) + spaceposx;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (newposx + "," + (int) entitySpace.posY + "," + -15 + "/" + 1);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + ((int) entitySpace.posX + "," + (int) entitySpace.posY + "," + -15 + "/" + 1);
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
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (75 + "," + (int) entitySpace.posY + "," + newposz + "/" + 2);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (75 + "," + (int) entitySpace.posY + "," + (int) entitySpace.posZ + "/" + 2);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                } else  {
                    if (targetposx != spaceposx) {
                        newposz = ((45 - spaceposx) / (targetposx - spaceposx)) * (targetposz - spaceposz) + spaceposz;
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (45 + "," + (int) entitySpace.posY + "," + newposz + "/" + 2);
                        } else {
                            entMove(entitySpace);
                        }
                    } else {
                        if (Arrays.asList(entitySpace.getSprinting().split("/")).size() < 2) {
                            EntitySpace.sprinting = (spaceposx + "," + (int) entitySpace.posY + "," + spaceposz) + "/" + (45 + "," + (int) entitySpace.posY + "," + (int) entitySpace.posZ + "/" + 2);
                        } else {
                            entMove(entitySpace);
                        }
                    }
                }
            }
        }
    }

    public static void entMove(EntitySpace entitySpace) {
        // 计算目标位置与生物当前位置之间的距离向量

        float speed = 80;

        if (entitySpace.getSprinting().split("/").length > 1){
            List<String> stringList0 = Arrays.asList(entitySpace.getSprinting().split("/")[0].split(","));
            List<String> stringList = Arrays.asList(entitySpace.getSprinting().split("/")[1].split(","));
            List<String> stringList1 = Arrays.asList(entitySpace.getSprinting().split("/"));
            int targetX = Integer.valueOf(stringList.get(0));
            int targetY = Integer.valueOf(stringList.get(1));
            int targetZ = Integer.valueOf(stringList.get(2));
            int oldposX = Integer.valueOf(stringList0.get(0));
            int oldposY = Integer.valueOf(stringList0.get(1));
            int oldposZ = Integer.valueOf(stringList0.get(2));
            int num = Integer.valueOf(stringList1.get(2));
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
        }
    }

    private static void setAndfindPos(EntitySpace entitySpace, int targetX, int targetY, int targetZ, double posX, double posY, double posZ, int oldposX, int oldposY, int oldposZ, float speed, int num) {

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
