package com.wdcftgg.spacetime.achievement;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 20:15
 */

import net.minecraft.advancements.CriteriaTriggers;

public class ModAdvancements {
    public static final AdvancementsHardWorking HARD_WORKING = CriteriaTriggers.register(new AdvancementsHardWorking());

    public static void init() {}
}
