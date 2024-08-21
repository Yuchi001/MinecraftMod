package net.yuhi.better_progression.mixin.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import net.yuhi.better_progression.tag.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Mixin(Item.class)
public class ItemMixin {
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
        
        int heavy_armor_weight = 0;
        
        for (var slot : player.getArmorSlots()) {
            System.out.println(slot.is(ModTags.Items.HEAVY_ARMOR_TAG));
            if (!slot.is(ModTags.Items.HEAVY_ARMOR_TAG)) continue;
            heavy_armor_weight += 1;
        }
        
        if (heavy_armor_weight == 0) return;
        
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, (heavy_armor_weight - 1) / 2, false, false, true));
    }
}
