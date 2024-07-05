package com.wdcftgg.spacetime.blocks.tileEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ReverseBeaconEntity extends
        TileEntity implements ITickable,

        ISidedInventory {
        /** List of effects that Beacon can apply */
        public static final Potion[][] EFFECTS_LIST = new Potion[][]{{MobEffects.SPEED, MobEffects.HASTE}, {MobEffects.RESISTANCE, MobEffects.JUMP_BOOST}, {MobEffects.STRENGTH}, {MobEffects.REGENERATION}};
        private static final Set<Potion> VALID_EFFECTS = Sets.<Potion>newHashSet();
        private final List<ReverseBeaconEntity.BeamSegment> beamSegments = Lists.<ReverseBeaconEntity.BeamSegment>newArrayList();
        @SideOnly(Side.CLIENT)
        private long beamRenderCounter;
        @SideOnly(Side.CLIENT)
        private float beamRenderScale;
        private boolean isComplete;
        /** Level of this beacon's pyramid. */
        private int levels = -1;
        /** Primary potion effect given by this beacon. */
        @Nullable
        private Potion primaryEffect;
        /** Secondary potion effect given by this beacon. */
        @Nullable
        private Potion secondaryEffect;
        /** Item given to this beacon as payment. */
        private ItemStack payment = ItemStack.EMPTY;
        /** Currently unused; see https://bugs.mojang.com/browse/MC-124395 */
        private String customName;

        /**
         * Like the old updateEntity(), except more generic.
         */
        @Override
        public void update ()
        {
            if (this.world.getTotalWorldTime() % 80L == 0L) {
                this.updateBeacon();
            }
            super.updateContainingBlockInfo();
        }


        public void updateBeacon ()
        {
            if (this.world != null) {
                this.updateSegmentColors();
                this.addEffectsToPlayers();
            }
        }

        private void addEffectsToPlayers ()
        {
            if (this.isComplete && this.levels > 0 && !this.world.isRemote && this.primaryEffect != null) {
                double d0 = (double) (this.levels * 10 + 10);
                int i = 0;

                if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
                    i = 1;
                }

                int j = (9 + this.levels * 2) * 20;
                int k = this.pos.getX();
                int l = this.pos.getY();
                int i1 = this.pos.getZ();
                AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) k, (double) l, (double) i1, (double) (k + 1), (double) (l + 1), (double) (i1 + 1))).grow(d0).expand(0.0D, (double) this.world.getHeight(), 0.0D);
                List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

                for (EntityPlayer entityplayer : list) {
                    entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, j, i, true, true));
                }

                if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null) {
                    for (EntityPlayer entityplayer1 : list) {
                        entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect, j, 0, true, true));
                    }
                }
            }
        }

        private void updateSegmentColors ()
        {
            int i = this.pos.getX();
            int j = this.pos.getY();
            int k = this.pos.getZ();
            int l = this.levels;
            this.levels = 0;
            this.beamSegments.clear();
            this.isComplete = true;
            ReverseBeaconEntity.BeamSegment tileentitybeacon$beamsegment = new ReverseBeaconEntity.BeamSegment(EnumDyeColor.WHITE.getColorComponentValues());
            this.beamSegments.add(tileentitybeacon$beamsegment);
            boolean flag = true;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int i1 = j - 1; i1 > 0; --i1) {
                IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos.setPos(i, i1, k));
                float[] afloat;

//                            System.out.println(i + "-" + i1 + "-" + k);
                if (iblockstate.getBlock() == Blocks.STAINED_GLASS) {
                    afloat = ((EnumDyeColor) iblockstate.getValue(BlockStainedGlass.COLOR)).getColorComponentValues();
                } else {
                    if (iblockstate.getBlock() != Blocks.STAINED_GLASS_PANE) {
                        if (iblockstate.getBlock() == Blocks.BEDROCK) {
//                            this.isComplete = false;
//                            this.beamSegments.clear();
                            break;
                        }
                        float[] customColor = iblockstate.getBlock().getBeaconColorMultiplier(iblockstate, this.world, blockpos$mutableblockpos, getPos());
                        if (customColor != null)
                            afloat = customColor;
                        else {
                            tileentitybeacon$beamsegment.incrementHeight();
                            continue;
                        }
                    } else
                        afloat = ((EnumDyeColor) iblockstate.getValue(BlockStainedGlassPane.COLOR)).getColorComponentValues();
                }

                if (!flag) {
                    afloat = new float[]{(tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F};
                }

                if (Arrays.equals(afloat, tileentitybeacon$beamsegment.getColors())) {
                    tileentitybeacon$beamsegment.incrementHeight();
                } else {
                    tileentitybeacon$beamsegment = new ReverseBeaconEntity.BeamSegment(afloat);
                    this.beamSegments.add(tileentitybeacon$beamsegment);
                }

                flag = false;
            }

            if (this.isComplete) {
                for (int l1 = 1; l1 <= 4; this.levels = l1++) {
                    int i2 = j + l1;

                    if (i2 < 0) {
                        break;
                    }

                    boolean flag1 = true;

                    for (int j1 = i - l1; j1 <= i + l1 && flag1; ++j1) {
                        for (int k1 = k - l1; k1 <= k + l1; ++k1) {
                            Block block = this.world.getBlockState(new BlockPos(j1, i2, k1)).getBlock();
//                            System.out.println(block.getRegistryName().toString() + "sss");
//                            System.out.println(!block.isBeaconBase(this.world, new BlockPos(j1, i2, k1), getPos()) + "ddd");

                            if (!block.isBeaconBase(this.world, new BlockPos(j1, i2, k1), getPos())) {
                                flag1 = false;
                                break;
                            }
                        }
                    }

                    if (!flag1) {
                        break;
                    }
                }

                if (this.levels == 0) {
                    this.isComplete = false;
                }
            }
        }

        @SideOnly(Side.CLIENT)
        public List<ReverseBeaconEntity.BeamSegment> getBeamSegments ()
        {
            return this.beamSegments;
        }

        @SideOnly(Side.CLIENT)
        public float shouldBeamRender ()
        {
            if (!this.isComplete) {
                return 0.0F;
            } else {
                int i = (int) (this.world.getTotalWorldTime() - this.beamRenderCounter);
                this.beamRenderCounter = this.world.getTotalWorldTime();

                if (i > 1) {
                    this.beamRenderScale -= (float) i / 40.0F;

                    if (this.beamRenderScale < 0.0F) {
                        this.beamRenderScale = 0.0F;
                    }
                }

                this.beamRenderScale += 0.025F;

                if (this.beamRenderScale > 1.0F) {
                    this.beamRenderScale = 1.0F;
                }

                return this.beamRenderScale;
            }
        }


        public int getLevels ()
        {
            return this.levels;
        }

        /**
         * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
         * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
         */
        @Nullable
        @Override
        public SPacketUpdateTileEntity getUpdatePacket ()
        {
            return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
        }


        @Override
        public NBTTagCompound getUpdateTag ()
        {
            return this.writeToNBT(new NBTTagCompound());
        }

        @SideOnly(Side.CLIENT)
        @Override
        public double getMaxRenderDistanceSquared ()
        {
            return 65536.0D;
        }

        @Nullable
        private static Potion isBeaconEffect ( int p_184279_0_)
        {
            Potion potion = Potion.getPotionById(p_184279_0_);
            return VALID_EFFECTS.contains(potion) ? potion : null;
        }

        @Override
        public void readFromNBT (NBTTagCompound compound)
        {
            super.readFromNBT(compound);
            this.primaryEffect = isBeaconEffect(compound.getInteger("Primary"));
            this.secondaryEffect = isBeaconEffect(compound.getInteger("Secondary"));
            this.levels = compound.getInteger("Levels");
        }

        @Override
        public NBTTagCompound writeToNBT (NBTTagCompound compound)
        {
            super.writeToNBT(compound);
            compound.setInteger("Primary", Potion.getIdFromPotion(this.primaryEffect));
            compound.setInteger("Secondary", Potion.getIdFromPotion(this.secondaryEffect));
            compound.setInteger("Levels", this.levels);
            return compound;
        }

        /**
         * Returns the number of slots in the inventory.
         */
        @Override
        public int getSizeInventory ()
        {
            return 1;
        }

        @Override
        public boolean isEmpty ()
        {
            return this.payment.isEmpty();
        }

        /**
         * Returns the stack in the given slot.
         */
        @Override
        public ItemStack getStackInSlot ( int index)
        {
            return index == 0 ? this.payment : ItemStack.EMPTY;
        }

        /**
         * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
         */
        @Override
        public ItemStack decrStackSize ( int index, int count)
        {
            if (index == 0 && !this.payment.isEmpty()) {
                if (count >= this.payment.getCount()) {
                    ItemStack itemstack = this.payment;
                    this.payment = ItemStack.EMPTY;
                    return itemstack;
                } else {
                    return this.payment.splitStack(count);
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        /**
         * Removes a stack from the given slot and returns it.
         */
        @Override
        public ItemStack removeStackFromSlot ( int index)
        {
            if (index == 0) {
                ItemStack itemstack = this.payment;
                this.payment = ItemStack.EMPTY;
                return itemstack;
            } else {
                return ItemStack.EMPTY;
            }
        }

        /**
         * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
         */
        @Override
        public void setInventorySlotContents ( int index, ItemStack stack)
        {
            if (index == 0) {
                this.payment = stack;
            }
        }

        @Override
        public String getName ()
        {
            return this.hasCustomName() ? this.customName : "container.beacon";
        }

        @Override
        public boolean hasCustomName ()
        {
            return this.customName != null && !this.customName.isEmpty();
        }


        public void setName (String name)
        {
            this.customName = name;
        }

        /**
         * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
         */
        @Override
        public int getInventoryStackLimit ()
        {
            return 1;
        }

        /**
         * Don't rename this method to canInteractWith due to conflicts with Container
         */
        @Override
        public boolean isUsableByPlayer (EntityPlayer player)
        {
            if (this.world.getTileEntity(this.pos) != this) {
                return false;
            } else {
                return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
            }
        }

        public void openInventory (EntityPlayer player)
        {
        }

        public void closeInventory (EntityPlayer player)
        {
        }

        /**
         * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
         * guis use Slot.isItemValid
         */
        @Override
        public boolean isItemValidForSlot ( int index, ItemStack stack)
        {
            return stack.getItem() != null && stack.getItem().isBeaconPayment(stack);
        }


        public String getGuiID ()
        {
            return "minecraft:beacon";
        }


        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerBeacon(playerInventory, this);
        }

        @Override
        public int getField ( int id)
        {
            switch (id) {
                case 0:
                    return this.levels;
                case 1:
                    return Potion.getIdFromPotion(this.primaryEffect);
                case 2:
                    return Potion.getIdFromPotion(this.secondaryEffect);
                default:
                    return 0;
            }
        }

        @Override
        public void setField(int id, int value)
        {
            switch (id) {
                case 0:
                    this.levels = value;
                    break;
                case 1:
                    this.primaryEffect = isBeaconEffect(value);
                    break;
                case 2:
                    this.secondaryEffect = isBeaconEffect(value);
            }
        }

        @Override
        public int getFieldCount ()
        {
            return 3;
        }

        @Override
        public void clear ()
        {
            this.payment = net.minecraft.item.ItemStack.EMPTY;
        }

        /**
         * See {@link Block#eventReceived} for more information. This must return true serverside before it is called
         * clientside.
         */
        @Override
        public boolean receiveClientEvent ( int id, int type)
        {
            if (id == 1) {
                this.updateBeacon();
                return true;
            } else {
                return super.receiveClientEvent(id, type);
            }
        }

        @Override
        public int[] getSlotsForFace (EnumFacing side)
        {
            return new int[0];
        }

        /**
         * Returns true if automation can insert the given item in the given slot from the given side.
         */
        @Override
        public boolean canInsertItem ( int index, ItemStack itemStackIn, EnumFacing direction)
        {
            return false;
        }

        /**
         * Returns true if automation can extract the given item in the given slot from the given side.
         */
        @Override
        public boolean canExtractItem ( int index, ItemStack stack, EnumFacing direction)
        {
            return false;
        }


        @Nonnull
        public AxisAlignedBB getRenderBoundingBox() {
            return new AxisAlignedBB(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

    static
        {
            for (Potion[] apotion : EFFECTS_LIST) {
                Collections.addAll(VALID_EFFECTS, apotion);
            }
        }

        public static class BeamSegment {
            /** RGB (0 to 1.0) colors of this beam segment */
            private final float[] colors;
            public static int height;

            public BeamSegment(float[] colorsIn) {
                this.colors = colorsIn;
                this.height = 1;
            }

            protected void incrementHeight() {
                this.height += 1;
            }

            /**
             * Returns RGB (0 to 1.0) colors of this beam segment
             */
            public float[] getColors() {
                return this.colors;
            }

            @SideOnly(Side.CLIENT)
            public int getHeight() {
                return this.height;
            }
        }


}
