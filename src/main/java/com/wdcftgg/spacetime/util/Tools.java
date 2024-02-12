package com.wdcftgg.spacetime.util;

import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
        return new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
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
        if (!entitySpace.world.isRemote && target != null && entitySpace.getSprinting() != "") {
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
                    newposx = ((15 - spaceposz) / (targetposz - spaceposz)) + spaceposz;
                    entitySpace.getNavigator().tryMoveToXYZ(newposx, entitySpace.posY, 15, 10);
                    entitySpace.setSprinting((newposx + "," + entitySpace.posY + "," + 15));
                } else {
                    newposx = ((-15 - spaceposz) / (targetposz - spaceposz)) + spaceposz;
                    entitySpace.getNavigator().tryMoveToXYZ(newposx, entitySpace.posY, -15, 10);
                    entitySpace.setSprinting((newposx + "," + entitySpace.posY + "," + -15));
                }
            } else if (entitySpace.getAdjustedHorizontalFacing() == EnumFacing.EAST || entitySpace.getAdjustedHorizontalFacing() == EnumFacing.WEST){
                if (entitySpace.getDistanceSq(new BlockPos(74, entitySpace.posY, entitySpace.posZ)) > entitySpace.getDistanceSq(new BlockPos(46, entitySpace.posY, entitySpace.posZ))) {
                    newposz = ((75 - spaceposx) / (targetposx - spaceposx)) + spaceposx;
                    entitySpace.getNavigator().tryMoveToXYZ(75, entitySpace.posY, newposz, 10);
                    entitySpace.setSprinting((75 + "," + entitySpace.posY + "," + newposz));
                } else  {
                    newposz = ((45 - spaceposx) / (targetposx - spaceposx)) + spaceposx;
                    entitySpace.getNavigator().tryMoveToXYZ(45, entitySpace.posY, newposz, 10);
                    entitySpace.setSprinting((45 + "," + entitySpace.posY + "," + newposz));
                }
            }



        }
    }


}
