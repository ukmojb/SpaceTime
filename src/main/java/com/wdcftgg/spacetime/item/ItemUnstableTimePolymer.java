package com.wdcftgg.spacetime.item;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.entity.EntityUnstableTimePolymer;
import com.wdcftgg.spacetime.init.ModCreativeTab;
import com.wdcftgg.spacetime.util.IHasModel;
import com.wdcftgg.spacetime.util.ITime;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/4/30 15:24
 */
public class ItemUnstableTimePolymer extends Item implements ITime, IHasModel {
    public ItemUnstableTimePolymer()
    {
        setUnlocalizedName("unstable_time_polymer");
        setRegistryName("unstable_time_polymer");
        setCreativeTab(ModCreativeTab.SpaceTimeTab);

        ModItems.ITEMS.add(this);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        int time = getTime(is);
        int chance = 80000;
        if (time > 0) {
            chance = chance / time;
            if (chance < 1) {
                chance = 1;
            }
        }
        float percentagechance = (float) 1 / chance * 100;
        if (time <= 0){
            list.add("Contain Time: §eNo Space Energy");
        } else {
            list.add("Contain Time: §e" + time);
        }
        if (time > 0) {
            list.add("Chance of split Time Crack: " + percentagechance + "%");
        }
    }

    @Override
    public int getMaxTime()
    {
        return 200000;
    }

    @Override
    public int addTime(ItemStack is, int time)
    {
        return TimeHelper.addTime(is, time, getMaxTime());
    }

    @Override
    public int getTime(ItemStack is)
    {
        return TimeHelper.getTime(is);
    }

    @Override
    public int getTimeFromInventory(IInventory inventory)
    {
        return TimeHelper.getTimeFromInventory(inventory);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        // 播放雪球被抛出去的声音——这个会在后面的章节中详细解释
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        // world.isRemote 用于判断是服务端还是客户端，这里我们要做的逻辑显然应该只在服务端执行
        // 这个字段的细节在第七章有详细阐述。
        if (!world.isRemote) {
            //生成雪球实体——关于实体的内容在第八章会详细解释，我们现在丢雪球就好了
            EntityUnstableTimePolymer unstabletimepolymer = new EntityUnstableTimePolymer(world, player);
            unstabletimepolymer.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(unstabletimepolymer);
        }
        // 互动成功，返回EnumActionResult.SUCCESS，item 是互动结束以后的 item
        // 因为这是个可以无限丢的雪球，所以这里数量没有减去 1。减去 1 的话丢出去就会少一个。
        // item.shrink(1); // 数量 - 1
        // 自然地，减去 2 的话丢出去就会少两个。
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
    }


    @Override
    public void registerModels()
    {
        SpaceTime.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public Item getItemChangeTo()
    {
        return ModItems.UNSTABLETIMEPOLYMER;
    }
}
