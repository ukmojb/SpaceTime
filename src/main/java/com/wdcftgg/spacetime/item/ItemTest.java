package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityBlackHole;
import com.wdcftgg.spacetime.event.EventTimeBack;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
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
            EntityBlackHole bl = new EntityBlackHole(player.world, 1, false);
            bl.setPosition(player.posX + 30, player.posY, player.posZ);
            player.world.spawnEntity(bl);
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