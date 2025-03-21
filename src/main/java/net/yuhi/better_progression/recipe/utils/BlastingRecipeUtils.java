package net.yuhi.better_progression.recipe.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.yuhi.better_progression.item.ModItems;

import java.util.Set;

public class BlastingRecipeUtils {
    private static final Set<Item> smeltableItems = Set.of(Items.COAL, 
            Items.NETHER_STAR, 
            Items.GHAST_TEAR, 
            Items.COBBLESTONE, 
            Items.ROTTEN_FLESH, 
            ModItems.POLISHED_PINK_QUARTZ.get(), 
            ModItems.POLISHED_QUARTZ.get());
    
    public static boolean CanSmelt(ItemStack stack) {
        return smeltableItems.stream().anyMatch(stack::is);
    }
}
