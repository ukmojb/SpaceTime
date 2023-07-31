package com.wdcftgg.spacetime.entity;

import com.wdcftgg.spacetime.item.STItems;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.construct.abstracts.ITimeSandSupplier;
import lumaceon.mods.clockworkphase.util.TimeSandHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 8:14
 */
public class EntityTimeCrack extends EntityLiving {

    private int neededtime = 604800;
    private Map<List<ItemStack>, ItemStack> recipe = new HashMap<List<ItemStack>, ItemStack>();
    private List<List<ItemStack>> inputList = new ArrayList<List<ItemStack>>();

    public  EntityTimeCrack(World worldIn)
    {
        super(worldIn);
        this.setSize(1.2F, 1.8F);
        this.setNoAI(true);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200000000.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);
        compound.setInteger("challengetime", neededtime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);
        neededtime = compound.getInteger("challengetime");
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (this.ticksExisted == 1) {
                initTimeCrackRecipe();
            }
            if (this.ticksExisted >= 35000) {
                world.addWeatherEffect(new EntityLightningBolt(world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), true));
                this.world.removeEntity(this);
            }
            if (neededtime == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), true));
                this.world.removeEntity(this);
                // 召唤空间裂缝
            }
            for (EntityLivingBase livingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition().east().north().down(), this.getPosition().west().south().up()))) {
                if (livingbase instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) livingbase;
                    if (TimeSandHelper.getTimeSandFromInventory(player.inventory) > 0) {
                        TimeSandHelper.removeTimeSandFromInventory(player.inventory, 100);
                    } else {
                        livingbase.attackEntityFrom(new DamageSource("TimeAttack").setDamageBypassesArmor(), 3);
                    }
                }
            }
            if (!recipe.isEmpty()) {
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.getPosition().down().west().south(), this.getPosition().up().east().north()));
                List<ItemStack> itemStacks = new ArrayList<ItemStack>();
                boolean pass = false;
                for (EntityItem item : items) {
                    itemStacks.add(item.getItem());
                }
                for (List<ItemStack> i : inputList) {
                    if (i.toString().contains(itemStacks.toString())) {
                        pass = true;
                    }
                    if (pass) {
                        for (EntityItem item : items) {
                            world.removeEntity(item);
                        }
                        EntityItem spawnitem = new EntityItem(world, this.posX, this.posY, this.posZ, recipe.get(i));
                        world.spawnEntity(spawnitem);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        // 604800 一星期时间
        if (player.world.isRemote) {
            ItemStack item = player.getHeldItem(hand);
            if(item.getItem() instanceof ITimeSandSupplier) {
                if ((neededtime - TimeSandHelper.getTimeSand(item)) >= 0) {
                    neededtime -= TimeSandHelper.getTimeSand(item);
                    TimeSandHelper.removeTimeSand(item, TimeSandHelper.getTimeSand(item));
                } else {
                    neededtime = 0;
                    TimeSandHelper.removeTimeSand(item, neededtime);
                }
            }
        }
        return true;
    }

    public void addTimeCrackRecipe(List<ItemStack> input, ItemStack output){
        inputList.add(input);
        recipe.put(input, output);
    }

    private void initTimeCrackRecipe(){
        addTimeCrackRecipe(newArray(ModItems.ingotTemporal.getDefaultInstance(), ModItems.brassIngot.getDefaultInstance()), STItems.TEMPORALBRASSINGOT.getDefaultInstance());
    }

    private List<ItemStack> newArray(ItemStack item){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        return input;
    }

    private List<ItemStack> newArray(ItemStack item, ItemStack item1){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        input.add(item1);
        return input;
    }

    private List<ItemStack> newArray(ItemStack item, ItemStack item1, ItemStack item2){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        input.add(item1);
        input.add(item2);
        return input;
    }

    private List<ItemStack> newArray(ItemStack item, ItemStack item1, ItemStack item2, ItemStack item3){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        input.add(item1);
        input.add(item2);
        input.add(item3);
        return input;
    }

    private List<ItemStack> newArray(ItemStack item, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack item4){
        List<ItemStack> input = new ArrayList<ItemStack>();
        input.add(item);
        input.add(item1);
        input.add(item2);
        input.add(item3);
        input.add(item4);
        return input;
    }
}
