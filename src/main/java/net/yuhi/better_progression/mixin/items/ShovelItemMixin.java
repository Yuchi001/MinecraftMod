package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShovelItem.class)
public class ShovelItemMixin implements LayerableItem{
}
