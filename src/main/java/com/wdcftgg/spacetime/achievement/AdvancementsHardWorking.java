package com.wdcftgg.spacetime.achievement;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/1 20:17
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class AdvancementsHardWorking implements ICriterionTrigger<AdvancementsHardWorking.Instance>{

        public static final ResourceLocation ID = new ResourceLocation("spacetime", "hard_working_leads_to_miracle");
//        public static final ResourceLocation ID = TwilightForestMod.prefix("make_tf_portal");
        private final Map<PlayerAdvancements, AdvancementsHardWorking.Listeners> listeners = Maps.newHashMap();

        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<AdvancementsHardWorking.Instance> listener) {
            AdvancementsHardWorking.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);
            listeners.add(listener);
        }

        @Override
        public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<AdvancementsHardWorking.Instance> listener) {
            AdvancementsHardWorking.Listeners listeners = this.listeners.get(playerAdvancementsIn);
            if (listeners != null) {
                listeners.remove(listener);
                if (listeners.isEmpty()) {
                    this.listeners.remove(playerAdvancementsIn);
                }
            }
        }

        @Override
        public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
            this.listeners.remove(playerAdvancementsIn);
        }

        @Override
        public AdvancementsHardWorking.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
            return new AdvancementsHardWorking.Instance();
        }

        public void trigger(EntityPlayerMP player) {
            AdvancementsHardWorking.Listeners listeners = this.listeners.get(player.getAdvancements());
            if (listeners != null) {
                listeners.trigger();
            }
        }

        public static class Instance extends AbstractCriterionInstance {

            public Instance() {
                super(AdvancementsHardWorking.ID);
            }
        }

        static class Listeners {

            private final PlayerAdvancements playerAdvancements;
            private final Set<ICriterionTrigger.Listener<AdvancementsHardWorking.Instance>> listeners = Sets.newHashSet();

            public Listeners(PlayerAdvancements playerAdvancementsIn) {
                this.playerAdvancements = playerAdvancementsIn;
            }

            public boolean isEmpty() {
                return this.listeners.isEmpty();
            }

            public void add(ICriterionTrigger.Listener<AdvancementsHardWorking.Instance> listener) {
                this.listeners.add(listener);
            }

            public void remove(ICriterionTrigger.Listener<AdvancementsHardWorking.Instance> listener) {
                this.listeners.remove(listener);
            }

            public void trigger() {
                for (ICriterionTrigger.Listener<AdvancementsHardWorking.Instance> listener : Lists.newArrayList(this.listeners)) {
                    listener.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }

