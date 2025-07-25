package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.BookRegistry;
import com.wdcftgg.spacetime.gui.book.data.ChapterData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.network.MessageRemovePotion;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.SpaceFrozenHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


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
//            ItemStack itemStack = player.getHeldItem(hand);
//            BlockPos right = Tools.getRightPosition(player, 1.5F);
//            EntitySpearsubspace spearsubspace = new EntitySpearsubspace(world, right.getX(), right.getY() + 0.5, right.getZ(), 0);
//            spearsubspace.shoot(player, 0, player.getRotationYawHead(), 0.0F, 1.5F, 1.0F);
//            world.spawnEntity(spearsubspace);
//            SpaceFrozenHelper.addSpaceFrozen(player, 20);
//            player.sendMessage(new TextComponentString(SpaceFrozenHelper.getSpaceFrozen(player) + ""));
//            SpaceFrozenHelper.addSpaceFrozen(player, 20);
//            player.removePotionEffect(ModPotions.LossSpatialSense);
//            if (player.isSneaking()) {
//                player.addPotionEffect(new PotionEffect(ModPotions.LossSpatialSense, 99999, 0, true, false));
//            }
            System.out.println(world.isRaining());
            System.out.println(world.rainingStrength);
//            PacketHandler.INSTANCE.sendTo(new MessageRemovePotion(Potion.getIdFromPotion(MobEffects.LEVITATION)),(EntityPlayerMP) player);

//            player.sendMessage(new TextComponentString(SpaceFrozenHelper.getSpaceFrozen(player) + "--" + SpaceFrozenHelper.getIsSpaceFrozen(player)));

        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (!player.world.isRemote) {
            EntityLiving living = (EntityLiving) entity;
            living.getLookHelper().setLookPositionWithEntity(player, 360, 360);
        }
        return false;
    }
}