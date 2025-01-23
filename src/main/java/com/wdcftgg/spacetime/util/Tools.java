package com.wdcftgg.spacetime.util;

import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import javax.annotation.Nullable;
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

    public static List<EntitySpaceSword> getSpaceChallengefieldSword(World world) {
        if (!world.isRemote && world.provider.getDimension() == Config.SPACEDDIM) {
            List<EntitySpaceSword> entitySpaceSwordList = world.getEntitiesWithinAABB(EntitySpaceSword.class, new AxisAlignedBB(new BlockPos(73, 80, -13), new BlockPos(47, 82, 13)));
            return entitySpaceSwordList;
        }
        return new ArrayList<EntitySpaceSword>();
    }

    public static BlockPos getRightPosition(Entity entity, float offset) {
        // 获取实体的朝向（Yaw 角度）
        float yaw = entity.rotationYaw;

        // 将角度转换为弧度
        double radians = Math.toRadians(yaw);

        // 计算左边 3 格的偏移量
        double offsetX = Math.cos(radians) * -(offset);
        double offsetZ = Math.sin(radians) * -(offset);

        // 获取实体当前位置
        double currentX = entity.posX;
        double currentY = entity.posY;
        double currentZ = entity.posZ;

        // 计算左边 3 格的坐标
        double leftX = currentX + offsetX;
        double leftY = currentY;
        double leftZ = currentZ + offsetZ;

        // 创建并返回新的 BlockPos
        return new BlockPos(leftX, leftY, leftZ);
    }

    public static BlockPos getLeftPosition(Entity entity, float offset) {
        // 获取实体的朝向（Yaw 角度）
        float yaw = entity.rotationYaw;

        // 将角度转换为弧度
        double radians = Math.toRadians(yaw);

        // 计算左边 3 格的偏移量
        double offsetX = Math.cos(radians) * offset;
        double offsetZ = Math.sin(radians) * offset;

        // 获取实体当前位置
        double currentX = entity.posX;
        double currentY = entity.posY;
        double currentZ = entity.posZ;

        // 计算左边 3 格的坐标
        double leftX = currentX + offsetX;
        double leftY = currentY;
        double leftZ = currentZ + offsetZ;

        // 创建并返回新的 BlockPos
        return new BlockPos(leftX, leftY, leftZ);
    }

    public static NBTTagCompound saveEntityToNbt(Entity entity) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        NBTTagCompound nbtTagCompound0 = new NBTTagCompound();
        nbtTagCompound0 = entity.writeToNBT(nbtTagCompound0);
        nbtTagCompound.setString("name", EntityList.getKey(entity).getNamespace());
        nbtTagCompound.setString("path", EntityList.getKey(entity).getPath());
        nbtTagCompound.setTag("data", nbtTagCompound0);
        System.out.println((nbtTagCompound.getString("name") + ":" + nbtTagCompound.getString("path")));
        return  nbtTagCompound;
    }

    public static Entity getEntityFromNbt(NBTTagCompound nbt, World world) {
        if (nbt.hasKey("name") && nbt.hasKey("path") && nbt.hasKey("data")) {

            NBTTagCompound nbt0 = new NBTTagCompound();
            nbt0.setString("id", (nbt.getString("name") + ":" + nbt.getString("path")));
            Entity entity = AnvilChunkLoader.readWorldEntity(nbt0, world, true);
            entity.readFromNBT((NBTTagCompound) nbt.getTag("data"));

            return entity;
        }
        return null;
    }

    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance) {
        return getClosestPlayer(world, posX, posY, posZ, distance, true);
    }

    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance, boolean includeCreative) {
        return getClosestPlayer(world, posX, posY, posZ, distance, includeCreative, false);
    }


    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance, boolean includeCreative, boolean includeSpectators) {
        double d0 = -1.0D;
        EntityPlayer closestPlayer = null;

        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer player = world.playerEntities.get(i);

            if ((!player.isCreative() || includeCreative) && (!player.isSpectator() || includeSpectators)) {
                double d1 = player.getDistanceSq(posX, posY, posZ);

                if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
                    d0 = d1;
                    closestPlayer = player;
                }
            }
        }

        return closestPlayer;
    }


    public static void faceEntity(EntityLivingBase entity, Entity target) {
        if (target != null) {
            // 计算目标位置与实体位置的差值
            double deltaX = target.posX - entity.posX;
            double deltaY = target.posY + target.getEyeHeight() - (entity.posY + entity.getEyeHeight());
            double deltaZ = target.posZ - entity.posZ;

            // 计算Yaw（水平角度）和Pitch（垂直角度）
            double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ); // 水平平面的距离
            float yaw = (float) (MathHelper.atan2(deltaZ, deltaX) * (180D / Math.PI)) - 90.0F;
            float pitch = (float) (-(MathHelper.atan2(deltaY, distanceXZ) * (180D / Math.PI)));

            // 设置实体的Yaw和Pitch
            entity.rotationYaw = yaw;
            entity.rotationPitch = pitch;
            entity.setRotationYawHead(yaw);
        }
    }
}
