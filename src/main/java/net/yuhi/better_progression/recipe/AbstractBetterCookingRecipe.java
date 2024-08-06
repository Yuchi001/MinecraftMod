package net.yuhi.better_progression.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBetterCookingRecipe extends AbstractCookingRecipe {
    protected final Ingredient secondIngredient;
    protected final int firstIngredientCount;
    protected final int secondIngredientCount;

    public AbstractBetterCookingRecipe(RecipeType<?> pType, ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient secondIngredient, ItemStack pResult, float pExperience, int pCookingTime, int firstIngredientCount, int secondIngredientCount) {
        super(pType, pId, pGroup, pCategory, pIngredient, pResult, pExperience, pCookingTime);
        this.secondIngredient = secondIngredient;
        this.firstIngredientCount = firstIngredientCount;
        this.secondIngredientCount = secondIngredientCount;
    }

    @Override
    public boolean matches(@NotNull Container pInv, @NotNull Level pLevel) {
        if (pInv.isEmpty()) return false;

        var firstSlotEmpty = pInv.getItem(0).isEmpty();
        var secondSlotEmpty = pInv.getItem(1).isEmpty();

        var ingredientFirstItemMatch = false;
        var secondIngredientFirstItemMatch = false;
        if (!firstSlotEmpty) {
            ingredientFirstItemMatch = this.ingredient.test(pInv.getItem(0))
                    && pInv.getItem(0).getCount() >= this.firstIngredientCount;
            secondIngredientFirstItemMatch = this.secondIngredient.test(pInv.getItem(0))
                    && pInv.getItem(0).getCount() >= this.secondIngredientCount;
        }

        var ingredientSecondItemMatch = false;
        var secondIngredientSecondItemMatch = false;
        if (!secondSlotEmpty) {
            ingredientSecondItemMatch = this.ingredient.test(pInv.getItem(1))
                    && pInv.getItem(1).getCount() >= this.firstIngredientCount;
            secondIngredientSecondItemMatch = this.secondIngredient.test(pInv.getItem(1))
                    && pInv.getItem(1).getCount() >= this.secondIngredientCount;
        }

        var isTheSameItem = pInv.getItem(0).sameItem(pInv.getItem(1));
        var hasEmptySlot = firstSlotEmpty || secondSlotEmpty;

        var defaultMatch = (ingredientFirstItemMatch || ingredientSecondItemMatch || secondIngredientFirstItemMatch || secondIngredientSecondItemMatch) && (isTheSameItem || hasEmptySlot);
        var specialMatch = (ingredientFirstItemMatch && secondIngredientSecondItemMatch) || (ingredientSecondItemMatch && secondIngredientFirstItemMatch);

        return this.isSpecial() ? specialMatch : defaultMatch;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        nonnulllist.add(this.secondIngredient);
        return nonnulllist;
    }
    
    public int getIngredientCount(int slot) {
        return slot == 0 ? this.firstIngredientCount : this.secondIngredientCount;
    }

    public ItemStack getResultItem() {
        return this.result;
    }
}
