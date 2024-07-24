package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.client.event.EventTimeBack;
import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 22:53
 */
public class ItemOnePunch extends Item implements  IHasModel {
    public ItemOnePunch()
    {
        setTranslationKey("one_punch");
        setRegistryName("one_punch");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        entity.setDead();
        entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 9999);
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
            list.add(I18n.format("spacetime.one_punch.tooltip"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote) {
            List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.getPosition().south(3).west(3).up(), player.getPosition().north(3).east(3).down()));
            for (Entity entity : entityList) {
                if (entity instanceof EntitySpace2) continue;
                entity.setDead();
            }
//            player.capabilities.setPlayerWalkSpeed(0.1F);

//            player.addPotionEffect(new PotionEffect(ModPotions.SpaceStop, 60));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }


}
