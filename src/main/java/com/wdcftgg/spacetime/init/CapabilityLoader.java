package com.wdcftgg.spacetime.init;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.capability.CapabilitySpaceFrozen;
import com.wdcftgg.spacetime.capability.ISpaceFrozen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityLoader {

    @CapabilityInject(ISpaceFrozen.class)
    public static Capability<ISpaceFrozen> spaceFrozen;

    public static void initCapability() {

        CapabilityManager.INSTANCE.register(ISpaceFrozen.class, new CapabilitySpaceFrozen.Storage(),
                CapabilitySpaceFrozen.Implementation.class);
    }

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            ICapabilitySerializable<NBTTagCompound> provider = new CapabilitySpaceFrozen.ProviderPlayer();
            event.addCapability(new ResourceLocation(SpaceTime.MODID + ":" + "spaceFrozen"), provider);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        Capability<ISpaceFrozen> capability = CapabilityLoader.spaceFrozen;
        Capability.IStorage<ISpaceFrozen> storage = capability.getStorage();

        if (event.getOriginal().hasCapability(capability, null) && event.getEntityPlayer().hasCapability(capability, null))
        {
            NBTBase nbt = storage.writeNBT(capability, event.getOriginal().getCapability(capability, null), null);
            storage.readNBT(capability, event.getEntityPlayer().getCapability(capability, null), null, nbt);
        }
    }
}
