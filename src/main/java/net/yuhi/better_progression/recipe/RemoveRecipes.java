package net.yuhi.better_progression.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.util.ArrayList;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getVanillaTools;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RemoveRecipes {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        RecipeManager recipeManager = server.getRecipeManager();
        removeVanillaRecipes(recipeManager);
    }

    private static void removeVanillaRecipes(RecipeManager recipeManager) {
        EMaterialType[] vanillaToolMaterials = {
                EMaterialType.IRON,
                EMaterialType.STONE,
                EMaterialType.WOOD,
                EMaterialType.DIAMOND
        };
        
        var recipesToRemove = new ArrayList<ResourceLocation>();
        for (var material : vanillaToolMaterials) {
            for (var tool : getVanillaTools()) {
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