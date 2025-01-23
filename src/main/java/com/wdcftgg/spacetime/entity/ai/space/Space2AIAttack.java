package com.wdcftgg.spacetime.entity.ai.space;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.dimension.SpaceWorldProvider;
import com.wdcftgg.spacetime.entity.EntitySpace;
import com.wdcftgg.spacetime.entity.EntitySpace2;
import com.wdcftgg.spacetime.entity.EntitySpaceSword;
import com.wdcftgg.spacetime.potion.ModPotions;
import com.wdcftgg.spacetime.util.Tools;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class Space2AIAttack extends EntityAIBase {
    private World world;
    protected EntitySpace2 attacker;
    protected int attackTick;
    protected Boolean isCloseCombat;

    public static long attacktime = -1;


    public Space2AIAttack(EntitySpace2 creature) {
        this.attacker = creature;
        this.world = creature.world;
        this.setMutexBits(5);
    }

    public boolean shouldExecute() {
        return attacker.getMode() != "speak";
    }

    public boolean shouldContinueExecuting() {
        return attackTick > 0;
    }

    public void startExecuting() {

    }

    public void updateTask() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        attack(attacker, entitylivingbase);
        attacker.setAttackTick(this.attackTick);
    }

    protected void attack(EntitySpace2 entitySpace2, EntityLivingBase target) {


        if (!entitySpace2.getMode().equals("default")) return;
        if (target == null) {
            entitySpace2.setMode("default");
            return;
        }


        //连续的远程变招
        if (entitySpace2.getLastmode() == "attack1") {
            entitySpace2.setMode("attack4");
            entitySpace2.setLastmode("attack4");
            return;
        }
        if (entitySpace2.getLastmode() == "attack4") {
            entitySpace2.setMode("attack5");
            return;
        }


        //正常出招
        double distance = entitySpace2.getDistance(target);
        Random random = new Random();

        isCloseCombat = distance <= 8;

        if (isCloseCombat) {
            int rand = random.nextInt(20);
            if (rand <= 2) {
                entitySpace2.setMode("attack2");
            } else {
                entitySpace2.setMode("attack1");
            }
        } else {
            int rand = random.nextInt(20);
            if (rand == 0) {
//                entitySpace2.setMode("summon");
            } else if (rand >= 12) {
                entitySpace2.setMode("attack1");
            } else {
                entitySpace2.setMode("walk");
            }
        }
    }


}