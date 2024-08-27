package net.yuhi.better_progression.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

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
    public boolean isSpecial() {
        return true;
    }


    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, base, addition);
    }

    @Override
    public boolean matches(Container inv, @NotNull Level world) {
        var itemResourceLocation = ForgeRegistries.ITEMS.getKey(inv.getItem(0).getItem());
        if (itemResourceLocation == null) return false;
        
        var itemPath = itemResourceLocation.getPath();
        var isNetherOrEnder = itemPath.startsWith("nether") || itemPath.startsWith("ender");
        
        var netherCheck = !addition.test(new ItemStack(Items.NETHERITE_INGOT)) || isNetherOrEnder;
        var enderCheck = !addition.test(new ItemStack(getItem(EItemCategory.Ingot, EMaterialType.ENDERITE))) || isNetherOrEnder;
        
        return addition.test(inv.getItem(1)) && 
                inv.getItem(0).getTags().anyMatch(t -> t == ModTags.Items.LAYERABLE_TAG) && 
                netherCheck && 
                enderCheck;
    }

    @Override
    public @NotNull ItemStack assemble(Container inv, @NotNull RegistryAccess pRegistryAccess) {
        var tin_ingot = getItem(EItemCategory.Ingot, EMaterialType.TIN);
        var enderite_ingot = getItem(EItemCategory.Ingot, EMaterialType.ENDERITE);
        
        ItemStack item = inv.getItem(0).copy();
        
        int tinCount = inv.countItem(tin_ingot);
        int goldCount = inv.countItem(Items.GOLD_INGOT);
        int netheriteCount = inv.countItem(Items.NETHERITE_INGOT);
        int enderiteCount = inv.countItem(enderite_ingot);

        LayerableItem.addTinCount(item, tinCount);
        LayerableItem.addGoldCount(item, goldCount);
        LayerableItem.addNetheriteCount(item, netheriteCount);
        LayerableItem.addEnderiteCount(item, enderiteCount);

        return item;
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