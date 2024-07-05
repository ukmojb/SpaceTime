package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.entity.EntityBlackHole;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/3 20:07
 */
@Mod.EventBusSubscriber
public class EventSpace {
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = new BlockPos(60,81,0);
        if (!player.world.isRemote){
            if (event.getHand() != EnumHand.MAIN_HAND)
                return;

            if (event.getPos().equals(pos)){
                if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.BEACON){
                    ItemStack stack = player.getHeldItem(event.getHand());
                    if (!stack.isEmpty() && player.isSneaking()) {
                        if (stack.getItem().equals(Items.GOLDEN_APPLE) && stack.getMetadata() == 1) {
                            EntitySpace entitySpace = new EntitySpace(event.getWorld());
                            entitySpace.setPosition(77.5, 86, 0.5);
                            event.getWorld().spawnEntity(entitySpace);
                            stack.shrink(1);
                            Tools.setBlockAABB(pos.up().west().north(), pos.down().east().south(), Blocks.AIR, entitySpace);
                        }
                    }
                }

            }
        }
    }
}
