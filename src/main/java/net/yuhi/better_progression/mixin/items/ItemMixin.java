package net.yuhi.better_progression.mixin.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

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
}
