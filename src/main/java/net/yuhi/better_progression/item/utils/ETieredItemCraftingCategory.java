package net.yuhi.better_progression.item.utils;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.LegacyUpgradeRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.util.function.Consumer;
import java.util.function.Function;

import static net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType.getHasName;
import static net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType.has;

@SuppressWarnings("removal")
public enum ETieredItemCraftingCategory {
    NETHER_SWORD(EItemCategory.Sword, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Sword, materialType, EMaterialType.NETHERITE)),
    NETHER_PICKAXE(EItemCategory.PickAxe, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.PickAxe, materialType, EMaterialType.NETHERITE)),
    NETHER_AXE(EItemCategory.Axe, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Axe, materialType, EMaterialType.NETHERITE)),
    NETHER_SHOVEL(EItemCategory.Shovel, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Shovel, materialType, EMaterialType.NETHERITE)),
    NETHER_HOE(EItemCategory.Hoe, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Hoe, materialType, EMaterialType.NETHERITE)),
    NETHER_MECHETE(EItemCategory.Machete, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Machete, materialType, EMaterialType.NETHERITE)),
    NETHER_LONGSWORD(EItemCategory.LongSword, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.LongSword, materialType, EMaterialType.NETHERITE)),
    NETHER_BATTLEAXE(EItemCategory.BattleAxe, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.BattleAxe, materialType, EMaterialType.NETHERITE)),
    
    NETHER_HELMET(EItemCategory.Helmet, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Helmet, materialType, EMaterialType.NETHERITE)),
    NETHER_CHESTPLATE(EItemCategory.Chestplate, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Chestplate, materialType, EMaterialType.NETHERITE)),
    NETHER_LEGGINGS(EItemCategory.Leggings, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Leggings, materialType, EMaterialType.NETHERITE)),
    NETHER_BOOTS(EItemCategory.Boots, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Boots, materialType, EMaterialType.NETHERITE)),

    NETHER_CHAINMAIL_HELMET(EItemCategory.ChainmailHelmet, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailHelmet, materialType, EMaterialType.NETHERITE)),
    NETHER_CHAINMAIL_CHESTPLATE(EItemCategory.ChainmailChestplate, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailChestplate, materialType, EMaterialType.NETHERITE)),
    NETHER_CHAINMAIL_LEGGINGS(EItemCategory.ChainmailLeggings, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailLeggings, materialType, EMaterialType.NETHERITE)),
    NETHER_CHAINMAIL_BOOTS(EItemCategory.ChainmailBoots, EMaterialType.NETHERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailBoots, materialType, EMaterialType.NETHERITE)),

    ENDER_SWORD(EItemCategory.Sword, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Sword, materialType, EMaterialType.ENDERITE)),
    ENDER_PICKAXE(EItemCategory.PickAxe, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.PickAxe, materialType, EMaterialType.ENDERITE)),
    ENDER_AXE(EItemCategory.Axe, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Axe, materialType, EMaterialType.ENDERITE)),
    ENDER_SHOVEL(EItemCategory.Shovel, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Shovel, materialType, EMaterialType.ENDERITE)),
    ENDER_HOE(EItemCategory.Hoe, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Hoe, materialType, EMaterialType.ENDERITE)),
    ENDER_MECHETE(EItemCategory.Machete, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Machete, materialType, EMaterialType.ENDERITE)),
    ENDER_LONGSWORD(EItemCategory.LongSword, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.LongSword, materialType, EMaterialType.ENDERITE)),
    ENDER_BATTLEAXE(EItemCategory.BattleAxe, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.BattleAxe, materialType, EMaterialType.ENDERITE)),
    
    ENDER_HELMET(EItemCategory.Helmet, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Helmet, materialType, EMaterialType.ENDERITE)),
    ENDER_CHESTPLATE(EItemCategory.Chestplate, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Chestplate, materialType, EMaterialType.ENDERITE)),
    ENDER_LEGGINGS(EItemCategory.Leggings, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Leggings, materialType, EMaterialType.ENDERITE)),
    ENDER_BOOTS(EItemCategory.Boots, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.Boots, materialType, EMaterialType.ENDERITE)),

    ENDER_CHAINMAIL_HELMET(EItemCategory.ChainmailHelmet, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailHelmet, materialType, EMaterialType.ENDERITE)),
    ENDER_CHAINMAIL_CHESTPLATE(EItemCategory.ChainmailChestplate, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailChestplate, materialType, EMaterialType.ENDERITE)),
    ENDER_CHAINMAIL_LEGGINGS(EItemCategory.ChainmailLeggings, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailLeggings, materialType, EMaterialType.ENDERITE)),
    ENDER_CHAINMAIL_BOOTS(EItemCategory.ChainmailBoots, EMaterialType.ENDERITE, (EMaterialType materialType) -> UpgradeRecipe(EItemCategory.ChainmailBoots, materialType, EMaterialType.ENDERITE)),
    
    CHAINMAIL_HELMET(EItemCategory.ChainmailHelmet, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.ChainmailHelmet, materialType, EItemCategory.Chainmail)),
    CHAINMAIL_CHESTPLATE(EItemCategory.ChainmailChestplate, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.ChainmailChestplate, materialType, EItemCategory.Chainmail)),
    CHAINMAIL_LEGGINGS(EItemCategory.ChainmailLeggings, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.ChainmailLeggings, materialType, EItemCategory.Chainmail)),
    CHAINMAIL_BOOTS(EItemCategory.ChainmailBoots, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.ChainmailBoots, materialType, EItemCategory.Chainmail)),

    HELMET(EItemCategory.Helmet, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.Helmet, materialType, EItemCategory.Plate)),
    CHESTPLATE(EItemCategory.Chestplate, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.Chestplate, materialType, EItemCategory.Plate)),
    LEGGINGS(EItemCategory.Leggings, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.Leggings, materialType, EItemCategory.Plate)),
    BOOTS(EItemCategory.Boots, (EMaterialType materialType) -> ArmorRecipe(EItemCategory.Boots, materialType, EItemCategory.Plate)),
    
    PLATE(EItemCategory.Plate, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var nugget = GetNugget(materialType);
        var result = ItemsUtilsMethods.getItem(EItemCategory.Plate, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" * ")
                .pattern("*#*")
                .pattern(" * ")
                .define('#', nugget)
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    CHAINMAIL(EItemCategory.Chainmail, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var nugget = GetNugget(materialType);
        var result = ItemsUtilsMethods.getItem(EItemCategory.Chainmail, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" * ")
                .pattern("*#*")
                .pattern(" * ")
                .define('*', nugget)
                .define('#', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    MACHETE(EItemCategory.Machete, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.Machete, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("  *")
                .pattern(" * ")
                .pattern("#  ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    CLUB(EItemCategory.Club, (EMaterialType materialType) -> {
        var result = GetTool(EItemCategory.Club, materialType);
        var builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" * ")
                .pattern(" * ")
                .pattern(" # ")
                .define('#', Items.STICK);
        var tag = GetTag(materialType);
        if (materialType.IsTag()) return builder.define('*', tag).unlockedBy(getHasName(tag), has(tag));

        var base = GetBase(materialType);
        return builder.define('*', base).unlockedBy(getHasName(base), has(base));
    }),
    SPEAR(EItemCategory.Spear, (EMaterialType materialType) -> {
        var result = GetTool(EItemCategory.Spear, materialType);
        var builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("  *")
                .pattern(" # ")
                .pattern("#  ")
                .define('#', Items.STICK);
        var tag = GetTag(materialType);
        if (materialType.IsTag()) return builder.define('*', tag).unlockedBy(getHasName(tag), has(tag));
        
        var base = GetBase(materialType);
        return builder.define('*', base).unlockedBy(getHasName(base), has(base));
    }),
    DAGGER(EItemCategory.Dagger, (EMaterialType materialType) -> {
        var result = GetTool(EItemCategory.Dagger, materialType);
        var builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("   ")
                .pattern(" * ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .unlockedBy(getHasName(result), has(result));
        var tag = GetTag(materialType);
        if (materialType.IsTag()) return builder.define('*', GetTag(materialType)).unlockedBy(getHasName(tag), has(tag));

        var base = GetBase(materialType);
        return builder.define('*', base).unlockedBy(getHasName(base), has(base));
    }),
    BATTLEAXE(EItemCategory.BattleAxe, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var axe = GetTool(EItemCategory.Axe, materialType);
        var result = ItemsUtilsMethods.getItem(EItemCategory.BattleAxe, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" *S")
                .pattern(" #*")
                .pattern("#  ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .define('S', axe)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    LONGSWORD(EItemCategory.LongSword, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var sword = GetTool(EItemCategory.Sword, materialType);
        var result = ItemsUtilsMethods.getItem(EItemCategory.LongSword, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("  *")
                .pattern("*S ")
                .pattern("#* ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .define('S', sword)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    PICKAXE(EItemCategory.PickAxe, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.PickAxe, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("***")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    HOE(EItemCategory.Hoe, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.Hoe, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("** ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    SWORD(EItemCategory.Sword, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.Sword, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" * ")
                .pattern(" * ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    SHOVEL(EItemCategory.Shovel, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.Shovel, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" * ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    }),
    AXE(EItemCategory.Axe, (EMaterialType materialType) -> {
        var ingot = GetBase(materialType);
        var result = GetTool(EItemCategory.Axe, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("** ")
                .pattern("*# ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', ingot)
                .unlockedBy(getHasName(ingot), has(ingot));
    });
    
    private Function<EMaterialType, ShapedRecipeBuilder> shapedRecipeSupplier = null;
    private Function<EMaterialType, LegacyUpgradeRecipeBuilder> upgradeRecipeSupplier = null;
    private final boolean isUpgrade;
    private final EItemCategory category;
    private final EMaterialType upgradeMaterial;
    
    ETieredItemCraftingCategory(EItemCategory category, Function<EMaterialType, ShapedRecipeBuilder> recipeSupplier) {
        this.shapedRecipeSupplier = recipeSupplier;
        this.category = category;
        this.isUpgrade = false;
        this.upgradeMaterial = null;
    }
    
    ETieredItemCraftingCategory(EItemCategory category, EMaterialType upgradeMaterial, Function<EMaterialType, LegacyUpgradeRecipeBuilder> recipeSupplier, int... temp) {
        this.upgradeRecipeSupplier = recipeSupplier;
        this.category = category;
        this.isUpgrade = true;
        this.upgradeMaterial = upgradeMaterial;
    }
    
    private static ShapedRecipeBuilder ArmorRecipe(EItemCategory category, EMaterialType materialType, EItemCategory armorBaseCategory) {
        var ingot = GetBase(materialType);
        var base = ItemsUtilsMethods.getItem(armorBaseCategory, materialType);
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, GetArmor(category, materialType))
                .pattern(" # ")
                .pattern("#*#")
                .pattern(" # ")
                .define('*', ItemsUtilsMethods.getLeatherArmorItem(category))
                .define('#', base)
                .unlockedBy(getHasName(ingot), has(ingot));
    }

    private static LegacyUpgradeRecipeBuilder UpgradeRecipe(EItemCategory category, EMaterialType base, EMaterialType upgrade) {
        var baseItem = ItemsUtilsMethods.getItem(category, base);
        var ingot = GetBase(upgrade);
        var result = ItemsUtilsMethods.getItem(category, upgrade, base);
        return LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(baseItem), Ingredient.of(ingot), RecipeCategory.COMBAT, result).unlocks(getHasName(ingot), has(ingot));
    }

    public boolean IsValidRecipeForItem(TierItemsCreator.ItemInfo itemInfo) {
        return category == itemInfo.category && itemInfo.is_upgrade == isUpgrade && (!isUpgrade || itemInfo.material_type == upgradeMaterial);
    }
    
    public void SaveRecipes(Consumer<FinishedRecipe> pWriter, TierItemsCreator.ItemInfo itemInfo) {
        var recipeId = itemInfo.is_upgrade ? itemInfo.material_type.GetName(true) + "_" +
                itemInfo.sub_material_type.GetName() + "_" +
                category.getName().toLowerCase()
                : itemInfo.material_type.GetName(true) + "_" + category.getName().toLowerCase();
        
        try {
            if (itemInfo.is_upgrade) upgradeRecipeSupplier.apply(itemInfo.sub_material_type).save(pWriter, recipeId);
            else shapedRecipeSupplier.apply(itemInfo.material_type).save(pWriter, recipeId);
        } catch (Exception ignored) {}
    }
    
    public void SaveShapedRecipe(Consumer<FinishedRecipe> pWriter, EMaterialType materialType) {
        var recipeId = "better_progression_" + materialType.GetName(true) + "_" + category.getName().toLowerCase();
        shapedRecipeSupplier.apply(materialType).save(pWriter, recipeId);
    }

    public void SaveSmithingRecipe(Consumer<FinishedRecipe> pWriter, EMaterialType materialType, EMaterialType subMaterialType) {
        var recipeId = materialType.GetName(true) + "_" +
                subMaterialType.GetName() + "_" +
                category.getName().toLowerCase();
        upgradeRecipeSupplier.apply(subMaterialType).save(pWriter, recipeId);
    }
    
    private static Item GetBase(EMaterialType materialType) {
        return switch (materialType) {
            case IRON -> Items.IRON_INGOT;
            case COPPER -> Items.COPPER_INGOT;
            case OBSIDIAN -> Items.OBSIDIAN;
            case DIAMOND -> Items.DIAMOND;
            case NETHERITE -> Items.NETHERITE_INGOT;
            default -> ItemsUtilsMethods.getItem(EItemCategory.Ingot, materialType);
        };
    }
    
    private static TagKey<Item> GetTag(EMaterialType materialType) {
         if (materialType == EMaterialType.WOOD) return ItemTags.PLANKS;
         return ItemTags.STONE_TOOL_MATERIALS;
    }
    
    private static Item GetTool(EItemCategory itemCategory, EMaterialType materialType) {
        if(materialType != EMaterialType.IRON) return ItemsUtilsMethods.getItem(itemCategory, materialType);
        
        return switch (itemCategory) {
            case Axe -> Items.IRON_AXE;
            case Shovel -> Items.IRON_SHOVEL;
            case PickAxe -> Items.IRON_PICKAXE;
            case Hoe -> Items.IRON_HOE;
            case Sword -> Items.IRON_SWORD;
            default -> ItemsUtilsMethods.getItem(itemCategory, materialType);
        };
    }

    private static Item GetNugget(EMaterialType materialType) {
        if(materialType != EMaterialType.IRON) return ItemsUtilsMethods.getItem(EItemCategory.Nugget, materialType);
        return Items.IRON_NUGGET;
    }

    private static Item GetArmor(EItemCategory itemCategory, EMaterialType materialType) {
        if(materialType != EMaterialType.IRON) return ItemsUtilsMethods.getItem(itemCategory, materialType);

        return switch (itemCategory) {
            case Helmet -> Items.IRON_HELMET;
            case Chestplate -> Items.IRON_CHESTPLATE;
            case Leggings -> Items.IRON_LEGGINGS;
            case Boots -> Items.IRON_BOOTS;
            case ChainmailHelmet -> Items.CHAINMAIL_HELMET;
            case ChainmailChestplate -> Items.CHAINMAIL_CHESTPLATE;
            case ChainmailLeggings -> Items.CHAINMAIL_LEGGINGS;
            case ChainmailBoots -> Items.CHAINMAIL_BOOTS;
            default -> ItemsUtilsMethods.getItem(itemCategory, materialType);
        };
    }
}
