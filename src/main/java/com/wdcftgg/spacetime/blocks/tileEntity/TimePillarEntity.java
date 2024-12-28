package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Objects;

public class TimePillarEntity  extends TileEntity implements ITickable {

    private int ticksexisted = 0;

    public void update() {
        if (!world.isRemote) {
            if (ticksexisted >= 50) {
                EntityPlayer player = world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 30, false);
                if (player != null) {
                    player.addItemStackToInventory(new ItemStack(STBlocks.TIMEPILLAR));
                    world.destroyBlock(this.pos, false);
                }
            } else {
                ticksexisted++;
            }
        }
    }



}