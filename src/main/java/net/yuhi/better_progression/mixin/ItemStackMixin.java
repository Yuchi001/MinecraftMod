package net.yuhi.better_progression.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

import static net.yuhi.better_progression.utils.ItemStackUtils.applyStackSizeToItem;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Unique
    ItemStack stack = (ItemStack) (Object) this;

    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean isDamageableItem();
    
    @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
    private void increaseStackLimit(CallbackInfoReturnable<Integer> returnInfo)
    {
        @SuppressWarnings("ConstantConditions") var itemstack = ((ItemStack) (Object) this);
        var item = itemstack.getItem();

        applyStackSizeToItem(returnInfo, itemstack, item);
    }

    @Inject(method = "hurtAndBreak", at = @At("HEAD"), cancellable = true)
    public <T extends LivingEntity> void hurtAndBreak(int pAmount, T pEntity, Consumer<T> pOnBroken, CallbackInfo ci) {
        if (!(!pEntity.level.isClientSide && (!(pEntity instanceof Player) || !((Player)pEntity).getAbilities().instabuild))) {
            ci.cancel();
            return;
        }

        if(!isDamageableItem()) {
            ci.cancel();
            return;
        }
        
        var item = getItem();
        if (item instanceof LayerableItem) {
            if (LayerableItem.getTinCount(stack) <= 0) return;
            LayerableItem.onUse(stack);
            ci.cancel();

            if (LayerableItem.getTinCount(stack) > 0) return;
            
            pOnBroken.accept(pEntity);
        }
    }

    @Inject(method = "setRepairCost", at = @At("HEAD"), cancellable = true)
    private void resetRepairCost(int repairCost, CallbackInfo ci) {
        ci.cancel();
    }
}
