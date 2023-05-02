package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.ITime;
import com.wdcftgg.spacetime.util.NBTHelper;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import lumaceon.mods.clockworkphase.init.ModBlocks;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 23:04
 */
public class TimeCompressorEntity  extends TileEntity implements ITickable {
    public void update() {
        if (!world.isRemote) {
            if(isGlass(this.pos) && isBrass(this.pos) && temporalOrGlass(this.pos)) {

                int Containtemporal = temporalNumber(this.pos, this.world);
                int ContainTime = 0;
                ContainTime = Containtemporal * 7000;
                if (Containtemporal == 26){
                    ContainTime = 200000;
                }
                ItemStack spownstack = new ItemStack(ModItems.TIMEPOLYMER, 1);
                TimeHelper.addTime(spownstack, ContainTime, 200000);
                EntityItem spownitem = new EntityItem(world, this.pos.getX(), (this.pos.getY() + 1), this.pos.getZ(), spownstack);
                this.world.spawnEntity(spownitem);

                allToAir(this.pos);
            }
        }
    }

    private boolean isGlass (BlockPos pos) {
        if (    world.getBlockState(pos.up(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).south().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).south().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).north().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.up(2).north().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).south().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).south().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).north().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.down(2).north().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).up().north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).up().south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).down().north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).down().south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).up().north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).up().south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).down().north()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).down().south()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).up().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).up().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).down().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).down().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2)).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).up().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).up().west()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).down().east()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).down().west()).getBlock() == Blocks.GLASS &&

                world.getBlockState(pos.south(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.south(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.north(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.west(2).down()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).up()).getBlock() == Blocks.GLASS &&
                world.getBlockState(pos.east(2).down()).getBlock() == Blocks.GLASS

        ){
            return true;
        }
        return false;
    }

    private boolean isBrass (BlockPos pos) {
        if (
                world.getBlockState(pos.up(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).south().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).south().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).north().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.up(3).north().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).south().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).south().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).north().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.down(3).north().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).up()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).down()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).up().north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).up().south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).down().north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(3).down().south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).up()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).down()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).up().north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).up().south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).down().north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(3).down().south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).up()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).down()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).up().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).up().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).down().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(3).down().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).up()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).down()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).up().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).up().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).down().east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(3).down().west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).up(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).up(2).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).up(2).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).up(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).up(2).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).up(2).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).up(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).up(2).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).up(2).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).up(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).up(2).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).up(2).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).down(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).down(2).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.south(2).down(2).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).down(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).down(2).east()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.north(2).down(2).west()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).down(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).down(2).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.west(2).down(2).north()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).down(2)).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).down(2).south()).getBlock() == ModBlocks.brassBlock &&
                world.getBlockState(pos.east(2).down(2).north()).getBlock() == ModBlocks.brassBlock

        ){
            return true;
        }
        return false;
    }

    private int temporalNumber (BlockPos pos, World entityworld) {

        int number = 0;
        if (entityworld.getBlockState(pos.east()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.west()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().east()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().west()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().east().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().west().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().east().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.up().west().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().east()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().west()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().east().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().west().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().east().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().west().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().south()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.down().north()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.north().west()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.north().east()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.south().east()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        if (entityworld.getBlockState(pos.south().west()).getBlock() == ModBlocks.blockTemporal) {
            number += 1;
        }
        return number;
    }

    private boolean temporalOrGlass (BlockPos pos) {
        if (world.getBlockState(pos.east()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.east()).getBlock() == Blocks.GLASS){
            if (world.getBlockState(pos.west()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.west()).getBlock() == Blocks.GLASS) {
                if (world.getBlockState(pos.north()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.north()).getBlock() == Blocks.GLASS) {
                    if (world.getBlockState(pos.south()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.south()).getBlock() == Blocks.GLASS) {
                        if (world.getBlockState(pos.up()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.up()).getBlock() == Blocks.GLASS) {
                            if (world.getBlockState(pos.down()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.down()).getBlock() == Blocks.GLASS) {
                                if (world.getBlockState(pos.up().east()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.up().east()).getBlock() == Blocks.GLASS) {
                                    if (world.getBlockState(pos.up().west()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.up().west()).getBlock() == Blocks.GLASS) {
                                        if (world.getBlockState(pos.up().south()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.up().south()).getBlock() == Blocks.GLASS) {
                                            if (world.getBlockState(pos.up().north()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.up().north()).getBlock() == Blocks.GLASS) {
                                                if (world.getBlockState(pos.down().east()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.down().east()).getBlock() == Blocks.GLASS) {
                                                    if (world.getBlockState(pos.down().west()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.down().west()).getBlock() == Blocks.GLASS) {
                                                        if (world.getBlockState(pos.down().south()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.down().south()).getBlock() == Blocks.GLASS) {
                                                            if (world.getBlockState(pos.down().north()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.down().north()).getBlock() == Blocks.GLASS) {
                                                                if (world.getBlockState(pos.north().west()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.north().west()).getBlock() == Blocks.GLASS) {
                                                                    if (world.getBlockState(pos.north().east()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.north().east()).getBlock() == Blocks.GLASS) {
                                                                        if (world.getBlockState(pos.south().east()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.south().east()).getBlock() == Blocks.GLASS) {
                                                                            if (world.getBlockState(pos.south().west()).getBlock() == ModBlocks.blockTemporal || world.getBlockState(pos.south().west()).getBlock() == Blocks.GLASS) {
                                                                                return true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }



    private void allToAir (BlockPos pos) {
        world.setBlockToAir(pos.east());
        world.setBlockToAir(pos.west());
        world.setBlockToAir(pos.north());
        world.setBlockToAir(pos.south());
        world.setBlockToAir(pos.up());
        world.setBlockToAir(pos.down());
        world.setBlockToAir(pos.up().east());
        world.setBlockToAir(pos.up().west());
        world.setBlockToAir(pos.up().south());
        world.setBlockToAir(pos.up().north());
        world.setBlockToAir(pos.down().east());
        world.setBlockToAir(pos.down().west());
        world.setBlockToAir(pos.down().south());
        world.setBlockToAir(pos.down().north());
        world.setBlockToAir(pos.north().west());
        world.setBlockToAir(pos.north().east());
        world.setBlockToAir(pos.south().east());
        world.setBlockToAir(pos.south().west());
        world.setBlockToAir(pos.up(3));
        world.setBlockToAir(pos.up(3).east());
        world.setBlockToAir(pos.up(3).north());
        world.setBlockToAir(pos.up(3).south());
        world.setBlockToAir(pos.up(3).west());
        world.setBlockToAir(pos.up(3).south().east());
        world.setBlockToAir(pos.up(3).south().west());
        world.setBlockToAir(pos.up(3).north().east());
        world.setBlockToAir(pos.up(3).north().west());
        world.setBlockToAir(pos.down(3));
        Timer timer1 =new Timer();
        Timer timer2 =new Timer();
        Timer timer3 =new Timer();
        Timer timer4 =new Timer();
        TimerTask task1 =new TimerTask(){
            public void run(){
                world.setBlockToAir(pos.down(3).west());
                world.setBlockToAir(pos.down(3).east());
                world.setBlockToAir(pos.down(3).north());
                world.setBlockToAir(pos.down(3).south());
                world.setBlockToAir(pos.down(3).south().east());
                world.setBlockToAir(pos.down(3).south().west());
                world.setBlockToAir(pos.down(3).north().east());
                world.setBlockToAir(pos.down(3).north().west());
                world.setBlockToAir(pos.west(3));
                world.setBlockToAir(pos.west(3).up());
                world.setBlockToAir(pos.west(3).down());
                world.setBlockToAir(pos.west(3).north());
                world.setBlockToAir(pos.west(3).south());
                world.setBlockToAir(pos.west(3).up().north());
                world.setBlockToAir(pos.west(3).up().south());
                world.setBlockToAir(pos.west(3).down().north());
                world.setBlockToAir(pos.west(3).down().south());
                world.setBlockToAir(pos.east(3));
                world.setBlockToAir(pos.east(3).up());
                world.setBlockToAir(pos.east(3).down());
                world.setBlockToAir(pos.east(3).north());
                world.setBlockToAir(pos.east(3).south());
                world.setBlockToAir(pos.east(3).up().north());
                world.setBlockToAir(pos.east(3).up().south());
                world.setBlockToAir(pos.east(3).down().north());
                world.setBlockToAir(pos.east(3).down().south());
                world.setBlockToAir(pos.north(3));
                world.setBlockToAir(pos.north(3).west());
                world.setBlockToAir(pos.north(3).east());
                world.setBlockToAir(pos.north(3).up());
                world.setBlockToAir(pos.north(3).down());
                world.setBlockToAir(pos.north(3).up().east());
                world.setBlockToAir(pos.north(3).up().west());
                world.setBlockToAir(pos.north(3).down().east());
                world.setBlockToAir(pos.north(3).down().west());
                world.setBlockToAir(pos.south(3));
                world.setBlockToAir(pos.south(3).west());
                world.setBlockToAir(pos.south(3).east());
                world.setBlockToAir(pos.south(3).up());
                world.setBlockToAir(pos.south(3).down());
            }
        };
        TimerTask task2 =new TimerTask(){
            public void run(){
                world.setBlockToAir(pos.south(3).up().east());
                world.setBlockToAir(pos.south(3).up().west());
                world.setBlockToAir(pos.south(3).down().east());
                world.setBlockToAir(pos.south(3).down().west());
                world.setBlockToAir(pos.south(2).up(2));
                world.setBlockToAir(pos.south(2).up(2).east());
                world.setBlockToAir(pos.south(2).up(2).west());
                world.setBlockToAir(pos.north(2).up(2));
                world.setBlockToAir(pos.north(2).up(2).east());
                world.setBlockToAir(pos.north(2).up(2).west());
                world.setBlockToAir(pos.west(2).up(2));
                world.setBlockToAir(pos.west(2).up(2).south());
                world.setBlockToAir(pos.west(2).up(2).north());
                world.setBlockToAir(pos.east(2).up(2));
                world.setBlockToAir(pos.east(2).up(2).south());
                world.setBlockToAir(pos.east(2).up(2).north());
                world.setBlockToAir(pos.south(2).down(2));
                world.setBlockToAir(pos.south(2).down(2).east());
                world.setBlockToAir(pos.south(2).down(2).west());
                world.setBlockToAir(pos.north(2).down(2));
                world.setBlockToAir(pos.north(2).down(2).east());
                world.setBlockToAir(pos.north(2).down(2).west());
                world.setBlockToAir(pos.west(2).down(2));
                world.setBlockToAir(pos.west(2).down(2).south());
                world.setBlockToAir(pos.west(2).down(2).north());
                world.setBlockToAir(pos.east(2).down(2));
                world.setBlockToAir(pos.east(2).down(2).south());
                world.setBlockToAir(pos.east(2).down(2).north());
                world.setBlockToAir(pos.up(2));
                world.setBlockToAir(pos.up(2).east());
                world.setBlockToAir(pos.up(2).north());
                world.setBlockToAir(pos.up(2).south());
                world.setBlockToAir(pos.up(2).west());
                world.setBlockToAir(pos.up(2).south().east());
                world.setBlockToAir(pos.up(2).south().west());
                world.setBlockToAir(pos.up(2).north().east());
                world.setBlockToAir(pos.up(2).north().west());
                world.setBlockToAir(pos.down(2));
                world.setBlockToAir(pos.down(2).west());
                world.setBlockToAir(pos.down(2).east());
                world.setBlockToAir(pos.down(2).north());
                world.setBlockToAir(pos.down(2).south());
                world.setBlockToAir(pos.down(2).south().east());
                world.setBlockToAir(pos.down(2).south().west());
                world.setBlockToAir(pos.down(2).north().east());
                world.setBlockToAir(pos.down(2).north().west());
                world.setBlockToAir(pos.west(2));
                world.setBlockToAir(pos.west(2).up());
                world.setBlockToAir(pos.west(2).down());
                world.setBlockToAir(pos.west(2).north());
                world.setBlockToAir(pos.west(2).south());
                world.setBlockToAir(pos.west(2).up().north());
                world.setBlockToAir(pos.west(2).up().south());
                world.setBlockToAir(pos.west(2).down().north());
                world.setBlockToAir(pos.west(2).down().south());
                world.setBlockToAir(pos.east(2));
            }
        };
        TimerTask task3 =new TimerTask(){
            public void run(){
                world.setBlockToAir(pos.east(2).up());
                world.setBlockToAir(pos.east(2).down());
                world.setBlockToAir(pos.east(2).north());
                world.setBlockToAir(pos.east(2).south());
                world.setBlockToAir(pos.east(2).up().north());
                world.setBlockToAir(pos.east(2).up().south());
                world.setBlockToAir(pos.east(2).down().north());
                world.setBlockToAir(pos.east(2).down().south());
                world.setBlockToAir(pos.north(2));
                world.setBlockToAir(pos.north(2).west());
                world.setBlockToAir(pos.north(2).east());
                world.setBlockToAir(pos.north(2).up());
                world.setBlockToAir(pos.north(2).down());
                world.setBlockToAir(pos.north(2).up().east());
                world.setBlockToAir(pos.north(2).up().west());
                world.setBlockToAir(pos.north(2).down().east());
                world.setBlockToAir(pos.north(2).down().west());
                world.setBlockToAir(pos.south(2));
                world.setBlockToAir(pos.south(2).west());
                world.setBlockToAir(pos.south(2).east());
                world.setBlockToAir(pos.south(2).up());
                world.setBlockToAir(pos.south(2).down());
                world.setBlockToAir(pos.south(2).up().east());
                world.setBlockToAir(pos.south(2).up().west());
                world.setBlockToAir(pos.south(2).down().east());
                world.setBlockToAir(pos.south(2).down().west());
                world.setBlockToAir(pos.up().south().west());
                world.setBlockToAir(pos.up().south().east());
                world.setBlockToAir(pos.up().north().west());
                world.setBlockToAir(pos.up().north().east());
                world.setBlockToAir(pos.down().south().west());
                world.setBlockToAir(pos.down().south().east());
                world.setBlockToAir(pos.down().north().west());
                world.setBlockToAir(pos.down().north().east());
            }
        };
        TimerTask task4 =new TimerTask(){
            public void run(){
                world.setBlockToAir(pos.south(2).up());
                world.setBlockToAir(pos.south(2).down());
                world.setBlockToAir(pos.north(2).up());
                world.setBlockToAir(pos.north(2).down());
                world.setBlockToAir(pos.west(2).up());
                world.setBlockToAir(pos.west(2).down());
                world.setBlockToAir(pos.east(2).up());
                world.setBlockToAir(pos.east(2).down());

                world.setBlockToAir(pos.south(3).up());
                world.setBlockToAir(pos.south(3).down());
                world.setBlockToAir(pos.north(3).up());
                world.setBlockToAir(pos.north(3).down());
                world.setBlockToAir(pos.west(3).up());
                world.setBlockToAir(pos.west(3).down());
                world.setBlockToAir(pos.east(3).up());
                world.setBlockToAir(pos.east(3).down());
                world.setBlockToAir(pos.east(2).south(2));
                world.setBlockToAir(pos.east(2).north(2));
                world.setBlockToAir(pos.east(2).south(2).up());
                world.setBlockToAir(pos.east(2).south(2).down());
                world.setBlockToAir(pos.east(2).north(2).up());
                world.setBlockToAir(pos.east(2).north(2).down());
                world.setBlockToAir(pos.west(2).south(2));
                world.setBlockToAir(pos.west(2).north(2));
                world.setBlockToAir(pos.west(2).south(2).up());
                world.setBlockToAir(pos.west(2).south(2).down());
                world.setBlockToAir(pos.west(2).north(2).up());
                world.setBlockToAir(pos.west(2).north(2).down());
            }
        };
        timer1.schedule(task1,100);
        timer2.schedule(task2,200);
        timer3.schedule(task3,300);
        timer4.schedule(task4,400);

    }
}
