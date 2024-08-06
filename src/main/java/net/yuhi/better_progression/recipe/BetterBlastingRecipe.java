package net.yuhi.better_progression.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BetterBlastingRecipe extends AbstractBetterCookingRecipe {
    public BetterBlastingRecipe(RecipeType<?> pType, ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient pSecondIngredient, ItemStack pResult, float pExperience, int pCookingTime, int firstIngredientCount, int secondIngredientCount) {
        super(pType, pId, pGroup, pCategory, pIngredient, pSecondIngredient, pResult, pExperience, pCookingTime, firstIngredientCount, secondIngredientCount);
    }

    public BetterBlastingRecipe( ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient pSecondIngredient, ItemStack pResult, float pExperience, int pCookingTime, int firstIngredientCount, int secondIngredientCount) {
        super(ModRecipeType.BetterBlastingRecipeType.BETTER_BLASTING, pId, pGroup, pCategory, pIngredient, pSecondIngredient, pResult, pExperience, pCookingTime, firstIngredientCount, secondIngredientCount);
    }

    @Override
    public boolean isSpecial() {
        return !this.ingredient.isEmpty() && !this.secondIngredient.isEmpty();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.BLAST_FURNACE);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.BLASTING_SERIALIZER;
    }
}
