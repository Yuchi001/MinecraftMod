package net.yuhi.better_progression.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.TinedItem;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("removal")
public class TinedItemRecipe extends LegacyUpgradeRecipe {
    final Ingredient base;
    final Ingredient addition;


    public TinedItemRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
        super(pId, pBase, pAddition, pResult);
        addition = pAddition;
        base = pBase;
    }


    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, base, addition);
    }

    @Override
    public boolean matches(Container inv, @NotNull Level world) {
        return addition.test(inv.getItem(1)) && inv.getItem(0).getTags().anyMatch(t -> t == ModTags.Items.TINNABLE_TAG);
    }

    @Override
    public @NotNull ItemStack assemble(Container inv, @NotNull RegistryAccess pRegistryAccess) {
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);
        ItemStack item = inv.getItem(0);
        int tinCount = inv.countItem(tin_ingot);

        TinedItem.addTinCount(item, tinCount);

        return item;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(Container pContainer) {
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);
        for (var i = 0; i < pContainer.getContainerSize(); i++) {
            if(!pContainer.getItem(i).is(tin_ingot)) continue;
            pContainer.removeItem(i, pContainer.countItem(tin_ingot));
        }
        return NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.TINED_ITEM_RECIPE_SERIALIZER;
    }

    public static class TinnedItemRecipeSerializer implements RecipeSerializer<TinedItemRecipe> {
        @Override
        public TinedItemRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new TinedItemRecipe(pRecipeId, ingredient, ingredient1, result);
        }

        @Override
        public TinedItemRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new TinedItemRecipe(recipeId, ingredient, ingredient1, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TinedItemRecipe recipe) {
            RecipeSerializer.SMITHING.toNetwork(buffer, recipe);
        }
    }
}