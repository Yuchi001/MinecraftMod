package net.yuhi.better_progression.utils;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.tag.ModTags;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ItemStackUtils {
    public static void applyStackSizeToItem(CallbackInfoReturnable<Integer> returnInfo, ItemStack itemstack, Item item) {
        for (var tag : itemstack.getTags().toList()) {
            if (tag == ModTags.Items.STACKS_TO_16) returnInfo.setReturnValue(16);
            else if (tag == ModTags.Items.STACKS_TO_32) returnInfo.setReturnValue(32);
            else if (tag == ModTags.Items.STACKS_TO_64) returnInfo.setReturnValue(64);
            else if (tag == ModTags.Items.NO_STACKING) returnInfo.setReturnValue(1);
        }
    }
}
