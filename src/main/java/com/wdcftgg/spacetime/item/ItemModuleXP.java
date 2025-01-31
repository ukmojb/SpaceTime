package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.item.construct.pocketwatch.module.ItemModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/31 17:34
 */
public class ItemModuleXP extends ItemModule implements IHasModel {
    public ItemModuleXP() {
        setTranslationKey("module_xp");
        setRegistryName("module_xp");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);
    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public int getPowerDivisor() {
        return 5;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            if (playerIn.experienceLevel >= 30) {
                playerIn.addExperienceLevel(-30);
            }
        }
    }
}
