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
                    250, 
                    5.0f, 
                    1.5f, 
                    10, 
            BlockTags.NEEDS_STONE_TOOL, 
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier BRONZE =
            new ForgeTier(
                    3,
                    59,
                    4.5f,
                    4.0f,
                    22,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)));

    public static final ForgeTier STEEL =
            new ForgeTier(
                    3,
                    750,
                    5.5f,
                    1.5f,
                    1,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)));

    public static final ForgeTier BETTER_DIAMOND =
            new ForgeTier(
                    3,
                    500,
                    7f,
                    3.0f,
                    10,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.DIAMOND));

    public static final ForgeTier OBSIDIAN = 
            new ForgeTier(
                    3,
                    19,
                    7,
                    2.5f,
                    5,
                    BlockTags.NEEDS_DIAMOND_TOOL,
                    () -> Ingredient.of(Items.OBSIDIAN));
}
