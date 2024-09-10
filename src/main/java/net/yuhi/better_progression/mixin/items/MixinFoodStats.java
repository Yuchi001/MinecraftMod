package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class MixinFoodStats {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void disablePassiveHeal(Player player, CallbackInfo ci) {
        if (player.getFoodData().getFoodLevel() >= 18) {
            ci.cancel();
        }
    }
}