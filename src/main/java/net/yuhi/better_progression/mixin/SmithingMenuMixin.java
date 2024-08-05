package net.yuhi.better_progression.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin {
    
    @Inject(method = "onTake", at = @At("TAIL"), cancellable = true)
    public void onTake(Player pPlayer, ItemStack pStack, CallbackInfo ci) {
        System.out.println("DUPA");
    }
    
    @Inject(method = "createResult", at = @At("HEAD"))
    public void createResult(CallbackInfo ci) {
        System.out.println("DUPA");
    }
}
