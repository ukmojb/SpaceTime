package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.item.ModItems;
import com.wdcftgg.spacetime.util.TimeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 5:15
 */
@Mod.EventBusSubscriber
public class EventExplosion {
    @SubscribeEvent
    public static void onExplosionEvent(ExplosionEvent.Detonate event) {
        for (Entity entity : event.getAffectedEntities()){
            if (entity instanceof EntityItem) {
                EntityItem item = (EntityItem) entity;
                if (item.getItem().getItem() == ModItems.TIMEPOLYMER) {
                    ItemStack spownstack = new ItemStack(ModItems.UNSTABLETIMEPOLYMER, item.getItem().getCount());
                    TimeHelper.addTime(spownstack, TimeHelper.getTime(item.getItem()), 200000);
                    EntityItem spawnitem = new EntityItem(item.world, item.posX, item.posY, item.posZ, spownstack);
                    item.world.spawnEntity(spawnitem);
                }
            }
        }
    }
}
