package com.wdcftgg.spacetime.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/28 12:58
 */

public class config {

    public static int SWORDBLOCKINGPARTICLE = 20;
    public static double ROTATIONALSPEED = 1d;

    public config() {
    }

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);
        String CATEGORY_SWORDCORE = "swordcore";
        String CATEGORY_TIMECORE = "timecore";

        try {
            config.load();
            SWORDBLOCKINGPARTICLE = config.get(CATEGORY_SWORDCORE, "SwordBlockingParticle", SWORDBLOCKINGPARTICLE, "The number of particles in a successful block").getInt(SWORDBLOCKINGPARTICLE);
            ROTATIONALSPEED = config.get(CATEGORY_TIMECORE, "RotationVelocityOfANormalMatrix", ROTATIONALSPEED, "Rotation velocity of a normal matrix(The Angle of rotation per tick)").getDouble(ROTATIONALSPEED);
            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }
}
