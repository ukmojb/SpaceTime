package com.wdcftgg.spacetime.capability;

import com.wdcftgg.spacetime.init.CapabilityLoader;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilitySpaceFrozen {
    public static class Storage implements Capability.IStorage<ISpaceFrozen>
    {
        private final String spaceFrozen = "spaceforzen";
        private final String isSpaceFrozen = "isspacefrozen";
        @Override
        public NBTBase writeNBT(Capability<ISpaceFrozen> cap, ISpaceFrozen instance, EnumFacing side) {

            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger(spaceFrozen, instance.getSpaceFrozen());
            nbt.setBoolean(isSpaceFrozen, instance.getIsSpaceFrozen());
            return nbt;
        }

        @Override
        public void readNBT(Capability<ISpaceFrozen> capability, ISpaceFrozen instance, EnumFacing side,
                            NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;

            instance.setSpaceFrozen(compound.getInteger(spaceFrozen));
            instance.setIsSpaceFrozen(compound.getBoolean(isSpaceFrozen));
        }
    }

    public static class Implementation implements ISpaceFrozen
    {
        private final String spaceFrozen = "spaceforzen";
        private final String isSpaceFrozen = "isspacefrozen";
        private int spaceFrozenNum = 0;
        private boolean isSpaceFrozenBool = false;

        public void addSpaceFrozen(int num) {
            spaceFrozenNum += num;
        }

        public void setSpaceFrozen(int num) {
            spaceFrozenNum = num;
        }

        public void removeSpaceFrozen(int num) {
            if (spaceFrozenNum >= num) {
                spaceFrozenNum -= num;
            } else {
                spaceFrozenNum = 0;
            }
        }

        public int getSpaceFrozen() {
            return spaceFrozenNum;
        }

        public void setIsSpaceFrozen(boolean is) {
            isSpaceFrozenBool = is;
        }

        public boolean getIsSpaceFrozen() {
            return isSpaceFrozenBool;
        }
    }

    public static class ProviderPlayer implements ICapabilitySerializable<NBTTagCompound>
    {
        private ISpaceFrozen spaceFrozen = new Implementation();
        private Capability.IStorage<ISpaceFrozen> storage = CapabilityLoader.spaceFrozen.getStorage();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.spaceFrozen.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (CapabilityLoader.spaceFrozen.equals(capability))
            {
                @SuppressWarnings("unchecked")
                T result = (T) spaceFrozen;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("spaceFrozen", storage.writeNBT(CapabilityLoader.spaceFrozen, spaceFrozen, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            storage.readNBT(CapabilityLoader.spaceFrozen, spaceFrozen, null, compound);
        }
    }
}
