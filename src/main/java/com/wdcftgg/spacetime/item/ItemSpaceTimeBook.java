package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.BookRegistry;
import com.wdcftgg.spacetime.gui.book.STBookGui;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSpaceTimeBook extends BookBase implements IHasModel {

    private int pageNum = 1;

    public ItemSpaceTimeBook()
    {
        super(BookRegistry.spaceTimeBook);
        setTranslationKey("spacetimebook");
        setRegistryName("spacetimebook");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        STItems.ITEMS.add(this);

    }

    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        list.add(I18n.format("spacetime.spacetimebook.tooltip"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if(!player.isSneaking())
        {

            System.out.println("AllPageNumList--" + BookRegistry.spaceTimeBook.pages);
            System.out.println("AllPageNum--" + BookRegistry.spaceTimeBook.getAllPageNum());
            System.out.println("pageNum--" + pageNum);


            Minecraft.getMinecraft().displayGuiScreen(new STBookGui(this, player.getHeldItem(hand)));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
