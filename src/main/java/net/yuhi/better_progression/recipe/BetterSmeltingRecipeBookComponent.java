package net.yuhi.better_progression.recipe;

import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.yuhi.better_progression.block.BetterFurnace.AbstractBetterFurnaceBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BetterSmeltingRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
    @Nullable
    private Ingredient fuels;
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.smeltable");

    protected @NotNull Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    protected @NotNull Set<Item> getFuelItems() {
        return AbstractBetterFurnaceBlockEntity.getFuel().keySet();
    }

    @Override
    public void setupGhostRecipe(Recipe<?> pRecipe, List<Slot> pSlots) {
        ItemStack itemstack = pRecipe.getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(pRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), (pSlots.get(3)).x, (pSlots.get(3)).y);
        NonNullList<Ingredient> nonnulllist = pRecipe.getIngredients();
        Slot slot = pSlots.get(2);
        if (slot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().filter((p_274685_) -> {
                    return p_274685_.isEnabled(this.minecraft.level.enabledFeatures());
                }).map(ItemStack::new));
            }

            this.ghostRecipe.addIngredient(this.fuels, slot.x, slot.y);
        }

        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for(int i = 0; i < 3; ++i) {
            if (!iterator.hasNext()) {
                return;
            }

            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = pSlots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }

    }
}
