package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityUnstableTimePolymer;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ITime;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 15:24
 */
public class ItemUnstableTimePolymer extends Item implements ITime, IHasModel {
    public ItemUnstableTimePolymer()
    {
        setTranslationKey("unstable_time_polymer");
        setRegistryName("unstable_time_polymer");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        int time = getTime(is);
        int chance = 200000;
        float percentagechance = (float)  time / chance * 100;
        if (time <= 0){
            list.add("Contain Time: §eNo Time Energy");
        } else {
            list.add("Contain Time: §e" + time);
        }
        if (time > 0) {
            list.add("Chance of split Time Crack: " + percentagechance + "%");
        }
    }

    @Override
    public int getMaxTime()
    {
        return 200000;
    }

    @Override
    public int addTime(ItemStack is, int time)
    {
        return TimeHelper.addTime(is, time, getMaxTime());
    }

    @Override
    public int getTime(ItemStack is)
    {
        return TimeHelper.getTime(is);
    }

    @Override
    public int getTimeFromInventory(IInventory inventory)
    {
        return TimeHelper.getTimeFromInventory(inventory);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityUnstableTimePolymer unstabletimepolymer = new EntityUnstableTimePolymer(world, player);
            unstabletimepolymer.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            unstabletimepolymer.addTag(String.valueOf(TimeHelper.getTime(item)));
            world.spawnEntity(unstabletimepolymer);
        }
        item.shrink(1);
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public Item getItemChangeTo()
    {
        return STItems.UNSTABLETIMEPOLYMER;
    }
}
