package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.SwordItem;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoeItem.class)
public class HoeItemMixin implements LayerableItem{
}
