package com.wdcftgg.spacetime.item;/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/31 21:42
 */

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSuperGoldApple extends ItemFood implements IHasModel {
    public ItemSuperGoldApple() {
        super(10, 66.6F, true);
        this.setAlwaysEdible();
        this.setTranslationKey("supergoldapple");
        this.setRegistryName("supergoldapple");
        this.setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 4800, 0));
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(22), 4800, 5));
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(12), 4800, 0));
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(13), 4800, 0));
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 4800, 1));
            player.addExperience(2048);
        }
        super.onFoodEaten(stack, worldIn, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_hasEffect_1_) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack p_getRarity_1_) {
        return EnumRarity.EPIC;
    }

    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
