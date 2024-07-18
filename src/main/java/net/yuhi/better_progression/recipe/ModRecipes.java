package net.yuhi.better_progression.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModRecipes {
    public static final BetterSimpleCookingSerializer SMELTING_SERIALIZER = new BetterSimpleCookingSerializer(BetterSmeltingRecipe::new, 200);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = 
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterProgression.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> VANILLA_RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "minecraft");
    
    public static final RegistryObject<RecipeSerializer<BetterSmeltingRecipe>> BETTER_FURNACE =
            VANILLA_RECIPES.register("smelting", () -> SMELTING_SERIALIZER);
    
    public static void register(IEventBus bus) {
        RECIPES.register(bus);
        VANILLA_RECIPES.register(bus);
    }
}
