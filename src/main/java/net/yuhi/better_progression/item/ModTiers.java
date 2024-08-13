package net.yuhi.better_progression.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public class ModTiers {
    public static final ForgeTier COPPER = 
            new ForgeTier(
                    1, 
                    150,
                    6.5f,
                    3.0f,
                    20,
            BlockTags.NEEDS_STONE_TOOL, 
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier BRONZE =
            new ForgeTier(
                    3,
                    300,
                    8.5f,
                    4.0f,
                    10,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)));

    public static final ForgeTier STEEL =
            new ForgeTier(
                    3,
                    500,
                    8.0f,
                    3f,
                    10,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)));

    public static final ForgeTier BETTER_DIAMOND =
            new ForgeTier(
                    3,
                    500,
                    8f,
                    3.5f,
                    15,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.DIAMOND));

    public static final ForgeTier OBSIDIAN = 
            new ForgeTier(
                    3,
                    19,
                    6.0f,
                    5.0f,
                    1,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.OBSIDIAN));

    public static final ForgeTier ENDGAMEBRONZE =
            new ForgeTier(
                    4,
                    600,
                    9.0f,
                    4.5f,
                    15,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static final ForgeTier ENDGAMESTEEL =
            new ForgeTier(
                    4,
                    1000,
                    9.0f,
                    4.0f,
                    15,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_INGOT));
}
