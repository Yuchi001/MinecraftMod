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
import net.yuhi.better_progression.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
    
    private void PickAxeRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var pickaxe : ModItems.getItemInfos(ModItems.EItemCategory.PickAxe)) {
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
        for (var axe : ModItems.getItemInfos(ModItems.EItemCategory.Axe)) {
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
        for (var sword : ModItems.getItemInfos(ModItems.EItemCategory.Sword)) {
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
        for (var shovel : ModItems.getItemInfos(ModItems.EItemCategory.Shovel)) {
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
        for (var hoe : ModItems.getItemInfos(ModItems.EItemCategory.Hoe)) {
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
        for (var knife : ModItems.getItemInfos(ModItems.EItemCategory.Knife)) {
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
        for (var machete : ModItems.getItemInfos(ModItems.EItemCategory.Machete)) {
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
        for (var longsword : ModItems.getItemInfos(ModItems.EItemCategory.LongSword)) {
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
        for (var battleaxe : ModItems.getItemInfos(ModItems.EItemCategory.BattleAxe)) {
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
        for (var dagger : ModItems.getItemInfos(ModItems.EItemCategory.Dagger)) {
            var mod_id = dagger.has_default_basis ? "minecraft" : dagger.mod_id;
            var registryKey = new ResourceLocation(mod_id, dagger.basis);

            var basisItem = ForgeRegistries.ITEMS.getValue(registryKey);
            if (basisItem == null) continue;

            var tier_name = dagger.basis;
            if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
            var recipeId = tier_name + "_" + dagger.category.getName().toLowerCase();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (Item) dagger.item.get())
                    .pattern("   ")
                    .pattern(" * ")
                    .pattern(" # ")
                    .define('#', Tags.Items.RODS_WOODEN)
                    .define('*', basisItem)
                    .unlockedBy(getHasName(basisItem), has(basisItem))
                    .save(pWriter, recipeId);
        }
    }
    private void ClubRecipeCreator(Consumer<FinishedRecipe> pWriter) {
        for (var club : ModItems.getItemInfos(ModItems.EItemCategory.Club)) {
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
        oreGeneric(pWriter, List.of(ModItems.getItem(ModItems.EItemCategory.RawMaterial, ModItems.EMaterialType.TIN)), RecipeCategory.MISC, ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN), 0.7f, 200, "better_progression");
    }


    private String defineBasisItemSchema(ModItems.ItemInfo<Item> item, ShapedRecipeBuilder builder){
        var basisItem = getBasisItem(item);
        if (basisItem == null) return "";

        var tier_name = item.basis;
        if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
        var recipeId = tier_name + "_" + item.category.getName().toLowerCase();

        builder.define('*', basisItem)
                .unlockedBy(getHasName(basisItem), has(basisItem));

        return recipeId;
    }

    private String defineBasisTagSchema(ModItems.ItemInfo<Item> item, ShapedRecipeBuilder builder){
        var tag = item.tag;
        if (tag == null) return "";

        var tier_name = item.basis;
        if(tier_name.contains("_")) tier_name = tier_name.substring(0, tier_name.indexOf("_"));
        var recipeId = tier_name + "_" + item.category.getName().toLowerCase();

        builder.define('*', tag)
                .unlockedBy(item.basis, has(tag));

        return recipeId;
    }
    private Item getBasisItem(ModItems.ItemInfo<Item> item) {
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
                .define('t', ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN))
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(pWriter, "hilt");
        
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
        ClubRecipeCreator(pWriter);
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
