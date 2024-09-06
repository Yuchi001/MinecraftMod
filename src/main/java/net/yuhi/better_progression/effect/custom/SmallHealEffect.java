package net.yuhi.better_progression.effect.custom;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class SmallHealEffect extends InstantenousMobEffect {
    private static final float EFFECT_VALUE = 1f;
    
    public SmallHealEffect() {
        super(MobEffectCategory.BENEFICIAL, 14725307);
    }

    @Override
    public void applyInstantenousEffect(@javax.annotation.Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        if (pLivingEntity instanceof LivingEntity) {
            float healAmount = EFFECT_VALUE * pAmplifier;
            if (pLivingEntity.getMobType() == MobType.UNDEAD) pLivingEntity.hurt(pSource.damageSources().magic(), healAmount * 2);
            else if (pLivingEntity instanceof Player) pLivingEntity.heal(healAmount);
        }
    }
}
