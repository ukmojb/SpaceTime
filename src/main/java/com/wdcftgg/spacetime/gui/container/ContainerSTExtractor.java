package com.wdcftgg.spacetime.gui.container;

import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import lumaceon.mods.clockworkphase.client.gui.components.SlotItemSpecific;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/27 9:31
 */
public class ContainerSTExtractor extends Container
{
    public STExtractorEntity te;

    public ContainerSTExtractor(STExtractorEntity te, InventoryPlayer ip, int STExtractorID)
    {
        super();
        this.te = te;

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(ip, j + i * 9 + 9, 48 + j * 18, 174 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(ip, i, 48 + i * 18, 232));
        }

        this.addSlotToContainer(new SlotItemSpecific(te, 0, 120, 74, ModItems.catalystElements[te.getSTExtractorID()]));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return te.isUsableByPlayer(player);
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }
}
