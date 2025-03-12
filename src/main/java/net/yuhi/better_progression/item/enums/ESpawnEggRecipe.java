package net.yuhi.better_progression.item.enums;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.yuhi.better_progression.item.ModItems;

import java.util.function.Consumer;

public enum ESpawnEggRecipe {
    SKELETON(Items.SKELETON_SPAWN_EGG, Items.BONE),
    ZOMBIE(Items.ZOMBIE_SPAWN_EGG, Items.ROTTEN_FLESH),
    SLIME(Items.SLIME_SPAWN_EGG, Items.SLIME_BALL),
    ENDERMAN(Items.ENDERMAN_SPAWN_EGG, Items.ENDER_EYE),
    CREEPER(Items.CREEPER_SPAWN_EGG, Items.GUNPOWDER),
    MAGMACUBE(Items.MAGMA_CUBE_SPAWN_EGG, Items.MAGMA_CREAM),
    PHANTOM(Items.PHANTOM_SPAWN_EGG, Items.PHANTOM_MEMBRANE),
    IRON_GOLEM(Items.IRON_GOLEM_SPAWN_EGG, Items.IRON_INGOT),
    WITHER_SKELETON(Items.WITHER_SKELETON_SPAWN_EGG, Items.COAL),
    COW(Items.COW_SPAWN_EGG, Items.BEEF),
    PIG(Items.PIG_SPAWN_EGG, Items.PORKCHOP),
    SHEEP(Items.SHEEP_SPAWN_EGG, ItemTags.WOOL),
    CHICKEN(Items.CHICKEN_SPAWN_EGG, Items.FEATHER),
    PIGMEN(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, Items.GOLD_INGOT),
    GHAST(Items.GHAST_SPAWN_EGG, Items.GHAST_TEAR),
    SPIDER(Items.SPIDER_SPAWN_EGG, Items.SPIDER_EYE);
    
    private boolean byTag = false;
    private Item spawnEgg;
    private Item ingredient;
    private TagKey<Item> ingredient_tag;
    
    ESpawnEggRecipe(Item spawnEgg, Item ingredient) {
        this.spawnEgg = spawnEgg;
        this.ingredient = ingredient;
    }

    ESpawnEggRecipe(Item spawnEgg, TagKey<Item> ingredient) {
        this.spawnEgg = spawnEgg;
        this.ingredient_tag = ingredient;
        this.byTag = true;
    }
    
    public static void SaveRecipes(Consumer<FinishedRecipe> pWriter) {
        for (ESpawnEggRecipe recipe : values()) {
            var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, recipe.spawnEgg)
                    .requires(Items.GHAST_TEAR)
                    .requires(ModItems.SOUL_SHARD.get())
                    .requires(Items.EGG);
            if (recipe.byTag) {
                builder.requires(recipe.ingredient_tag)
                        .unlockedBy("has_" + recipe.ingredient_tag.toString(), has(ItemTags.WOOL));
            } else {
                builder.requires(recipe.ingredient)
                        .unlockedBy(getHasName(recipe.ingredient), has(recipe.ingredient));
            }
            builder.save(pWriter, recipe.name().toLowerCase() + "_spawn_egg_bp");
        }
    }

    private static String getHasName(ItemLike pItemLike) {
        return "has_" + getItemName(pItemLike);
    }

    private static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    private static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
