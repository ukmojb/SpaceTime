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
    public static double TIMEMAXHEALTH = 150d;
    public static double TIMELASTLIFEMAXHEALTH = 300d;
    public static boolean ALTARAUTOMATE = true;

    public config() {
    }

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);
        String CATEGORY_SWORDCORE = "swordcore";
        String CATEGORY_TIMECORE = "timecore";
        String CATEGORY_BOSS = "boss";

        try {
            config.load();
            SWORDBLOCKINGPARTICLE = config.get(CATEGORY_SWORDCORE, "SwordBlockingParticle", SWORDBLOCKINGPARTICLE, "The number of particles in a successful block").getInt();
            ROTATIONALSPEED = config.get(CATEGORY_TIMECORE, "RotationVelocityOfANormalMatrix", ROTATIONALSPEED, "Rotation velocity of a normal matrix(The Angle of rotation per tick)").getDouble();
            ALTARAUTOMATE = config.get(CATEGORY_TIMECORE, "AltarAutomate", ALTARAUTOMATE, "Whether the altar can be automated").getBoolean();
            TIMEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeMaxHealth", TIMEMAXHEALTH, "Boss[time] every life health").getDouble();
            TIMELASTLIFEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeLastLifeMaxHealth", TIMELASTLIFEMAXHEALTH, "Boss[time] Last life health").getDouble();
            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }
}
