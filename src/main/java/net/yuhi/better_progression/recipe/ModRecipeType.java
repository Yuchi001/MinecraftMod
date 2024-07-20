package net.yuhi.better_progression.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModRecipeType<T extends Recipe<?>> {
    public static class BetterSmeltingRecipeType implements RecipeType<BetterSmeltingRecipe> {
        public static final BetterSmeltingRecipeType BETTER_SMELTING = new BetterSmeltingRecipeType();
        public static final String ID = "smelting";
    }

    public static class BetterBlastingRecipeType implements RecipeType<BetterBlastingRecipe> {
        public static final BetterBlastingRecipeType BETTER_BLASTING = new BetterBlastingRecipeType();
        public static final String ID = "blasting";
    }

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
        DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "minecraft");

    public static final RegistryObject<RecipeType<BetterSmeltingRecipe>> BETTER_SMELTING =
            RECIPE_TYPES.register(BetterSmeltingRecipeType.ID, BetterSmeltingRecipeType::new);

    public static final RegistryObject<RecipeType<BetterBlastingRecipe>> BETTER_BLASTING =
            RECIPE_TYPES.register(BetterBlastingRecipeType.ID, BetterBlastingRecipeType::new);

    public static void register(IEventBus bus) {
        RECIPE_TYPES.register(bus);
    }
}
