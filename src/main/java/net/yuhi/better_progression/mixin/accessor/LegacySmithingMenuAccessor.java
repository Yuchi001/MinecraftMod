package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.world.inventory.LegacySmithingMenu;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("removal")
@Mixin(LegacySmithingMenu.class)
public interface LegacySmithingMenuAccessor {
    @Accessor("selectedRecipe")
    public LegacyUpgradeRecipe getSelectedRecipe();
}
