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

    private static final String CATEGORY_POTION = "potion";
    private static final String CATEGORY_TIMECORE = "timecore";
    private static final String CATEGORY_BOSS = "boss";
    private static final String CATEGORY_DIM = "world";

    public static boolean LossSpatialSense = true;
    public static double ROTATIONALSPEED = 30d;
    public static int GUIPOSX = 0;
    public static int GUIPOSY = 50;
    public static double TIMEMAXHEALTH = 150d;
    public static double TIMELASTLIFEMAXHEALTH = 300d;
    public static boolean SAYINBAR = true;
    public static boolean ALTARAUTOMATE = false;
    public static int SPACEDDIM = 253;
    public static int BLACKHOLEDIM = 254;
    public static int SPACEPHASES2HIGH = 165;
    public static int ABSORBNUM = 5184;
    public static int RENDERBLACKHOLEMODE = 2;
    public static int SUMMONMOBCOOLING = 100;
    public static double MOBMOVESPEED = 0.5D;
    public static String[] CANSUMMONMOB = new String[]{
        "minecraft:zombie",
        "minecraft:skeleton",
        "minecraft:witch",
        "minecraft:creeper",
        "minecraft:vindication_illager",
    };

    public Config() {
    }

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);


        try {
            config.load();
            dimInit(config);
            bossInit(config);
            timeInit(config);
            potionInit(config);
            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }


    private static void dimInit(Configuration config) {
        RENDERBLACKHOLEMODE = config.get(CATEGORY_DIM, "RenderBlackHoleMode", RENDERBLACKHOLEMODE, "How black holes are rendered in the black hole dimension(0 is High, 1 is Lower, 2 is Minimum, 3 is not rander)").setMinValue(0).setMaxValue(3).getInt();
        SPACEDDIM = config.get(CATEGORY_DIM, "SpaceDim", SPACEDDIM, "space Dimension id").getInt();
    }

    private static void bossInit(Configuration config) {
        TIMEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeMaxHealth", TIMEMAXHEALTH, "Boss[time] every life health").getDouble();
        TIMELASTLIFEMAXHEALTH = config.get(CATEGORY_BOSS, "TimeLastLifeMaxHealth", TIMELASTLIFEMAXHEALTH, "Boss[time] Last life health").getDouble();
        SPACEPHASES2HIGH = config.get(CATEGORY_BOSS, "SpacePhases2High", SPACEPHASES2HIGH, "players will be attacked when they reach this height").getInt();
        ABSORBNUM = config.get(CATEGORY_BOSS, "AbsorbNum", ABSORBNUM, "the number of make the black hole explode").getInt();
        CANSUMMONMOB = config.get(CATEGORY_BOSS, "CanSummonMob", CANSUMMONMOB, "Space can summon out the monster").getStringList();
        SUMMONMOBCOOLING = config.get(CATEGORY_BOSS, "SummonMobCooling", SUMMONMOBCOOLING, "Every time a creature is summoned to cool down").setMinValue(0).setMaxValue(99999).getInt();
        MOBMOVESPEED = config.get(CATEGORY_BOSS, "MobMoveSpeed", MOBMOVESPEED, "The speed at which the creature moves towards the player after it has just emerged from the portal").setMinValue(0).setMaxValue(99999).getDouble();
    }

    private static void timeInit(Configuration config) {
        ROTATIONALSPEED = config.get(CATEGORY_TIMECORE, "RotationVelocityOfANormalMatrix", ROTATIONALSPEED, "Rotation velocity of a normal matrix(The Angle of rotation per tick)").getDouble();
        GUIPOSX = config.get(CATEGORY_TIMECORE, "GuiPosX", GUIPOSX, "The altar shows the gui orientation of the energy requirements on the x axis").getInt();
        GUIPOSY = config.get(CATEGORY_TIMECORE, "GuiPosY", GUIPOSY, "The altar shows the gui orientation of the energy requirements on the y axis").getInt();
        ALTARAUTOMATE = config.get(CATEGORY_TIMECORE, "AltarAutomate", ALTARAUTOMATE, "Whether the altar can be automated").getBoolean();
    }

    private static void potionInit(Configuration config) {
        SAYINBAR = config.get(CATEGORY_POTION, "SpaceStopSayInBar", SAYINBAR, "if true, a prompt will appear in the action bar when the space is fixed").getBoolean();
        LossSpatialSense = config.get(CATEGORY_POTION, "LossSpatialSenseRender", LossSpatialSense, "if true, the gui will be rendered").getBoolean();
    }
}
