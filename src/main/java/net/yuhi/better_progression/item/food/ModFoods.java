package net.yuhi.better_progression.item.food;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties COOKED_BEAR_MEAT = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.2F).meat().build();
    public static final FoodProperties BEAR_MEAT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).meat().build();
}
