package com.wdcftgg.spacetime.gui;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.gui.container.ContainerSTExtractor;
import com.wdcftgg.spacetime.gui.guicontainer.GuiSTExtractor;
import lumaceon.mods.clockworkphase.ClockworkPhase;
import lumaceon.mods.clockworkphase.block.tileentity.TileEntityExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

//todo
//https://fmltutor.ustc-zzzz.net/3.4.3-GUI%E7%95%8C%E9%9D%A2%E4%B8%AD%E7%9A%84%E4%BA%A4%E4%BA%92.html
public class GuiElementLoader implements IGuiHandler {

    public GuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpaceTime.instance, this);
    }
    public static final int GUI_Extractor = 0;


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID)
        {
            case GUI_Extractor:
                TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                if(te instanceof STExtractorEntity)
                {
                    return new ContainerSTExtractor((STExtractorEntity) te, player.inventory, ((STExtractorEntity) te).getSTExtractorID());
                }
                break;
            default:
                return null;
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te;
        switch(ID)
        {
            case GUI_Extractor:
                te = world.getTileEntity(new BlockPos(x, y, z));
                if(te instanceof STExtractorEntity)
                {
                    return new GuiSTExtractor((STExtractorEntity)te, player.inventory, ((STExtractorEntity) te).getSTExtractorID());
                }
                break;

            default:
                return null;
        }
        return null;
    }
}
