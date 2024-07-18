package net.yuhi.better_progression.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeType<T extends Recipe<?>> {
    public static class BetterSmeltingRecipeType implements RecipeType<BetterSmeltingRecipe> {
        public static final BetterSmeltingRecipeType BETTER_SMELTING = new BetterSmeltingRecipeType();
        public static final String ID = "smelting";
    }
}
