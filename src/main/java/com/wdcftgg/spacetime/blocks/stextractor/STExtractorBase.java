package com.wdcftgg.spacetime.blocks.stextractor;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.blocks.STBlocks;
import com.wdcftgg.spacetime.blocks.tileEntity.stextractor.STExtractorEntity;
import com.wdcftgg.spacetime.gui.GuiElementLoader;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.IHasModel;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.lib.NBTTags;
import lumaceon.mods.clockworkphase.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/27 9:33
 */
public class STExtractorBase extends Block implements ITileEntityProvider, IHasModel {
    public STExtractorBase(String name){
        super(Material.ROCK);
        setTranslationKey(name + "_stextractor");
        setRegistryName(name + "_stextractor");

        STBlocks.BLOCKS.add(this);
        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        this.setCreativeTab(ModCreativeTab.SpaceTimeTab);
        setHarvestLevel("pickaxe", 2);
        setHardness(5.0F);
        setResistance(5.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new STExtractorEntity();
    }

    @Override
    public void registerModels() {
        SpaceTime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack is = player.getHeldItem(hand);
        if(!is.isEmpty() && is.getItem().equals(ModItems.timeTuner))
        {
            int ttx = NBTHelper.getInt(is, NBTTags.CLOCKWORK_PHASE_X);
            int tty = NBTHelper.getInt(is, NBTTags.CLOCKWORK_PHASE_Y);
            int ttz = NBTHelper.getInt(is, NBTTags.CLOCKWORK_PHASE_Z);
            TileEntity timeWell = world.getTileEntity(new BlockPos(ttx, tty, ttz));
            if(timeWell instanceof STExtractorEntity)
            {
                if(timeWell.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) <= 2048)
                {
                    TileEntity te = world.getTileEntity(pos);
                    if(te instanceof STExtractorEntity)
                    {
                        STExtractorEntity extractor = (STExtractorEntity) te;
                        extractor.timeWellX = ttx;
                        extractor.timeWellY = tty;
                        extractor.timeWellZ = ttz;
                    }
                }
                else if(world.isRemote)
                {
                    player.sendMessage(new TextComponentString("The time link is too weak from this distance."));
                }
            }
            return true;
        }
        else if(!world.isRemote && !player.isSneaking())
        {
            player.openGui(SpaceTime.instance, GuiElementLoader.GUI_Extractor, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof STExtractorEntity)
        {
            STExtractorEntity timeWell = (STExtractorEntity)te;
            for(int n = 0; n < timeWell.getSizeInventory(); n++)
            {
                ItemStack is = timeWell.removeStackFromSlot(n);
                if(!is.isEmpty())
                {
                    float f = 0.7F;
                    double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, is);
                    world.spawnEntity(entityitem);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }
}
