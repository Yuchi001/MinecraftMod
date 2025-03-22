package net.yuhi.better_progression.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.block.entity.AlchemyTableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AlchemyTableRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final Ingredient fuel;

    public AlchemyTableRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result, Ingredient fuel) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.fuel = fuel;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!(container instanceof AlchemyTableBlockEntity entity)) return false;

        if (!entity.isValidFuel(fuel.getItems()[0])) return false;

        List<ItemStack> inputItems = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            if (container.getItem(i).isEmpty()) continue;
            inputItems.add(container.getItem(i));
        }

        List<Ingredient> ingredientsList = new ArrayList<>(ingredients);

        for (ItemStack stack : inputItems) {
            boolean matched = ingredientsList.removeIf(ingredient -> ingredient.test(stack));
            if (!matched) return false;
        }
        
        return ingredientsList.isEmpty();
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALCHEMY_TABLE_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeType.ALCHEMY.get();
    }
    
    public Ingredient getFuel() {
        return fuel;
    }

    public static class AlchemyTableRecipeSerializer implements RecipeSerializer<AlchemyTableRecipe> {
        @Override
        public AlchemyTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (JsonElement element : GsonHelper.getAsJsonArray(json, "ingredients")) {
                ingredients.add(Ingredient.fromJson(element));
            }
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            Ingredient fuel = Ingredient.fromJson(json.get("fuel"));

            return new AlchemyTableRecipe(id, ingredients, result, fuel);
        }

        @Override
        public @Nullable AlchemyTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            int ingredientCount = buffer.readVarInt();
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack result = buffer.readItem();
            Ingredient fuel = Ingredient.fromNetwork(buffer);

            return new AlchemyTableRecipe(id, ingredients, result, fuel);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyTableRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
            recipe.fuel.toNetwork(buffer);
        }
    }
}
