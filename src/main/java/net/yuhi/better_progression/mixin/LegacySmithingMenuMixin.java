package net.yuhi.better_progression.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.LegacySmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import net.yuhi.better_progression.mixin.accessor.ItemCombinerMenuAccessor;
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
    
    @Inject(method = "onTake", at = @At("TAIL"), cancellable = true)
    public void onTake(Player pPlayer, ItemStack pStack, CallbackInfo ci) {
        var inputSlots = ((ItemCombinerMenuAccessor) this).getInputSlots();
        var additionalSlotItem = inputSlots.getItem(1);
        var layerItems = LayerableItem.getLayerItems();
        
        if (layerItems.stream().noneMatch(additionalSlotItem::is)) return;
        
        for (var i =0; i < 64; i++) {
            shrinkStackInSlot(1);
        }
    }
}
