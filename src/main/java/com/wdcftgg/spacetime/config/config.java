package com.wdcftgg.spacetime.config;

import com.wdcftgg.spacetime.SpaceTime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/28 12:58
 */

public class config {

    public static int SWORDBLOCKINGPARTICLE = 20;

    public config() {
    }

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);
        String CATEGORY_SWORDCORE = "swordcore";

        try {
            config.load();
            SWORDBLOCKINGPARTICLE = config.get(CATEGORY_SWORDCORE, "SwordBlockingParticle", SWORDBLOCKINGPARTICLE, "The number of particles in a successful block").getInt(SWORDBLOCKINGPARTICLE);
            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }
}
