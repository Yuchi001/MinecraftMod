package net.yuhi.better_progression.mixin;

import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Llama.class)
public class LlamaMixin {

    @Inject(method = "getStrength", at = @At("RETURN"), cancellable = true)
    private void getStrength(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(5);
        cir.cancel();
    }
}
