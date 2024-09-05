package net.yuhi.better_progression.recipe.utils;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.BetterProgression;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType.*;

public enum ESmeltingRecipeType {
    SMELT_BLOCK("_from_smelting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, output.get(), 0.1f, 200, RecipeSerializer.SMELTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get()))),
    SMELT_ORE_COMMON(List.of(
            new RecipeBuilderPair("_from_smelting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 0.7f, 200, RecipeSerializer.SMELTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get()))),
            new RecipeBuilderPair("_from_blasting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 0.7f, 100, RecipeSerializer.BLASTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get())))
    )),
    SMELT_ORE_UNCOMMON(List.of(
            new RecipeBuilderPair("_from_smelting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 1f, 200, RecipeSerializer.SMELTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get()))),
            new RecipeBuilderPair("_from_blasting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 1f, 100, RecipeSerializer.BLASTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get())))
    )),
    SMELT_ORE_RARE(List.of(
            new RecipeBuilderPair("_from_smelting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 2f, 200, RecipeSerializer.SMELTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get()))),
            new RecipeBuilderPair("_from_blasting_bp", (Supplier<ItemLike> input, Supplier<ItemLike> output) -> SimpleCookingRecipeBuilder.generic(Ingredient.of(input.get()), RecipeCategory.MISC, output.get(), 2f, 100, RecipeSerializer.BLASTING_RECIPE).group(GetGroup(output)).unlockedBy(getHasName(input.get()), has(input.get())))
    ));
    
    private final List<RecipeBuilderPair> recipeBuilderPair;
    
    private record RecipeBuilderPair(String recipeName, BiFunction<Supplier<ItemLike>, Supplier<ItemLike>, SimpleCookingRecipeBuilder> recipeBuilder) {}
    
    ESmeltingRecipeType(String recipeName, ESmeltingRecipeType recipeType) {
        RecipeBuilderPair pair = recipeType.recipeBuilderPair.get(0);
        recipeBuilderPair = List.of(new RecipeBuilderPair(recipeName, pair.recipeBuilder()));
    }

    ESmeltingRecipeType(String recipeName, BiFunction<Supplier<ItemLike>, Supplier<ItemLike>, SimpleCookingRecipeBuilder> cookingRecipeBuilder) {
        recipeBuilderPair = List.of(new RecipeBuilderPair(recipeName, cookingRecipeBuilder));
    }

    ESmeltingRecipeType(List<RecipeBuilderPair> recipes) {
        recipeBuilderPair = recipes;
    }
    
    private static String GetGroup(Supplier<ItemLike> output) {
        return ForgeRegistries.ITEMS.getKey(output.get().asItem()).getPath();
    }
    
    public void SaveRecipes(Consumer<FinishedRecipe> pWriter, Supplier<ItemLike> input, Supplier<ItemLike> output){
        for (var recipePair : recipeBuilderPair) {
            var recipeId = BetterProgression.MOD_ID + ":" + getItemName(output.get()) + recipePair.recipeName + "_" + getItemName(input.get());
            recipePair.recipeBuilder.apply(input, output).save(pWriter, recipeId);
        }
    }
}
