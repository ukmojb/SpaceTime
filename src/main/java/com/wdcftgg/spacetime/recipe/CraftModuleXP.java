package com.wdcftgg.spacetime.recipe;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.item.STItems;
import com.wdcftgg.spacetime.util.ISidedFunction;
import lumaceon.mods.clockworkphase.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class CraftModuleXP extends IForgeRegistryEntry.Impl<IRecipe> implements net.minecraftforge.common.crafting.IShapedRecipe {

    final static Field eventHandlerField = ObfuscationReflectionHelper.findField(InventoryCrafting.class, "field_70465_c");//

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {

        if (sameItemStack(inv.getStackInSlot(0), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(2), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(6), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(8), new ItemStack(Blocks.BOOKSHELF)) &&

            sameItemStack(inv.getStackInSlot(4), new ItemStack(ModItems.temporalCoreSedate)) &&
            sameItemStack(inv.getStackInSlot(1), new ItemStack(Blocks.EMERALD_BLOCK)) &&
            sameItemStack(inv.getStackInSlot(7), new ItemStack(Blocks.EMERALD_BLOCK)) &&
            sameItemStack(inv.getStackInSlot(3), new ItemStack(ModItems.ingotTemporal)) &&
            sameItemStack(inv.getStackInSlot(5), new ItemStack(ModItems.ingotTemporal))
        ) {
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {

        if (!isGoodForCrafting(inv)) return ItemStack.EMPTY;

        if (sameItemStack(inv.getStackInSlot(0), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(2), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(6), new ItemStack(Blocks.BOOKSHELF)) &&
            sameItemStack(inv.getStackInSlot(8), new ItemStack(Blocks.BOOKSHELF)) &&

            sameItemStack(inv.getStackInSlot(4), new ItemStack(ModItems.temporalCoreSedate)) &&
            sameItemStack(inv.getStackInSlot(1), new ItemStack(Blocks.EMERALD_BLOCK)) &&
            sameItemStack(inv.getStackInSlot(7), new ItemStack(Blocks.EMERALD_BLOCK)) &&
            sameItemStack(inv.getStackInSlot(3), new ItemStack(ModItems.ingotTemporal)) &&
            sameItemStack(inv.getStackInSlot(5), new ItemStack(ModItems.ingotTemporal))
        ) {
            return STItems.MODULEXP.getDefaultInstance();
        }

        return ItemStack.EMPTY;
    }

    public boolean isGoodForCrafting(InventoryCrafting inv) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return SpaceTime.proxy.apply(new ClientIsGoodForCrafting(inv), null);
        } else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                PlayerList manager = server.getPlayerList();
                if (manager != null) {
                    Container container = getContainer(inv);
                    if (container == null) return false;

                    EntityPlayerMP foundPlayer = null;

                    for (EntityPlayerMP playerMP : manager.getPlayers()) {
                        if (inv.isUsableByPlayer(playerMP) && getContainer(inv).equals(playerMP.openContainer) && getContainer(inv).getCanCraft(playerMP) && getContainer(inv).canInteractWith(playerMP)) {
                            if (foundPlayer != null) return false;
                            foundPlayer = playerMP;
                        }
                    }

                    if (foundPlayer != null) {
                        updatePlayer(foundPlayer);
                        return isValidForCrafting(foundPlayer);
                    }
                }
            }
        }
        return false;
    }

    public void updatePlayer(EntityPlayerMP player) {
//        player.connection.sendPacket(new SPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
    }


    @Override
    public boolean canFit(int width, int height) {
        return height >= 3;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public boolean isValidForCrafting(EntityPlayer player) {
        return player.experienceLevel >= 30;
    }

    private Container getContainer(InventoryCrafting inv) {
        if(eventHandlerField == null) return null;

        try {
            eventHandlerField.setAccessible(true);
            Object object = eventHandlerField.get(inv);
            Container container = object instanceof Container ? (Container) object : null;
            eventHandlerField.setAccessible(false);
            return container;
        }
        catch(IllegalArgumentException | IllegalAccessException e) {
            eventHandlerField.setAccessible(false);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getRecipeWidth() {
        return 3;
    }

    @Override
    public int getRecipeHeight() {
        return 3;
    }

    private class ClientIsGoodForCrafting implements ISidedFunction<Void, Boolean> {
        private final InventoryCrafting inv;

        public ClientIsGoodForCrafting(InventoryCrafting inv) {
            this.inv = inv;
        }

        @Override
        @SideOnly(Side.SERVER)
        public Boolean applyServer(Void input) {
            return false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Boolean applyClient(Void input) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            return player != null && isValidForCrafting(player);
        }
    }

    private boolean sameItemStack(ItemStack itemStack1, ItemStack itemStack2) {
        return (itemStack1.getItem() == itemStack2.getItem()) && (itemStack1.getMetadata() == itemStack2.getMetadata());
    }
}
