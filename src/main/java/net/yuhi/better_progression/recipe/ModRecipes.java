package net.yuhi.better_progression.recipe;

import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModRecipes {
    public static final BetterSimpleCookingSerializer BLASTING_SERIALIZER = new BetterSimpleCookingSerializer(BetterBlastingRecipe::new, 100);
    public static final LayerRecipe.LayeredItemRecipeSerializer LAYERED_ITEM_RECIPE_SERIALIZER = new LayerRecipe.LayeredItemRecipeSerializer();

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterProgression.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> VANILLA_RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "minecraft");

    public static final RegistryObject<RecipeSerializer<BetterBlastingRecipe>> BETTER_BLAST_FURNACE =
            VANILLA_RECIPES.register("blasting", () -> BLASTING_SERIALIZER);
    
    public static RegistryObject<RecipeSerializer<LayerRecipe>> LAYERED_ITEM_RECIPE =
            RECIPES.register("layering", () -> LAYERED_ITEM_RECIPE_SERIALIZER);

    public static void register(IEventBus bus) {
        RECIPES.register(bus);
        VANILLA_RECIPES.register(bus);
    }
}
