package net.yuhi.better_progression.block.blockdata;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum EBlockCraftingRecipeType {
    BLOCK_SHAPELESS((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
                    .requires(ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    BLOCK_SHAPE((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
                    .pattern("   ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    STAIRS((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) -> 
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
                    .pattern("s  ")
                    .pattern("ss ")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOODEN_FENCE((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 3)
                    .pattern("   ")
                    .pattern("psp")
                    .pattern("psp")
                    .define('s', Items.STICK)
                    .define('p', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOODEN_FENCE_GATE((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 1)
                    .pattern("   ")
                    .pattern("sps")
                    .pattern("sps")
                    .define('s', Items.STICK)
                    .define('p', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOOD((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 3)
                    .pattern("   ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    SLAB((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 6)
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    TRAPDOOR((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 2)
                    .pattern("   ")
                    .pattern("sss")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    DOOR((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get(), 3)
                    .pattern("ss ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    PRESSURE_PLATE((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get())
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    BUTTON((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get())
                    .requires(ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get())),
            false),
    
    PLANKS((Supplier<ItemLike> result, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get())
                    .requires(ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get())),
            false);
    
    final boolean isShaped;
    final BiFunction<Supplier<ItemLike>, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction;
    
    EBlockCraftingRecipeType(BiFunction<Supplier<ItemLike>, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction, boolean isShaped) {
        this.builderFunction = builderFunction;
        this.isShaped = isShaped;
    }

    EBlockCraftingRecipeType(BiFunction<Supplier<ItemLike>, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction) {
        this.builderFunction = builderFunction;
        this.isShaped = true;
    }
    
    public void SaveRecipe(Consumer<FinishedRecipe> writer, Supplier<ItemLike> itemLike, List<Supplier<ItemLike>> ingredients) {
        var item = itemLike.get();
        var result = builderFunction.apply(itemLike, ingredients);
        var recipeId = GetName(item);
        if(recipeId == null) return;
        
        if (isShaped) ((ShapedRecipeBuilder)result).save(writer, recipeId);
        else ((ShapelessRecipeBuilder)result).save(writer, recipeId);
    }
    
    private String GetName(ItemLike item){
        var asItem = item.asItem();
        if (asItem == Items.AIR) return null;
        
        var resourceKey = ForgeRegistries.ITEMS.getKey(asItem);
        if(resourceKey == null) return null;
        
        return resourceKey.getPath(); 
    }

    static String getHasName(ItemLike pItemLike) {
        return "has_" + getItemName(pItemLike);
    }

    static String getItemName(ItemLike pItemLike) {
        return ForgeRegistries.ITEMS.getKey(pItemLike.asItem()).getPath();
    }

    static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
