package net.yuhi.better_progression.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class BetterSimpleCookingSerializer<T extends AbstractBetterCookingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final BetterSimpleCookingSerializer.CookieBaker<T> factory;
    public BetterSimpleCookingSerializer(BetterSimpleCookingSerializer.CookieBaker<T> pFactory, int pDefaultCookingTime) {
        this.defaultCookingTime = pDefaultCookingTime;
        this.factory = pFactory;
    }

    public @NotNull T fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
        String s = GsonHelper.getAsString(pJson, "group", "");
        CookingBookCategory cookingbookcategory = CookingBookCategory.CODEC.byName(GsonHelper.getAsString(pJson, "category", (String)null), CookingBookCategory.MISC);
        JsonElement jsonelement = (JsonElement)(GsonHelper.isArrayNode(pJson, "ingredient") ? GsonHelper.getAsJsonArray(pJson, "ingredient") : GsonHelper.getAsJsonObject(pJson, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        Ingredient secondIngredient = pJson.has("secondIngredient") ? Ingredient.fromJson((JsonElement)(GsonHelper.isArrayNode(pJson, "secondIngredient") ? GsonHelper.getAsJsonArray(pJson, "secondIngredient") : GsonHelper.getAsJsonObject(pJson, "secondIngredient"))) : Ingredient.EMPTY;
        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!pJson.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack;
        if (pJson.get("result").isJsonObject()) itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
        else {
            String s1 = GsonHelper.getAsString(pJson, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack = new ItemStack(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() -> 
                    new IllegalStateException("Item: " + s1 + " does not exist")));
        }
        float experience = GsonHelper.getAsFloat(pJson, "experience", 0.0F);
        int cookingTime = GsonHelper.getAsInt(pJson, "cookingtime", this.defaultCookingTime);
        int ingredientCount = GsonHelper.getAsInt(pJson, "ingredientcount", 1);
        int secondIngredientCount = GsonHelper.getAsInt(pJson, "secondingredientcount", 1);
        return this.factory.create(pRecipeId, s, cookingbookcategory, ingredient, secondIngredient, itemstack, experience, cookingTime, ingredientCount, secondIngredientCount);
    }

    public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        String s = pBuffer.readUtf();
        CookingBookCategory cookingbookcategory = pBuffer.readEnum(CookingBookCategory.class);
        Ingredient firstIngredient = Ingredient.fromNetwork(pBuffer);
        Ingredient secondIngredient = Ingredient.fromNetwork(pBuffer);
        ItemStack itemstack = pBuffer.readItem();
        float experience = pBuffer.readFloat();
        int cookingTime = pBuffer.readVarInt();
        int ingredientCount = pBuffer.readVarInt();
        int secondIngredientCount = pBuffer.readVarInt();
        return this.factory.create(pRecipeId, s, cookingbookcategory, firstIngredient, secondIngredient, itemstack, experience, cookingTime, ingredientCount, secondIngredientCount);
    }

    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pBuffer.writeEnum(pRecipe.category());

        var ingredtients = pRecipe.getIngredients();
        ingredtients.get(0).toNetwork(pBuffer);
        ingredtients.get(1).toNetwork(pBuffer);

        pBuffer.writeItem(pRecipe.getResultItem());
        pBuffer.writeFloat(pRecipe.getExperience());
        pBuffer.writeVarInt(pRecipe.getCookingTime());
        
        pBuffer.writeInt(pRecipe.getIngredientCount(0));
        pBuffer.writeInt(pRecipe.getIngredientCount(1));
    }

    interface CookieBaker<T extends AbstractBetterCookingRecipe> {
        T create(ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, Ingredient pSecondIngredient, ItemStack pResult, float pExperience, int pCookingTime, int pIngredientCount, int pSecondIngredientCount);
    }
}
