package net.yuhi.better_progression.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.TinedItem;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;

public class TinedItemRecipe extends ShapelessRecipe {
    public TinedItemRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, CraftingBookCategory.EQUIPMENT, result, ingredients);
    }

    @Override
    public boolean matches(CraftingContainer inv, @NotNull Level world) {
        boolean hasTinnableItem = false;
        boolean hasTinIngot = false;
        int slotsTaken = 0;
        for (var i = 0; i < inv.getContainerSize(); i++) {
            if(!inv.getItem(i).isEmpty()) slotsTaken++;
        }
        if (slotsTaken != 2) return false;

        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(ModTags.Items.TINNABLE_TAG)) {
                    hasTinnableItem = true;
                } else if (itemStack.getItem() == tin_ingot) {
                    hasTinIngot = true;
                }
            }
        }
        return hasTinnableItem && hasTinIngot;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess pRegistryAccess) {
        ItemStack item = ItemStack.EMPTY;
        int tinCount = 0;
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(ModTags.Items.TINNABLE_TAG)) {
                    item = itemStack.copy();
                } else if (itemStack.getItem() == tin_ingot) {
                    tinCount += itemStack.getCount();
                }
            }
        }

        if (!item.isEmpty()) {
            TinedItem.addTinCount(item, tinCount);
        }

        return item;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pContainer) {
        var tin_ingot = ModItems.getItem(ModItems.EItemCategory.Ingot, ModItems.EMaterialType.TIN);
        for (var i = 0; i < pContainer.getContainerSize(); i++) {
            if(!pContainer.getItem(i).is(tin_ingot)) continue;;
            pContainer.removeItem(i, pContainer.countItem(tin_ingot));
        }
        return NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.TINED_ITEM_RECIPE_SERIALIZER;
    }

    public static class TinnedItemRecipeSerializer implements RecipeSerializer<TinedItemRecipe> {
        @Override
        public TinedItemRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ShapelessRecipe recipe = RecipeSerializer.SHAPELESS_RECIPE.fromJson(recipeId, json);
            return new TinedItemRecipe(recipe.getId(), recipe.getGroup(), recipe.getResultItem(RegistryAccess.EMPTY), recipe.getIngredients());
        }

        @Override
        public TinedItemRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ShapelessRecipe recipe = RecipeSerializer.SHAPELESS_RECIPE.fromNetwork(recipeId, buffer);
            return new TinedItemRecipe(recipe.getId(), recipe.getGroup(), recipe.getResultItem(RegistryAccess.EMPTY), recipe.getIngredients());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TinedItemRecipe recipe) {
            RecipeSerializer.SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}