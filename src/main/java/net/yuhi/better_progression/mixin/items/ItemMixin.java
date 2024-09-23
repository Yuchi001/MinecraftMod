package net.yuhi.better_progression.mixin.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import net.yuhi.better_progression.tag.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

import static net.yuhi.better_progression.utils.ItemStackUtils.applyStackSizeToItem;


@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
    private void increaseStackLimit(CallbackInfoReturnable<Integer> returnInfo)
    {
        @SuppressWarnings("ConstantConditions") var itemstack = ((Item) (Object) this).getDefaultInstance();
        var item = itemstack.getItem();

        applyStackSizeToItem(returnInfo, itemstack, item);
    }
    
    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    public void getRarity(ItemStack pStack, CallbackInfoReturnable<Rarity> cir) {
        if (ForgeRegistries.ITEMS.getKey(pStack.getItem()).getPath().toLowerCase().contains("totem_of_undying")) {
            cir.setReturnValue(Rarity.RARE);
            cir.cancel();
        }
    }
    
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void injectAppendHoverTextHead(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        var item = (Object)stack.getItem();
        
        if (item instanceof LayerableItem) {
            var components = LayerableItem.getLayeredDescription(stack);
            tooltip.addAll(components);
        }
    }
    
    @Inject(method = "inventoryTick", at = @At("HEAD"))
    public void onArmorTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected, CallbackInfo ci) {
        if (!(pEntity instanceof Player player)) return;
        
        betterProgression$onNetheriteArmorTick(player);
        betterProgression$onEnderiteArmorTick(player);
        
        int heavy_armor_weight = 0;
        
        for (var slot : player.getArmorSlots()) {
            if (!slot.is(ModTags.Items.HEAVY_ARMOR_TAG)) continue;
            heavy_armor_weight += 1;
        }
        
        if (heavy_armor_weight == 0) return;
        
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, (heavy_armor_weight - 1) / 2, false, false, true));
    }
    
    @Unique
    public void betterProgression$onNetheriteArmorTick(Player player) {
        int netherite_armor_count = 0;

        for (var slot : player.getArmorSlots()) {
            if (!slot.is(ModTags.Items.NETHERITE_ARMOR_TAG)) continue;
            netherite_armor_count += 1;
        }

        if (netherite_armor_count == 0) return;

        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10, (netherite_armor_count - 1) / 2, false, false, true));
    }

    @Unique
    public void betterProgression$onEnderiteArmorTick(Player player) {
        int enderite_armor_count = 0;

        for (var slot : player.getArmorSlots()) {
            if (!slot.is(ModTags.Items.ENDERITE_ARMOR_TAG)) continue;
            enderite_armor_count += 1;
        }

        if (enderite_armor_count == 0) return;

        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, (enderite_armor_count - 1) / 2, false, false, true));
    }
}
