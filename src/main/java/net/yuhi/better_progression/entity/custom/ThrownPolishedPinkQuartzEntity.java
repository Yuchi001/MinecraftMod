package net.yuhi.better_progression.entity.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.yuhi.better_progression.effect.ModEffects;
import net.yuhi.better_progression.entity.ModEntityTypes;
import net.yuhi.better_progression.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;

public class ThrownPolishedPinkQuartzEntity extends ThrowableItemProjectile {
    public ThrownPolishedPinkQuartzEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    
    public ThrownPolishedPinkQuartzEntity(Level pLevel) {
        super(ModEntityTypes.POLISHED_PINK_QUARTZ.get(), pLevel);
    }

    public ThrownPolishedPinkQuartzEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntityTypes.POLISHED_PINK_QUARTZ.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POLISHED_PINK_QUARTZ.get();
    }

    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.applySplash(pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)pResult).getEntity() : null);

            this.level.levelEvent(2007, this.blockPosition(), 14725307);
            this.discard();
        }
    }

    private void applySplash(@Nullable Entity pTarget) {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
        if (!list.isEmpty()) {
            for(LivingEntity livingentity : list) {
                if (livingentity.isAffectedByPotions()) {
                    double d0 = this.distanceToSqr(livingentity);
                    if (d0 < 16.0D) {
                        double d1;
                        if (livingentity == pTarget) {
                            d1 = 1.0D;
                        } else {
                            d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                        }

                        MobEffect mobeffect = ModEffects.SMALL_HEAL.get();
                        mobeffect.applyInstantenousEffect(this, this.getOwner(), livingentity, 1, d1);
                    }
                }
            }
        }

    }
}
