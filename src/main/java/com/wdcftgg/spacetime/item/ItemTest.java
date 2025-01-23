package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.particle.ParticleSpearsubspace;
import com.wdcftgg.spacetime.entity.EntityPortal;
import com.wdcftgg.spacetime.entity.EntitySpearsubspace;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.init.ParticleInit;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.tools.Tool;
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
            ItemStack itemStack = player.getHeldItem(hand);
            BlockPos right = Tools.getRightPosition(player, 1.5F);
            EntitySpearsubspace spearsubspace = new EntitySpearsubspace(world, right.getX(), right.getY() + 0.5, right.getZ(), 0);
            spearsubspace.shoot(player, 0, player.getRotationYawHead(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(spearsubspace);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (!player.world.isRemote) {
            System.out.println("adwdaw");
            EntityLiving living = (EntityLiving) entity;
            living.getLookHelper().setLookPositionWithEntity(player, 360, 360);
        }
        return false;
    }
}