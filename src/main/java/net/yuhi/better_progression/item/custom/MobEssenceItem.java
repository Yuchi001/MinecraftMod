package net.yuhi.better_progression.item.custom;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType.getHasName;
import static net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType.has;

public class MobEssenceItem extends Item {
    private final int stacks;
    private final String mobName;
    private final Supplier<Item> ingredient;
    
    public MobEssenceItem(String mobName, int stacks, Supplier<Item> ingredient, Properties pProperties) {
        super(pProperties);
        this.stacks = stacks;
        this.ingredient = ingredient;
        this.mobName = mobName;
    }

    public int getStacks() {
        return stacks;
    }
    
    public Item getIngredient() {
        return ingredient.get();
    }
    
    public String getItemName() {
        return mobName + "_essence";
    }
    
    public Item getSpawnEgg() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(mobName + "_spawn_egg"));
    }
    
    public void SaveRecipe(Consumer<FinishedRecipe> consumer) {
        var ingredientItem = getIngredient();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this, 2)
                .requires(ingredientItem, 8)
                .requires(Items.GHAST_TEAR, 1)
                .unlockedBy(getHasName(ingredientItem), has(ingredientItem))
                .save(consumer, getItemName());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getSpawnEgg())
                .requires(this)
                .requires(Items.EGG, 1)
                .unlockedBy(getHasName(ingredientItem), has(ingredientItem))
                .save(consumer, mobName + "_spawn_egg_from_essence");
    }
}
