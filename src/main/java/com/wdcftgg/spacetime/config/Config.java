package com.wdcftgg.spacetime.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/28 12:58
 */

public class Config {

    public static int SWORDBLOCKINGPARTICLE = 20;
    public static double ROTATIONALSPEED = 30d;
    public static int GUIPOSX = 0;
    public static int GUIPOSY = 50;
    public static double TIMEMAXHEALTH = 150d;
    public static double TIMELASTLIFEMAXHEALTH = 300d;
    public static boolean ALTARAUTOMATE = false;
    public static int SPACEDIMID = 253;
    public static int SPACEPHASES2HIGH = 165;

    public Config() {
    }

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);
        String CATEGORY_SWORDCORE = "swordcore";
        String CATEGORY_TIMECORE = "timecore";
        String CATEGORY_BOSS = "boss";
        String CATEGORY_DIM = "world";

        try {
            config.load();
            SWORDBLOCKINGPARTICLE = config.get(CATEGORY_SWORDCORE, "SwordBlockingParticle", SWORDBLOCKINGPARTICLE, "The number of particles in a successful block").getInt();
            ROTATIONALSPEED = config.get(CATEGORY_TIMECORE, "RotationVelocityOfANormalMatrix", ROTATIONALSPEED, "Rotation velocity of a normal matrix(The Angle of rotation per tick)").getDouble();
            GUIPOSX = config.get(CATEGORY_TIMECORE, "GuiPosX", ROTATIONALSPEED, "The altar shows the gui orientation of the energy requirements on the x axis").getInt();
            GUIPOSY = config.get(CATEGORY_TIMECORE, "GuiPosY", ROTATIONALSPEED, "The altar shows the gui orientation of the energy requirements on the y axis").getInt();
            ALTARAUTOMATE = config.get(CATEGORY_TIMECORE, "AltarAutomate", ALTARAUTOMATE, "Whether the altar can be automated").getBoolean();
            TIMEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeMaxHealth", TIMEMAXHEALTH, "Boss[time] every life health").getDouble();
            TIMELASTLIFEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeLastLifeMaxHealth", TIMELASTLIFEMAXHEALTH, "Boss[time] Last life health").getDouble();
            SPACEDIMID = config.get(CATEGORY_DIM, "SPACEDIMID", SPACEDIMID, "space Dimension id").getInt();
            SPACEPHASES2HIGH = config.get(CATEGORY_BOSS, "SPACEPHASES2HIGH", SPACEPHASES2HIGH, "players will be attacked when they reach this height").getInt();
            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }
}
