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

    private void ArmorRecipeCreator(Consumer<FinishedRecipe> pWriter) {
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
                var registryKey = new ResourceLocation(armor.mod_id, armor.basis);

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

    private void ChainmailRecipeCreator(Consumer<FinishedRecipe> pWriter) {
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
                var registryKey = new ResourceLocation(armor.mod_id, armor.basis);

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

    private void NuggetRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var nugget : getItemInfosForCraftingRecipes(EItemCategory.Nugget)) {
            var mod_id = nugget.has_default_basis ? "minecraft" : nugget.mod_id;
            var registryKey = new ResourceLocation(mod_id, nugget.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = nugget.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + nugget.category.getName().toLowerCase();
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (Item)nugget.item.get(), 9)
                    .requires(basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    
    private void PickAxeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
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
    private void SmeltingRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        oreGeneric(pWriter, List.of(getItem(EItemCategory.RawMaterial, EMaterialType.TIN)), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
        oreGeneric(pWriter, List.of(ModBlocks.STANNIN_ORE.get()), RecipeCategory.MISC, getItem(EItemCategory.Ingot, EMaterialType.TIN), 0.7f, 200, "better_progression");
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
        
        PickAxeRecipeCreator(pWriter);
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
        ArmorRecipeCreator(pWriter);
        ChainmailRecipeCreator(pWriter);
        NuggetRecipeCreator(pWriter);
        SmeltingRecipeCreator(pWriter);
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
