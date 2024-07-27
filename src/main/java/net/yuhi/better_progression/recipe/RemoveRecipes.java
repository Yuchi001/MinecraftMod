package net.yuhi.better_progression.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RemoveRecipes {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        RecipeManager recipeManager = server.getRecipeManager();
        removeVanillaRecipes(recipeManager);
    }

    private static void removeVanillaRecipes(RecipeManager recipeManager) {
        ModItems.EMaterialType[] vanillaToolMaterials = {
                ModItems.EMaterialType.IRON,
                ModItems.EMaterialType.STONE,
                ModItems.EMaterialType.WOOD,
                ModItems.EMaterialType.DIAMOND
        };
        
        var recipesToRemove = new ArrayList<ResourceLocation>();
        for (var material : vanillaToolMaterials) {
            for (var tool : ModItems.getVanillaTools()) {
                var recipeName = tool.getFullName(material.GetName());
                recipesToRemove.add(new ResourceLocation("minecraft", recipeName));
            }
                
        }

        var recipes = recipeManager.getRecipes();
        for (ResourceLocation recipeId : recipesToRemove) {
            recipes.removeIf(recipe -> recipe.getId().equals(recipeId));
        }
        recipeManager.replaceRecipes(recipes);
    }
}