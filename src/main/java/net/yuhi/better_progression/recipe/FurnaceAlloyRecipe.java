package net.yuhi.better_progression.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.yuhi.better_progression.BetterProgression;
import org.jetbrains.annotations.Nullable;

public class FurnaceAlloyRecipe extends BetterFurnaceRecipe {
    protected FurnaceAlloyRecipe(RecipeType<?> type, ResourceLocation id, CookingBookCategory category, Ingredient firstIngredient, Ingredient secondIngredient, ItemStack result, float experience, int cookingTime) {
        super(type, id, category, firstIngredient, secondIngredient, result, experience, cookingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FurnaceAlloyRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "furnace_alloy";
    }

    public static class Serializer implements RecipeSerializer<FurnaceAlloyRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static ResourceLocation ID = new ResourceLocation(BetterProgression.MOD_ID, "furnace_alloy");

        @Override
        public FurnaceAlloyRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            Ingredient firstIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "firstIngredient"));
            Ingredient secondIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "secondIngredient"));
            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience");
            int cookingTime = GsonHelper.getAsInt(pSerializedRecipe, "cookingTime");

            return new FurnaceAlloyRecipe(new Type(), ID, CookingBookCategory.MISC, firstIngredient, secondIngredient, result, experience, cookingTime);
        }

        @Override
        public @Nullable FurnaceAlloyRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack result = pBuffer.readItem();
            Ingredient firstIngredient = Ingredient.fromNetwork(pBuffer);
            Ingredient secondIngredient = Ingredient.fromNetwork(pBuffer);
            float experience = pBuffer.readFloat();
            int cookingTime = pBuffer.readVarInt();

            return new FurnaceAlloyRecipe(Type.INSTANCE, ID, CookingBookCategory.MISC, firstIngredient, secondIngredient, result, experience, cookingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FurnaceAlloyRecipe pRecipe) {
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pRecipe.firstIngredient.toNetwork(pBuffer);
            pRecipe.secondIngredient.toNetwork(pBuffer);
            pBuffer.writeFloat(pRecipe.experience);
            pBuffer.writeVarInt(pRecipe.cookingTime);
        }
    }
}
