package net.yuhi.better_progression.recipe;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BetterSmeltingRecipe extends AbstractBetterCookingRecipe {
    public BetterSmeltingRecipe(RecipeType<?> pType, ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient pSecondIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(pType, pId, pGroup, pCategory, pIngredient, pSecondIngredient, pResult, pExperience, pCookingTime);
    }

    public BetterSmeltingRecipe( ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient pSecondIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(ModRecipeType.BetterSmeltingRecipeType.BETTER_SMELTING, pId, pGroup, pCategory, pIngredient, pSecondIngredient, pResult, pExperience, pCookingTime);
    }

    @Override
    public boolean isSpecial() {
        return !this.ingredient.isEmpty() && !this.secondIngredient.isEmpty();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FURNACE);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return  ModRecipes.SMELTING_SERIALIZER;
    }
}
