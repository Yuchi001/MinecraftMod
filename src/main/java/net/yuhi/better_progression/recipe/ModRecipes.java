package net.yuhi.better_progression.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterProgression.MOD_ID);
    
    public static final RegistryObject<RecipeSerializer<FurnaceAlloyRecipe>> FURNACE_ALLOY = 
            RECIPES.register("furnace_alloy", () -> FurnaceAlloyRecipe.Serializer.INSTANCE);
    
    public static void register(IEventBus bus) {
        RECIPES.register(bus);
    }
}
