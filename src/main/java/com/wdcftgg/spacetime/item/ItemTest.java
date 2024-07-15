package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityBlackHole;
import com.wdcftgg.spacetime.entity.EntityPortal;
import com.wdcftgg.spacetime.event.EventTimeBack;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTest extends Item implements IHasModel {
    public ItemTest()
    {
        setTranslationKey("sssssssad");
        setRegistryName("sssssssad");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote) {
            EntityPortal portal = new EntityPortal(world, 1);
            EntityPortal portal1 = new EntityPortal(world, 1);
            Tools.setPosition(portal, Tools.getLeftPosition(player, 3 ).up(4));
            Tools.setPosition(portal1, Tools.getRightPosition(player, 3 ).up(4));
            world.spawnEntity(portal);
            world.spawnEntity(portal1);
//            NBTTagCompound nbttagcompound = new NBTTagCompound();
//            boolean flag = false;
//            System.out.println("ssswwww");
//            nbttagcompound.setString("id", "minecraft:zombie");
//            Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, player.posX, player.posY, player.posZ, true);
//            entity.setLocationAndAngles(player.posX, player.posY, player.posZ, entity.rotationYaw, entity.rotationPitch);
//            ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
//            Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation("minecraft:vindicator"), world);
//            Tools.setPosition((EntityLivingBase) entity, player.getPosition());
//            world.spawnEntity(entity);
//            List<Entity> list = world.getLoadedEntityList();
//            for (Entity entity : list) {
//                if (entity instanceof EntityPortal) {
//                    EntityPortal portal = (EntityPortal) entity;
//                    portal.setCanSummon(true);
//                }
//            }
//            EntityZombie zombie = new EntityZombie(world)
//            System.out.println(zombie.r);
//
//            player.getHeldItem(hand).shrink(1);
//            ItemStack offitem = player.getHeldItem(EnumHand.OFF_HAND);
//            if (offitem.getItem().getRegistryName().toString().contains("shulker_box")) {
//                NBTTagCompound BlockEntityTag = (NBTTagCompound) offitem.getTagCompound().getTag("BlockEntityTag");
//                int num0 = 0;
//                for (NBTBase nbtBase : BlockEntityTag.getTagList("Items", 10)) {
//                    NBTTagCompound nbttagcompound = (NBTTagCompound) nbtBase;
//                    int num = nbttagcompound.getInteger("Count");
//                    num0 += num;
//                }
////                System.out.println(num0 + "---d");
////                System.out.println(offitem.serializeNBT()..getTagList("Items", 10) + "---d");
//            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}