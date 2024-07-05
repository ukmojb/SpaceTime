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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static List<EntitySpaceSword> getSpaceChallengefieldSword(World world) {
        if (!world.isRemote && world.provider.getDimension() == Config.SPACEDDIM) {
            List<EntitySpaceSword> entitySpaceSwordList = world.getEntitiesWithinAABB(EntitySpaceSword.class, new AxisAlignedBB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13)));
            return entitySpaceSwordList;
        }
        return new ArrayList<EntitySpaceSword>();
    }
}
