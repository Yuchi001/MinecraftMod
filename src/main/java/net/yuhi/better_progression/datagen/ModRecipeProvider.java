package net.yuhi.better_progression.datagen;

import com.mojang.datafixers.kinds.Const;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.custom.MobEssenceItem;
import net.yuhi.better_progression.recipe.utils.ESmeltingRecipeType;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.utils.ETieredItemCraftingCategory;
import net.yuhi.better_progression.tag.ModTags;

import java.util.List;
import java.util.function.Consumer;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;
import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItemInfosForCraftingRecipes;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    private void MetalRecipesCollectionCreator(Consumer<FinishedRecipe> pWriter) {
        for (var nugget : getItemInfosForCraftingRecipes(EItemCategory.Nugget)) {
            var mod_id = nugget.has_default_basis ? "minecraft" : nugget.mod_id;
            var registryKey = new ResourceLocation(mod_id, nugget.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = nugget.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var nuggetRecipeId = tier_name + "_" + nugget.category.getName().toLowerCase();
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (Item)nugget.item.get(), 9)
                    .requires(basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, nuggetRecipeId);

            var ingotRecipeId = tier_name + "_ingot_from_nugget";
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, basisItem, 1)
                    .requires((Item)nugget.item.get(), 9)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, ingotRecipeId);

            if (nugget.has_default_basis) continue;
            
            var metalBlockStr = tier_name + "_block";
            
            var metalBlockRegistryKey = new ResourceLocation(mod_id, metalBlockStr);

            var metalBlock = ForgeRegistries.BLOCKS.getValue(metalBlockRegistryKey);
            if (metalBlock == null) continue;

            var ingotsFromBlockRecipeId = tier_name + "_ingot_from_block";
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, basisItem, 9)
                    .requires(metalBlock)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, ingotsFromBlockRecipeId);
        }
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SADDLE)
                .pattern("sss")
                .pattern("* *")
                .pattern("z z")
                .define('s', Items.LEATHER)
                .define('*', Items.STRING)
                .define('z', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(pWriter, "better_saddle");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.TOTEM_OF_UNDYING)
                .requires(ModItems.BROKEN_TOTEM_OF_UNDYING.get())
                .requires(Items.GOLD_INGOT, 3)
                .requires(Items.EMERALD, 2)
                .requires(Items.GHAST_TEAR, 2)
                .requires(Items.NETHER_STAR)
                .unlockedBy(getHasName(ModItems.BROKEN_TOTEM_OF_UNDYING.get()), has(ModItems.BROKEN_TOTEM_OF_UNDYING.get()))
                .save(pWriter, "totem_of_undying_from_broken_totem_of_undying");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.TNT)
                .requires(Blocks.SAND, 2)
                .requires(Items.GUNPOWDER, 2)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER)).save(pWriter, "better_tnt");
        
        var enderiteIngot = getItem(EItemCategory.Ingot, EMaterialType.ENDERITE);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, enderiteIngot, 9)
                .requires(ModBlocks.ENDERITE_BLOCK.get())
                .unlockedBy(getHasName(enderiteIngot), has(ModItems.DRAGON_REMAINS.get())).save(pWriter, "enderite_ingot_from_block");

        var tinIngot = getItem(EItemCategory.Ingot, EMaterialType.TIN);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, tinIngot, 9)
                .requires(ModBlocks.TIN_BLOCK.get(), 1)
                .unlockedBy(getHasName(tinIngot), has(tinIngot)).save(pWriter, "tin_ingot_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.POLISHED_PINK_QUARTZ.get(), 1)
                .requires(ModItems.PINK_QUARTZ.get(), 2)
                .unlockedBy(getHasName(ModItems.PINK_QUARTZ.get()), has(ModItems.PINK_QUARTZ.get()))
                .save(pWriter, "polished_pink_quartz");
        
        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModBlocks.PINK_QUARTZ_BLOCK.get()),
                RecipeCategory.COMBAT,
                ModItems.POLISHED_PINK_QUARTZ.get(),
                4
        ).unlockedBy(getHasName(ModItems.PINK_QUARTZ.get()), has(ModItems.PINK_QUARTZ.get()))
                .save(pWriter, "polished_pink_quartz_from_stonecutting");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.POLISHED_QUARTZ.get(), 1)
                .requires(Items.QUARTZ, 2)
                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                .save(pWriter, "polished_quartz");

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(Blocks.QUARTZ_BLOCK),
                RecipeCategory.COMBAT,
                ModItems.POLISHED_QUARTZ.get(),
                4
        ).unlockedBy(getHasName(Blocks.QUARTZ_BLOCK), has(Blocks.QUARTZ_BLOCK))
                .save(pWriter, "polished_quartz_from_stonecutting");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, enderiteIngot)
                .requires(ModItems.DRAGON_REMAINS.get(), 4)
                .requires(tinIngot, 4)
                .unlockedBy(getHasName(ModItems.DRAGON_REMAINS.get()), has(ModItems.DRAGON_REMAINS.get())).save(pWriter, "enderite_ingot_from_remains");
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HILT.get())
                .pattern(" t ")
                .pattern("lsl")
                .pattern(" t ")
                .define('s', Items.STICK)
                .define('l', Items.LEATHER)
                .define('t', getItem(EItemCategory.Ingot, EMaterialType.TIN))
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(pWriter, "hilt");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.ACTIVATOR_RAIL, 6)
                .pattern("csc")
                .pattern("ctc")
                .pattern("csc")
                .define('c', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .define('t', Items.REDSTONE_TORCH)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter, "copper_activator_rail");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DETECTOR_RAIL, 6)
                .pattern("c c")
                .pattern("csc")
                .pattern("crc")
                .define('c', Items.COPPER_INGOT)
                .define('s', Items.STONE_PRESSURE_PLATE)
                .define('r', Items.REDSTONE)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter, "copper_detector_rail");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.RAIL, 16)
                .pattern("c c")
                .pattern("csc")
                .pattern("c c")
                .define('c', Items.COPPER_INGOT)
                .define('s', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter, "copper_rail");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.POWERED_RAIL, 16)
                .pattern("c c")
                .pattern("csc")
                .pattern("crc")
                .define('c', Items.GOLD_INGOT)
                .define('s', Items.STICK)
                .define('r', Items.REDSTONE)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter, "copper_powered_rail");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURE_DIAMOND.get())
                .pattern("***")
                .pattern("*d*")
                .pattern("***")
                .define('*', Items.LAPIS_LAZULI)
                .define('d', Items.DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(pWriter, "pure_diamond");
        
        MetalRecipesCollectionCreator(pWriter);
        
        for (var blockData : ModBlocks.BLOCKS_DATA) {
            blockData.SaveRecipes(pWriter);
        }
        
        for (var itemInfo : ModItems.REGISTERED_ITEMS) {
            itemInfo.SaveRecipes(pWriter);
            if(itemInfo.item.get() instanceof MobEssenceItem item) item.SaveRecipe(pWriter); 

            for (var tag : itemInfo.tags) {
                if (tag == ModTags.Items.COPPER_TOOLS_ARMOR) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> getItem(EItemCategory.Nugget, EMaterialType.COPPER));
                else if (tag == ModTags.Items.CUSTOM_IRON_TOOLS) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> Items.IRON_NUGGET);
                else if (tag == ModTags.Items.CUSTOM_GOLD_TOOLS) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> Items.GOLD_NUGGET);
                else if (tag == ModTags.Items.BRONZE_TOOLS_ARMOR) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> getItem(EItemCategory.Nugget, EMaterialType.BRONZE));
                else if (tag == ModTags.Items.STEEL_TOOLS_ARMOR) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> getItem(EItemCategory.Nugget, EMaterialType.STEEL));
                else if (tag == ModTags.Items.NETHERITE_TOOLS_ARMOR) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, () -> Items.NETHERITE_SCRAP);
                else if (tag == ModTags.Items.ENDERITE_TOOLS_ARMOR) ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, itemInfo.item::get, ModItems.DRAGON_REMAINS::get);
            }
        }

        var vanillaTools = List.of(Items.IRON_SWORD,
                Items.IRON_AXE, Items.IRON_PICKAXE,
                Items.IRON_HOE, Items.IRON_SHOVEL,
                Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS,
                Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE,
                Items.GOLDEN_HOE, Items.GOLDEN_SHOVEL,
                Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
        for (var item: vanillaTools) {
            ESmeltingRecipeType.SMELT_ORE_RARE.SaveRecipes(pWriter, () -> item, () -> Items.IRON_NUGGET);
        }
        
        ESmeltingRecipeType.SMELT_FOOD.SaveRecipes(pWriter, () -> Items.WHEAT, () -> Items.BREAD);

        ESmeltingRecipeType.SMELT_ORE_COMMON.SaveRecipes(pWriter, () -> getItem(EItemCategory.RawMaterial, EMaterialType.TIN), () -> getItem(EItemCategory.Ingot, EMaterialType.TIN));

        var vanilla_materials = List.of(EMaterialType.GOLD, EMaterialType.IRON);
        for (var material: vanilla_materials) {
            ETieredItemCraftingCategory.CHAINMAIL_BOOTS.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.CHAINMAIL_LEGGINGS.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.CHAINMAIL_CHESTPLATE.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.CHAINMAIL_HELMET.SaveShapedRecipe(pWriter, material);

            ETieredItemCraftingCategory.BOOTS.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.LEGGINGS.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.CHESTPLATE.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.HELMET.SaveShapedRecipe(pWriter, material);

            ETieredItemCraftingCategory.SWORD.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.AXE.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.PICKAXE.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.SHOVEL.SaveShapedRecipe(pWriter, material);
            ETieredItemCraftingCategory.HOE.SaveShapedRecipe(pWriter, material);
        }
    }
}
