package com.wdcftgg.spacetime.event;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.config.config;
import com.wdcftgg.spacetime.init.ModSounds;
import com.wdcftgg.spacetime.potion.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/20 2:46
 */

@Mod.EventBusSubscriber
public class EventSword {

    public EventSword() {
    }

    //剑封
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        EntityPlayer player = event.getEntityPlayer();
        if (SpaceTime.getCanStackBlock(event.getItemStack()) && player.getActivePotionEffect(ModPotions.swordcore) != null) {
            EnumAction action = player.getHeldItemOffhand().getItemUseAction();
            if (action == EnumAction.NONE || action == EnumAction.EAT && !player.canEat(false)) {
                player.setActiveHand(event.getHand());
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onItemUseStart(LivingEntityUseItemEvent.Start evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer && SpaceTime.getCanStackBlock(evt.getItem())) {
            evt.setDuration(72000);
        }

    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (SpaceTime.getCanStackBlock(player.getHeldItemMainhand()) && player.getActivePotionEffect(ModPotions.swordcore) != null && player.isHandActive() && event.getSource().getTrueSource() != null) {
                boolean pass = event.getSource().getTrueSource().getAdjustedHorizontalFacing().getOpposite() == player.getAdjustedHorizontalFacing();
                if (pass){
                    event.setAmount(event.getAmount() * 0.4f);
                } else {
                    if (!pass) return;
                    event.setAmount(event.getAmount() * 0.6f);
                }
            }
        }
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (player.getActivePotionEffect(ModPotions.swordcore) != null) {
                event.setAmount(event.getAmount() * 1.7f);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().world.isRemote) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (SpaceTime.getCanStackBlock(player.getHeldItemMainhand()) && player.getActivePotionEffect(ModPotions.swordcore) != null && player.isHandActive()) {
                if(event.getSource().getTrueSource() != null){
                    boolean pass = event.getSource().getTrueSource().getAdjustedHorizontalFacing().getOpposite() != player.getAdjustedHorizontalFacing();
                    if (pass) return;
                }
                if (player.rayTrace(1.5,2) != null){
                    Vec3d hitVec = player.rayTrace(1,1).hitVec;
                    Random r = new Random();
                    for (int i = 0; i <= config.SWORDBLOCKINGPARTICLE; i++) {
                        player.world.spawnParticle(EnumParticleTypes.END_ROD,hitVec.x,hitVec.y,hitVec.z,r.nextDouble() - 0.5,r.nextDouble()- 0.5,r.nextDouble()- 0.5, 10);
                    }
                }
                player.playSound(ModSounds.SWORDBLOCKING, 10, 1);
            }
        }
    }
}
