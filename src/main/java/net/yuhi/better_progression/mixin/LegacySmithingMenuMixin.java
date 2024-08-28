package net.yuhi.better_progression.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.LegacySmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.mixin.accessor.LegacySmithingMenuAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("removal")
@Mixin(LegacySmithingMenu.class)
public abstract class LegacySmithingMenuMixin {
    
    @Shadow
    protected abstract void shrinkStackInSlot(int pSlotIndex);
    
    @Inject(method = "onTake", at = @At("TAIL"))
    public void onTake(Player pPlayer, ItemStack pStack, CallbackInfo ci) {
        var selectedRecipe = ((LegacySmithingMenuAccessor) this).getSelectedRecipe();
        
        if (!selectedRecipe.isSpecial()) return;
        
        for (var i =0; i < 64; i++) {
            shrinkStackInSlot(1);
        }
    }
}
