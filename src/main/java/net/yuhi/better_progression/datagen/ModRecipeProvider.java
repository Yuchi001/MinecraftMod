package net.yuhi.better_progression.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.utils.ItemInfo;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;

import java.util.List;
import java.util.function.Consumer;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;
import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItemInfosForCraftingRecipes;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
    
    private void PlateRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var plate : getItemInfosForCraftingRecipes(EItemCategory.Plate)) {
            var mod_id = plate.has_default_basis ? "minecraft" : plate.mod_id;
            var ingotRegistryKey = new ResourceLocation(mod_id, plate.basis);
            var nuggetRegistryKey = new ResourceLocation(plate.material_type == EMaterialType.COPPER ? BetterProgression.MOD_ID : mod_id, plate.basis.substring(0, plate.basis.lastIndexOf("_")) + "_nugget");

            var ingotItem = ForgeRegistries.ITEMS.getValue(ingotRegistryKey);
            var nuggetItem = ForgeRegistries.ITEMS.getValue(nuggetRegistryKey);
            if (ingotItem == null || nuggetItem == null) continue;

            var tier_name = plate.basis;
            var recipeId = tier_name.substring(0, tier_name.lastIndexOf('_')) + "_" + plate.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (Item)plate.item.get())
                    .pattern(" * ")
                    .pattern("*#*")
                    .pattern(" * ")
                    .define('#', nuggetItem)
                    .define('*', ingotItem)
                    .unlockedBy(getHasName(ingotItem), has(ingotItem))
                    .save(pWriter, recipeId);
        }
    }

    private void ChainmailRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var chainmail : getItemInfosForCraftingRecipes(EItemCategory.Chainmail)) {
            var mod_id = chainmail.has_default_basis ? "minecraft" : chainmail.mod_id;
            var ingotRegistryKey = new ResourceLocation(mod_id, chainmail.basis);
            var nuggetRegistryKey = new ResourceLocation(chainmail.material_type == EMaterialType.COPPER ? BetterProgression.MOD_ID : mod_id, chainmail.basis.substring(0, chainmail.basis.lastIndexOf("_")) + "_nugget");

            var ingotItem = ForgeRegistries.ITEMS.getValue(ingotRegistryKey);
            var nuggetItem = ForgeRegistries.ITEMS.getValue(nuggetRegistryKey);
            if (ingotItem == null || nuggetItem == null) continue;

            var tier_name = chainmail.basis;
            var recipeId = tier_name.substring(0, tier_name.lastIndexOf('_')) + "_" + chainmail.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (Item)chainmail.item.get())
                    .pattern(" * ")
                    .pattern("*#*")
                    .pattern(" * ")
                    .define('*', nuggetItem)
                    .define('#', ingotItem)
                    .unlockedBy(getHasName(ingotItem), has(ingotItem))
                    .save(pWriter, recipeId);
        }
    }

    private void PlateArmorRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        var armorCategories = List.of(EItemCategory.Helmet,
                EItemCategory.Chestplate,
                EItemCategory.Leggings,
                EItemCategory.Boots);

        var leatherArmor = List.of(Items.LEATHER_HELMET,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_BOOTS);

        for(var armorCategory : armorCategories) {
            for (var armor : getItemInfosForCraftingRecipes(armorCategory)) {
                var mod_id = armor.has_default_basis ? "minecraft" : armor.mod_id;
                var registryKey = new ResourceLocation(mod_id, armor.basis);

                var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
                if (basisItem == null) continue;

                var tier_name = armor.basis;
                var recipeId = tier_name.substring(0, tier_name.lastIndexOf('_')) + "_" + armor.category.getName().toLowerCase();
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (Item)armor.item.get())
                        .pattern(" * ")
                        .pattern("*#*")
                        .pattern(" * ")
                        .define('#', leatherArmor.get(armorCategories.indexOf(armorCategory)))
                        .define('*', ItemsUtilsMethods.getItem(EItemCategory.Plate, armor.material_type))
                        .unlockedBy(getHasName(basisItem), has(basisItem))
                        .save(pWriter, recipeId);
            }
        }
    }

    private void ChainmailArmorRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        var armorCategories = List.of(EItemCategory.ChainmailHelmet, 
                EItemCategory.ChainmailChestplate, 
                EItemCategory.ChainmailLeggings, 
                EItemCategory.ChainmailBoots);
        
        var leatherArmor = List.of(Items.LEATHER_HELMET, 
                Items.LEATHER_CHESTPLATE, 
                Items.LEATHER_LEGGINGS, 
                Items.LEATHER_BOOTS);
        
        for(var armorCategory : armorCategories) {
            for (var armor : getItemInfosForCraftingRecipes(armorCategory)) {
                var mod_id = armor.has_default_basis ? "minecraft" : armor.mod_id;
                var registryKey = new ResourceLocation(mod_id, armor.basis);

                var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
                if (basisItem == null) continue;

                var tier_name = armor.basis;
                var recipeId = tier_name.substring(0, tier_name.lastIndexOf('_')) + "_chainmail_" + armor.category.getName().toLowerCase();
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (Item)armor.item.get())
                        .pattern(" * ")
                        .pattern("*#*")
                        .pattern(" * ")
                        .define('#', leatherArmor.get(armorCategories.indexOf(armorCategory)))
                        .define('*', ItemsUtilsMethods.getItem(EItemCategory.Chainmail, armor.material_type))
                        .unlockedBy(getHasName(basisItem), has(basisItem))
                        .save(pWriter, recipeId);
            }
        }
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
    
    private void PickaxeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var pickaxe : getItemInfosForCraftingRecipes(EItemCategory.PickAxe)) {
            var mod_id = pickaxe.has_default_basis ? "minecraft" : pickaxe.mod_id;
            var registryKey = new ResourceLocation(mod_id, pickaxe.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = pickaxe.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + pickaxe.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)pickaxe.item.get())
                    .pattern("***")
                    .pattern(" # ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void AxeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var axe : getItemInfosForCraftingRecipes(EItemCategory.Axe)) {
            var mod_id = axe.has_default_basis ? "minecraft" : axe.mod_id;
            var registryKey = new ResourceLocation(mod_id, axe.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = axe.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + axe.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)axe.item.get())
                    .pattern("** ")
                    .pattern("*# ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    private void SwordRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var sword : getItemInfosForCraftingRecipes(EItemCategory.Sword)) {
            var mod_id = sword.has_default_basis ? "minecraft" : sword.mod_id;
            var registryKey = new ResourceLocation(mod_id, sword.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = sword.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + sword.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)sword.item.get())
                    .pattern(" * ")
                    .pattern(" * ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    private void ShovelRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var shovel : getItemInfosForCraftingRecipes(EItemCategory.Shovel)) {
            var mod_id = shovel.has_default_basis ? "minecraft" : shovel.mod_id;
            var registryKey = new ResourceLocation(mod_id, shovel.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = shovel.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + shovel.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)shovel.item.get())
                    .pattern(" * ")
                    .pattern(" # ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    private void HoeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var hoe : getItemInfosForCraftingRecipes(EItemCategory.Hoe)) {
            var mod_id = hoe.has_default_basis ? "minecraft" : hoe.mod_id;
            var registryKey = new ResourceLocation(mod_id, hoe.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = hoe.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + hoe.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)hoe.item.get())
                    .pattern("** ")
                    .pattern(" # ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void KnifeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var knife : getItemInfosForCraftingRecipes(EItemCategory.Knife)) {
            var mod_id = knife.has_default_basis ? "minecraft" : knife.mod_id;
            var registryKey = new ResourceLocation(mod_id, knife.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = knife.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + knife.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)knife.item.get())
                    .pattern("   ")
                    .pattern(" * ")
                    .pattern(" # ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void MacheteRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var machete : getItemInfosForCraftingRecipes(EItemCategory.Machete)) {
            var mod_id = machete.has_default_basis ? "minecraft" : machete.mod_id;
            var registryKey = new ResourceLocation(mod_id, machete.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = machete.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + machete.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)machete.item.get())
                    .pattern("  *")
                    .pattern(" * ")
                    .pattern("#  ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    private void LongSwordRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var longsword : getItemInfosForCraftingRecipes(EItemCategory.LongSword)) {
            var mod_id = longsword.has_default_basis ? "minecraft" : longsword.mod_id;
            var longswordResourceLocation = new ResourceLocation(mod_id, longsword.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(longswordResourceLocation);
            if (basisItem == null) continue;

            var tier_name = longsword.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + longsword.category.getName().toLowerCase();
            
            var swordResourceLocation = new ResourceLocation(mod_id, tier_name + "_" + "sword");
            var swordItem = ForgeRegistries.ITEMS.getValue(swordResourceLocation);
            if (swordItem == null) continue;
            
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item)longsword.item.get())
                    .pattern("  *")
                    .pattern("*S ")
                    .pattern("#* ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .define('S', swordItem)
                    .unlockedBy(getHasName(swordItem), has(swordItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void EndGameArmorRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        var armorCategories = List.of(EItemCategory.Helmet,
                EItemCategory.Chestplate,
                EItemCategory.Leggings,
                EItemCategory.Boots);
        for (var armorCategory : armorCategories) {
            for (var armor : getItemInfosForCraftingRecipes(armorCategory, true)) {
                var basis = armor.basis;
                var mod_id = armor.has_default_basis ? "minecraft" : armor.mod_id;
                var basisIngotResourceLocation = new ResourceLocation(mod_id, basis);

                var basisItem = ForgeRegistries.ITEMS.getValue(basisIngotResourceLocation);
                if (basisItem == null) continue;
                
                var recipeId = armor.material_type.GetName(true) + "_" + 
                        armor.sub_material_type.GetName() + "_" + 
                        armor.category.getName().toLowerCase();
                
                var armorResourceLocation = ForgeRegistries.ITEMS.getKey((Item)armor.item.get());
                if(armorResourceLocation == null) continue;
                
                var baseMetalArmorResourceLocation = new ResourceLocation(BetterProgression.MOD_ID, armorResourceLocation.getPath().substring(armorResourceLocation.getPath().indexOf("_") + 1));
                var baseMetalArmor = ForgeRegistries.ITEMS.getValue(baseMetalArmorResourceLocation);
                if(baseMetalArmor == null) continue;


                //noinspection removal
                LegacyUpgradeRecipeBuilder.smithing(
                                Ingredient.of(baseMetalArmor),
                                Ingredient.of(basisItem),
                                RecipeCategory.COMBAT,
                                (Item) armor.item.get())
                        .unlocks(getHasName(basisItem), has(basisItem))
                        .save(pWriter, recipeId);
            }
        }
    }

    private void EndGameChainmailRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        var armorCategories = List.of(EItemCategory.ChainmailHelmet,
                EItemCategory.ChainmailChestplate,
                EItemCategory.ChainmailLeggings,
                EItemCategory.ChainmailBoots);
        for (var chainmailCategory : armorCategories) {
            for (var chainmail : getItemInfosForCraftingRecipes(chainmailCategory, true)) {
                var basis = chainmail.basis;
                var mod_id = chainmail.has_default_basis ? "minecraft" : chainmail.mod_id;
                var basisIngotResourceLocation = new ResourceLocation(mod_id, basis);

                var basisItem = ForgeRegistries.ITEMS.getValue(basisIngotResourceLocation);
                if (basisItem == null) continue;

                var recipeId = chainmail.material_type.GetName(true) + "_" +
                        chainmail.sub_material_type.GetName() + "_" +
                        chainmail.category.getName().toLowerCase();

                var armorResourceLocation = ForgeRegistries.ITEMS.getKey((Item)chainmail.item.get());
                if(armorResourceLocation == null) continue;

                var baseMetalArmorResourceLocation = new ResourceLocation(BetterProgression.MOD_ID, armorResourceLocation.getPath().substring(armorResourceLocation.getPath().indexOf("_") + 1));
                var baseMetalArmor = ForgeRegistries.ITEMS.getValue(baseMetalArmorResourceLocation);
                if(baseMetalArmor == null) continue;

                //noinspection removal
                LegacyUpgradeRecipeBuilder.smithing(
                                Ingredient.of(baseMetalArmor),
                                Ingredient.of(basisItem),
                                RecipeCategory.COMBAT,
                                (Item) chainmail.item.get())
                        .unlocks(getHasName(basisItem), has(basisItem))
                        .save(pWriter, recipeId);
            }
        }
    }
    
    private void BattleAxeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var battleaxe : getItemInfosForCraftingRecipes(EItemCategory.BattleAxe)) {
            var mod_id = battleaxe.has_default_basis ? "minecraft" : battleaxe.mod_id;
            var longswordResourceLocation = new ResourceLocation(mod_id, battleaxe.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(longswordResourceLocation);
            if (basisItem == null) continue;

            var tier_name = battleaxe.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + battleaxe.category.getName().toLowerCase();

            var battleaxeResourceLocation = new ResourceLocation(mod_id, tier_name + "_" + "battleaxe");
            var battleaxeItem = ForgeRegistries.ITEMS.getValue(battleaxeResourceLocation);
            if (battleaxeItem == null) continue;

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item) battleaxe.item.get())
                    .pattern(" *S")
                    .pattern(" #*")
                    .pattern("#  ")
                    .define('#', ModItems.HILT.get())
                    .define('*', basisItem)
                    .define('S', battleaxeItem)
                    .unlockedBy(getHasName(battleaxeItem), has(battleaxeItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void DaggerRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var dagger : getItemInfosForCraftingRecipes(EItemCategory.Dagger)) {
            var builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item) dagger.item.get())
                    .pattern("   ")
                    .pattern(" * ")
                    .pattern(" # ")
                    .define('#', Tags.Items.RODS_WOODEN);
            var recipeId = dagger.tag == null ? defineBasisItemSchema(dagger, builder) : defineBasisTagSchema(dagger, builder);
            if(recipeId.isEmpty()) continue;

            builder.save(pWriter, recipeId);
        }
    }

    private void SpearRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var spear : getItemInfosForCraftingRecipes(EItemCategory.Spear)) {
            var builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item) spear.item.get())
                    .pattern("  *")
                    .pattern(" # ")
                    .pattern("#  ")
                    .define('#', Tags.Items.RODS_WOODEN);
            var recipeId = spear.tag == null ? defineBasisItemSchema(spear, builder) : defineBasisTagSchema(spear, builder);
            if(recipeId.isEmpty()) continue;

            builder.save(pWriter, recipeId);
        }
    }
    
    private void ClubRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var club : getItemInfosForCraftingRecipes(EItemCategory.Club)) {
            var builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item) club.item.get())
                    .pattern(" * ")
                    .pattern(" * ")
                    .pattern(" # ")
                    .define('#', Tags.Items.RODS_WOODEN);
            var recipeId = club.tag == null ? defineBasisItemSchema(club, builder) : defineBasisTagSchema(club, builder);
            if(recipeId.isEmpty()) continue;

            builder.save(pWriter, recipeId);
        }
    }
    
    private void AutomatedBlocksRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var data : ModBlocks.BLOCKS_DATA) {
            data.SaveRecipes(pWriter);
        }
    }

    private void SmeltingRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        oreGeneric(pWriter, List.of(getItem(EItemCategory.RawMaterial, EMaterialType.TIN)), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.END_TIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 1.4f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.STANNIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.DEEPSLATE_STANNIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.PINK_QUARTZ_ORE.get()), RecipeCategory.MISC, ModItems.PINK_QUARTZ.get(), 0.7f, 200, "better_progression");
    }


    private String defineBasisItemSchema(ItemInfo<Item> item, ShapedRecipeBuilder builder){
        var basisItem = getBasisItem(item);
        if (basisItem == null) return "";

        var tier_name = item.basis;
        if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
        var recipeId = tier_name + "_" + item.category.getName().toLowerCase();

        builder.define('*', basisItem)
                .unlockedBy(getHasName(basisItem), has(basisItem));

        return recipeId;
    }

    private String defineBasisTagSchema(ItemInfo<Item> item, ShapedRecipeBuilder builder){
        var tag = item.tag;
        if (tag == null) return "";

        var tier_name = item.basis;
        if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
        var recipeId = tier_name + "_" + item.category.getName().toLowerCase();

        builder.define('*', tag)
                .unlockedBy(item.basis, has(tag));

        return recipeId;
    }
    
    private Item getBasisItem(ItemInfo<Item> item) {
        var mod_id = item.has_default_basis ? "minecraft" : item.mod_id;
        var registryKey = new ResourceLocation(mod_id, item.basis);

        return ForgeRegistries.ITEMS.getValue(registryKey);
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_AXE)
                .pattern("** ")
                .pattern("*# ")
                .pattern(" # ")
                .define('*', Items.IRON_INGOT)
                .define('#', ModItems.HILT.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter, "better_iron_axe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_HOE)
                .pattern("** ")
                .pattern(" # ")
                .pattern(" # ")
                .define('*', Items.IRON_INGOT)
                .define('#', ModItems.HILT.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter, "better_iron_hoe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_SHOVEL)
                .pattern(" * ")
                .pattern(" # ")
                .pattern(" # ")
                .define('*', Items.IRON_INGOT)
                .define('#', ModItems.HILT.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter, "better_iron_shovel");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_SWORD)
                .pattern(" * ")
                .pattern(" * ")
                .pattern(" # ")
                .define('#', ModItems.HILT.get())
                .define('*', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter, "better_iron_sword");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_PICKAXE)
                .pattern("***")
                .pattern(" # ")
                .pattern(" # ")
                .define('*', Items.IRON_INGOT)
                .define('#', ModItems.HILT.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter, "better_iron_pickaxe");
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.TNT)
                .requires(Blocks.SAND, 2)
                .requires(Items.GUNPOWDER, 2)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER)).save(pWriter, "better_tnt");
        
        var enderiteIngot = getItem(EItemCategory.Ingot, EMaterialType.ENDERITE);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENDERITE_BLOCK.get())
                .requires(enderiteIngot, 9)
                .unlockedBy(getHasName(enderiteIngot), has(enderiteIngot)).save(pWriter, "enderite_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, enderiteIngot, 9)
                .requires(ModBlocks.ENDERITE_BLOCK.get())
                .unlockedBy(getHasName(enderiteIngot), has(ModItems.DRAGON_REMAINS.get())).save(pWriter, "enderite_ingot_from_block");

        var tinIngot = getItem(EItemCategory.Ingot, EMaterialType.TIN);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, tinIngot, 9)
                .requires(ModBlocks.TIN_BLOCK.get(), 1)
                .unlockedBy(getHasName(tinIngot), has(tinIngot)).save(pWriter, "tin_ingot_from_block");
        
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
        
        PickaxeRecipeCreator(pWriter);
        AxeRecipeCreator(pWriter);
        SwordRecipeCreator(pWriter);
        ShovelRecipeCreator(pWriter);
        HoeRecipeCreator(pWriter);
        KnifeRecipeCreator(pWriter);
        MacheteRecipeCreator(pWriter);
        LongSwordRecipeCreator(pWriter);
        BattleAxeRecipeCreator(pWriter);
        DaggerRecipeCreator(pWriter);
        SpearRecipeCreator(pWriter);
        ClubRecipeCreator(pWriter);
        PlateArmorRecipeCreator(pWriter);
        ChainmailArmorRecipeCreator(pWriter);
        MetalRecipesCollectionCreator(pWriter);
        ChainmailRecipeCreator(pWriter);
        PlateRecipeCreator(pWriter);
        EndGameArmorRecipeCreator(pWriter);
        EndGameChainmailRecipeCreator(pWriter);
        SmeltingRecipeCreator(pWriter);
        AutomatedBlocksRecipeCreator(pWriter);
    }
    
    protected static void oreGeneric(Consumer<FinishedRecipe> pWriter, List<ItemLike> items, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreSmelting(pWriter, items, pCategory, pResult, pExperience, pCookingTIme, pGroup);
        oreBlasting(pWriter, items, pCategory, pResult, pExperience * 2, pCookingTIme / 2, pGroup);
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
