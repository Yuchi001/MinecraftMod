package net.yuhi.better_progression.recipe;

import com.google.gson.JsonObject;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.LayerableItem;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class LayerRecipe extends LegacyUpgradeRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;


    public LayerRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
        super(pId, pBase, pAddition, pResult);
        addition = pAddition;
        base = pBase;
        result = pResult;
    }


    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, base, addition);
    }

    @Override
    public boolean matches(Container inv, @NotNull Level world) {
        return addition.test(inv.getItem(1)) && inv.getItem(0).getTags().anyMatch(t -> t == ModTags.Items.LAYERABLE_TAG);
    }

    @Override
    public @NotNull ItemStack assemble(Container inv, @NotNull RegistryAccess pRegistryAccess) {
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);
        ItemStack item = inv.getItem(0).copy();
        int tinCount = inv.countItem(tin_ingot);
        int goldCount = inv.countItem(Items.GOLD_INGOT);
        

        LayerableItem.addTinCount(item, tinCount);
        LayerableItem.addGoldCount(item, goldCount);

        return item;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(Container pContainer) {
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);
        for (var i = 0; i < pContainer.getContainerSize(); i++) {
            if(pContainer.getItem(i).is(tin_ingot)) pContainer.removeItem(i, pContainer.countItem(tin_ingot));
            if(pContainer.getItem(i).is(Items.GOLD_INGOT)) pContainer.removeItem(i, pContainer.countItem(Items.GOLD_INGOT));
        }
        return NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.LAYERED_ITEM_RECIPE_SERIALIZER;
    }

    public static class LayeredItemRecipeSerializer implements RecipeSerializer<LayerRecipe> {
        @Override
        public LayerRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new LayerRecipe(pRecipeId, ingredient, ingredient1, result);
        }

        @Override
        public LayerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new LayerRecipe(recipeId, ingredient, ingredient1, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, LayerRecipe recipe) {
            RecipeSerializer.SMITHING.toNetwork(buffer, recipe);
        }
    }
}