package net.yuhi.better_progression.mixin;

import net.minecraft.world.item.Tiers;
import net.yuhi.better_progression.mixin.accessor.TiersAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(Tiers.class)
public class TiersMixin {
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyEnumValues(String pLevel, int pUses, int pSpeed, int pDamage, float pEnchantmentValue, float pRepairIngredient, int par7, Supplier par8, CallbackInfo ci) {
        var accessor = (TiersAccessor)this;
        
        switch (pLevel) {
            case "WOOD" -> {
                accessor.setSpeed(1f);
            }
            case "STONE" -> {
                accessor.setSpeed(1.5f);
            }
            case "DIAMOND" -> {
                accessor.setSpeed(3.0f);
                accessor.setUses(131);
            }
        }
    }
}
