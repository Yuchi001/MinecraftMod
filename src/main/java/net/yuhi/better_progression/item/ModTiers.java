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
                    () -> Ingredient.of(ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.BRONZE)));

    public static final ForgeTier STEEL =
            new ForgeTier(
                    3,
                    750,
                    5.5f,
                    1.5f,
                    1,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.STEEL)));

    public static final ForgeTier BETTER_DIAMOND =
            new ForgeTier(
                    3,
                    500,
                    7f,
                    3.0f,
                    10,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.DIAMOND));

    public static final ForgeTier OBSYDIAN = 
            new ForgeTier(
                    3,
                    51,
                    7,
                    2.5f,
                    5,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.OBSIDIAN));
}
