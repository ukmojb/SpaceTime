package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.proxy.CommonProxy;
import com.wdcftgg.spacetime.proxy.ServerProxy;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

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

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10D);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if (!world.isRemote && !ServerProxy.spacelist.isEmpty()){
            for (int num : ServerProxy.spacelist) {
                EntitySpace entitySpace = (EntitySpace) world.getEntityByID(num);
                if (entitySpace != null) {
                    entitySpace.attackEntityFrom(DamageSource.OUT_OF_WORLD, (float) (entitySpace.getMaxHealth() * 0.05));
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
