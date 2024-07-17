package net.yuhi.better_progression.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier COPPER = 
            new ForgeTier(
                    1, 
                    250, 
                    5.0f, 
                    1.5f, 
                    10, 
            BlockTags.NEEDS_STONE_TOOL, 
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier BETTER_GOLD =
            new ForgeTier(
                    1,
                    250,
                    12.0f,
                    0.5f,
                    22,
                    BlockTags.NEEDS_IRON_TOOL,
                    () -> Ingredient.of(Items.GOLD_INGOT));

    public static final ForgeTier BRONZE =
            new ForgeTier(
                    3,
                    59,
                    4.5f,
                    4.0f,
                    22,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

    public static final ForgeTier STEEL =
            new ForgeTier(
                    3,
                    750,
                    5.5f,
                    1.5f,
                    1,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final ForgeTier BETTER_DIAMOND =
            new ForgeTier(
                    3,
                    500,
                    7f,
                    3.0f,
                    10,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.DIAMOND));

    public static final ForgeTier NETHERBRONZE =
            new ForgeTier(
                    4,
                    375,
                    8.0f,
                    6f,
                    25,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static final ForgeTier NETHERSTEEL =
            new ForgeTier(
                    4,
                    1125,
                    8.0f,
                    2f,
                    20,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static final ForgeTier DIAMONITE =
            new ForgeTier(
                    4,
                    750,
                    9.5f,
                    4f,
                    30,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_INGOT));
}
