package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/14 21:00
 */
@Mod.EventBusSubscriber
public class EventWool {

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.world.isRemote){
            if (event.getHand() != EnumHand.MAIN_HAND)
                return;

            IBlockState worldBlock = event.getWorld().getBlockState(event.getPos());
            if (worldBlock.getBlock().getMetaFromState(worldBlock) == 10){
                if (event.getWorld().getBlockState(event.getPos().up().east()).getBlock() == ModBlocks.TIMEALTARCORE || event.getWorld().getBlockState(event.getPos().up().south()).getBlock() == ModBlocks.TIMEALTARCORE || event.getWorld().getBlockState(event.getPos().up().north()).getBlock() == ModBlocks.TIMEALTARCORE || event.getWorld().getBlockState(event.getPos().up().west()).getBlock() == ModBlocks.TIMEALTARCORE){
                    ItemStack stack = player.getHeldItem(event.getHand());
                    if(!stack.isEmpty()) {
                        ItemStack target = stack.splitStack(1);
                        EntityItem item = new EntityItem(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 1.25, event.getPos().getZ() + 0.5, target);
                        item.setPickupDelay(40);
                        item.motionX = item.motionY = item.motionZ = 0;
                        event.getWorld().spawnEntity(item);
                    }
                }
            }
        }
    }
}
