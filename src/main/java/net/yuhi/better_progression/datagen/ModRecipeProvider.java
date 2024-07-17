package net.yuhi.better_progression.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
    
    private static final List<ItemLike> TIN_SMELTABLES = List.of(
            ModItems.RAW_TIN.get()
    );
    
    private static void woodenRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_CLUB.get())
                .pattern(" * ")
                .pattern(" * ")
                .pattern(" * ")
                .define('*', ItemTags.PLANKS)
                .unlockedBy("has_item", has(ItemTags.PLANKS))
                .save(pWriter, "wooden_club");
    }
    
    private static void copperRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_PICKAXE.get())
                .pattern("***")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('*', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "copper_pickaxe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HOE.get())
                .pattern("** ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('*', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "copper_hoe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_AXE.get())
                .pattern("** ")
                .pattern("*# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('*', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "copper_axe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SWORD.get())
                .pattern(" * ")
                .pattern(" * ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('*', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "copper_sword");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SHOVEL.get())
                .pattern(" * ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('*', Items.COPPER_INGOT)
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "copper_shovel");
    }
    
    private static void tinRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.TIN_INGOT.get())
                .unlockedBy(getHasName(ModItems.TIN_INGOT.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "tin_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 9)
                .requires(ModBlocks.TIN_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.TIN_BLOCK.get()), has(ModItems.TIN_INGOT.get()))
                .save(pWriter, "tin_ingot");

        oreGeneric(pWriter, TIN_SMELTABLES, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.25f, 200, "tin_ingot_smeltable");
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        copperRecipes(pWriter);
        tinRecipes(pWriter);
        woodenRecipes(pWriter);
    }
    
    protected static void oreGeneric(Consumer<FinishedRecipe> pWriter, List<ItemLike> items, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreSmelting(pWriter, items, pCategory, pResult, pExperience, pCookingTIme, pGroup);
        oreBlasting(pWriter, items, pCategory, pResult, pExperience, pCookingTIme, pGroup);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, BetterProgression.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
