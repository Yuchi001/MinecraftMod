package net.yuhi.better_progression.mixin;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Unique
    private static List<Enchantment> betterProgression$getAllowedConflicts() {
        return List.of(
                Enchantments.PROJECTILE_PROTECTION,
                Enchantments.FIRE_PROTECTION,
                Enchantments.BLAST_PROTECTION,
                Enchantments.FALL_PROTECTION,
                Enchantments.FALL_PROTECTION,
                Enchantments.SHARPNESS,
                Enchantments.SMITE,
                Enchantments.BANE_OF_ARTHROPODS,
                Enchantments.MULTISHOT,
                Enchantments.INFINITY_ARROWS,
                Enchantments.MENDING
        );
    }
    
    @Inject(method = "isCompatibleWith", at = @At("HEAD"), cancellable = true)
    private void allowConflictingEnchantments(Enchantment pOther, CallbackInfoReturnable<Boolean> cir) {
        var current = (Enchantment) (Object) this;
        if(betterProgression$getAllowedConflicts().contains(pOther) && current != pOther) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
