package net.yuhi.better_progression.block.blockdata;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum EBlockCraftingRecipeType {
    BLOCK_SHAPELESS_1((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 1))
                    .requires(ingredients.get(0).get(), 9)
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get())), false),

    BLOCK_FROM_4_4((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 4))
                    .pattern("   ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    BLOCK_FROM_2_1((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 1))
                    .pattern("   ")
                    .pattern("s  ")
                    .pattern("s  ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    STICK_4((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.STICK, Objects.requireNonNullElse(resultCount, 4))
                    .pattern("   ")
                    .pattern("s  ")
                    .pattern("s  ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    STAIRS_4((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) -> 
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 4))
                    .pattern("s  ")
                    .pattern("ss ")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOODEN_FENCE_3((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 3))
                    .pattern("   ")
                    .pattern("psp")
                    .pattern("psp")
                    .define('s', Items.STICK)
                    .define('p', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOODEN_FENCE_GATE_1((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 1))
                    .pattern("   ")
                    .pattern("sps")
                    .pattern("sps")
                    .define('s', Items.STICK)
                    .define('p', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    WOOD_3((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 3))
                    .pattern("   ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    SLAB_6((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 6))
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    TRAPDOOR_2((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 2))
                    .pattern("   ")
                    .pattern("sss")
                    .pattern("sss")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    DOOR_3((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 3))
                    .pattern("ss ")
                    .pattern("ss ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),
    
    PRESSURE_PLATE_1((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get(), Objects.requireNonNullElse(resultCount, 1))
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("ss ")
                    .define('s', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    SIGN_3((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), Objects.requireNonNullElse(resultCount, 3))
                    .pattern("ppp")
                    .pattern("ppp")
                    .pattern(" s ")
                    .define('s', Items.STICK)
                    .define('p', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    RAIL_16((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, result.get(), Objects.requireNonNullElse(resultCount, 3))
                    .pattern("m m")
                    .pattern("msm")
                    .pattern("m m")
                    .define('s', Items.STICK)
                    .define('m', ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get()))),

    BUTTON_1((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, result.get(), Objects.requireNonNullElse(resultCount, 1))
                    .requires(ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get())),
            false),
    
    PLANKS_4((Supplier<ItemLike> result, Integer resultCount, List<Supplier<ItemLike>> ingredients) ->
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get(), Objects.requireNonNullElse(resultCount, 4))
                    .requires(ingredients.get(0).get())
                    .unlockedBy(getHasName(ingredients.get(0).get()), has(ingredients.get(0).get())),
            false);
    
    final boolean isShaped;
    final TriFunction<Supplier<ItemLike>, Integer, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction;
    
    EBlockCraftingRecipeType(TriFunction<Supplier<ItemLike>, Integer, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction, boolean isShaped) {
        this.builderFunction = builderFunction;
        this.isShaped = isShaped;
    }

    EBlockCraftingRecipeType(TriFunction<Supplier<ItemLike>, Integer, List<Supplier<ItemLike>>, CraftingRecipeBuilder> builderFunction) {
        this.builderFunction = builderFunction;
        this.isShaped = true;
    }
    
    public void SaveRecipe(Consumer<FinishedRecipe> writer, Supplier<ItemLike> itemLike, Integer resultCount, List<Supplier<ItemLike>> ingredients) {
        var item = itemLike.get();
        var result = builderFunction.apply(itemLike, resultCount, ingredients);
        var recipeId = GetName(item) + "_" + GetName(ingredients.get(0).get());
        
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
