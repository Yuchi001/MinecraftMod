package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public class PickAxeItemMixin implements LayerableItem{
}
