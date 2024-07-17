package net.yuhi.better_progression.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public abstract class BetterFurnaceRecipe implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    private final CookingBookCategory category;
    protected final Ingredient firstIngredient;
    protected final Ingredient secondIngredient;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookingTime;

    protected BetterFurnaceRecipe(RecipeType<?> type, ResourceLocation id, CookingBookCategory category, Ingredient firstIngredient, Ingredient secondIngredient, ItemStack result, float experience, int cookingTime) {
        this.type = type;
        this.id = id;
        this.category = category;
        this.firstIngredient = firstIngredient;
        this.secondIngredient = secondIngredient;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if(pLevel.isClientSide()) return false;

        var firstItem = pContainer.getItem(0);
        var secondItem = pContainer.getItem(1);
        
        if (firstItem.isEmpty() && secondItem.isEmpty()) return false;
        
        if (!firstItem.isEmpty()) return this.firstIngredient.test(firstItem) || this.secondIngredient.test(firstItem);
        
        if (!secondItem.isEmpty()) return this.firstIngredient.test(secondItem) || this.secondIngredient.test(firstItem);
        
        var firstCompatibility = this.firstIngredient.test(firstItem) || this.firstIngredient.test(secondItem);
        var secondCompatibility = this.secondIngredient.test(firstItem) || this.secondIngredient.test(secondItem);
        
        return firstCompatibility && secondCompatibility;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.firstIngredient);
        nonnulllist.add(this.secondIngredient);
        return nonnulllist;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }
 
    public CookingBookCategory category() {
        return this.category;
    }
}
