package com.wdcftgg.spacetime.blocks.tileEntity;

import com.wdcftgg.spacetime.blocks.HourGlass.HourGlassBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
 * @create 2023/6/22 21:09
 */
public class ConcretizationHourGlassEntity extends TileEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().transitionLengthTicks = 0;
        if (world.getTileEntity(pos) instanceof  ConcretizationHourGlassEntity) {
            if (world.getBlockState(pos.up()).getBlock() instanceof HourGlassBase) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("concretizationhourglass.animation.deploy", true));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }


    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "concretizationhourglass", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        return super.writeToNBT(data);
    }

}