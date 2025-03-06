package net.yuhi.better_progression.mixin.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract AttributeInstance getAttribute(Attribute attribute);

    @Shadow public abstract void heal(float pHealAmount);

    /*@Inject(method = "<init>", at = @At("RETURN"))
    private void onLivingEntityInit(EntityType<? extends LivingEntity> entityType, Level level, CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayer player) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);

            if (maxHealth != null) {
                System.out.println("PLR " + player.getHealth());
                *//*maxHealth.setBaseValue(6);
                player.setHealth(6);*//*
            }
        }
    }*/
    
    /*@Inject(method = "getHealth", at = @At("RETURN"), cancellable = true)
    private void onGetHealth(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof ServerPlayer player) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null) {
                if (cir.getReturnValue() <= player.getMaxHealth()) return;
                player.setHealth(player.getMaxHealth());
                cir.setReturnValue(player.getMaxHealth());
            }
        }
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void onGetMaxHealth(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof ServerPlayer player) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth != null) {
                int additionalHealth = 0;
                try {
                    for (var s : player.getArmorSlots()) {
                        if (s.isEmpty()) continue;
                        if (!(s.getItem() instanceof ArmorItem armorItem)) continue;
                        if (!(armorItem.getMaterial() instanceof BetterArmorMaterial material)) continue;
                        additionalHealth += material.getLifeMod(armorItem.getType());
                    }

                    cir.setReturnValue((float)(6 + additionalHealth));
                } catch (Exception ignored) {}
            }
        }
    }*/
}