package com.wdcftgg.spacetime.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISidedFunction<F, T> {
    @SideOnly(Side.SERVER)
    T applyServer(F var1);

    @SideOnly(Side.CLIENT)
    T applyClient(F var1);
}

