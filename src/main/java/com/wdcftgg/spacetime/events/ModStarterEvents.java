package com.wdcftgg.spacetime.events;

import com.wdcftgg.spacetime.util.IDLNBT;
import com.wdcftgg.spacetime.util.NBTStrDef.IDLNBTDef;
import com.wdcftgg.spacetime.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModStarterEvents {
	  @SubscribeEvent
	  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		  EntityPlayer player = event.player;
		  //SpaceTime.Log(getPlyrIdlTagSafe(player).toString());
		  int lastStarterVer = IDLNBT.getPlayerIdeallandIntSafe(player, IDLNBTDef.STARTER_KIT_VERSION_TAG);
		  if(lastStarterVer < IDLNBTDef.CUR_STARTER_KIT_VERSION) {
//			  IDLNBT.setPlayerIdeallandTagSafe(player, STARTER_KIT_VERSION_TAG, CUR_STARTER_KIT_VERSION);
//
//			  ItemStack scry = new ItemStack(Items.QUARTZ);
//			  player.addItemStackToInventory(scry);
//
//			  if (player instanceof EntityPlayerMP) {
//				  CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP)player, "spacetime.msg.starter_kit_given", TextFormatting.AQUA);
//			  }
//			  SpaceTime.Log(String.format("Given starter items to player %s, ver %d", player.getDisplayNameString(), CUR_STARTER_KIT_VERSION));
		  }
	  }
	
}
