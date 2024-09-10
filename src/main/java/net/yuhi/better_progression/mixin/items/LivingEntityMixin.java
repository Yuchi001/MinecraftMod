package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract AttributeInstance getAttribute(Attribute attribute);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onLivingEntityInit(EntityType<? extends LivingEntity> entityType, Level level, CallbackInfo ci) {
        if ((Object) this instanceof Player) {
            var entity = (LivingEntity) (Object) this;
            AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);

            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(6.0);
                entity.setHealth(6.0F);
            }
        }
    }
}