package com.wdcftgg.spacetime.util;

import com.wdcftgg.spacetime.capability.ISpaceFrozen;
import com.wdcftgg.spacetime.init.CapabilityLoader;
import com.wdcftgg.spacetime.network.MessageSpaceFrozen;
import com.wdcftgg.spacetime.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class SpaceFrozenHelper {

    public static void addSpaceFrozen(Entity entity, int num) {
        setSpaceFrozen(entity, getSpaceFrozen(entity) + num);
        PacketHandler.INSTANCE.sendToAllAround(new MessageSpaceFrozen(entity.getEntityId(), SpaceFrozenHelper.getSpaceFrozen(entity)), new NetworkRegistry.TargetPoint(entity.world.provider.getDimension(), (double)entity.getPosition().getX(), (double)entity.getPosition().getY(), (double)entity.getPosition().getZ(), 256.0D));
    }

    public static void setSpaceFrozen(Entity entity, int num) {
        if (entity.hasCapability(CapabilityLoader.spaceFrozen, null)) {
            ISpaceFrozen spaceFrozen = entity.getCapability(CapabilityLoader.spaceFrozen, null);
            if (spaceFrozen != null) {
                spaceFrozen.setSpaceFrozen(num);
            }
        }
    }

    public static void removeSpaceFrozen(Entity entity, int num) {
        if (num < 0) {
            addSpaceFrozen(entity, getSpaceFrozen(entity) + Math.abs(num));
        }
        if (getSpaceFrozen(entity) >= num) {
            setSpaceFrozen(entity, getSpaceFrozen(entity) - num);
        } else {
            setSpaceFrozen(entity, 0);
            if (getIsSpaceFrozen(entity)) setIsSpaceFrozen(entity, false);
        }
    }

    public static int getSpaceFrozen(Entity entity) {
        if (entity.hasCapability(CapabilityLoader.spaceFrozen, null)) {
            ISpaceFrozen spaceFrozen = entity.getCapability(CapabilityLoader.spaceFrozen, null);
            if (spaceFrozen != null) {
                return spaceFrozen.getSpaceFrozen();
            }
        }
        return 0;
    }

    public static void setIsSpaceFrozen(Entity entity, boolean is) {
        if (entity.hasCapability(CapabilityLoader.spaceFrozen, null)) {
            ISpaceFrozen spaceFrozen = entity.getCapability(CapabilityLoader.spaceFrozen, null);
            if (spaceFrozen != null) {
                spaceFrozen.setIsSpaceFrozen(is);
            }
        }
    }

    public static boolean getIsSpaceFrozen(Entity entity) {
        if (entity.hasCapability(CapabilityLoader.spaceFrozen, null)) {
            ISpaceFrozen spaceFrozen = entity.getCapability(CapabilityLoader.spaceFrozen, null);
            if (spaceFrozen != null) {
                return spaceFrozen.getIsSpaceFrozen();
            }
        }
        return false;
    }


}
