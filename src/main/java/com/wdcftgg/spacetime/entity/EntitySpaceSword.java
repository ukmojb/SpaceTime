package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.config.Config;
import com.wdcftgg.spacetime.network.MessageTimeBack;
import com.wdcftgg.spacetime.network.MessageTimeParticle;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2024/2/9 14:21
 */
public class EntitySpaceSword extends EntityLiving implements IAnimatable {

    public AnimationFactory factory = new AnimationFactory(this);

    public EntitySpaceSword(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 2.5F);
    }

    public void onDeath(DamageSource cause)
    {
        if (!world.isRemote && !CommonProxy.spacelist.isEmpty()){
            for (int num : CommonProxy.spacelist) {
                EntitySpace entitySpace = (EntitySpace) world.getEntityByID(num);
                if (entitySpace != null) {
                    entitySpace.attackEntityFrom(DamageSource.OUT_OF_WORLD, (float) (entitySpace.getMaxHealth() * 0.05 + 5));
                }
            }
        }
    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.sword", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}