package net.yuhi.better_progression.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public interface LayerableItem {
    public static final String TIN_COUNT_KEY = "TinCount";
    public static final String GOLD_COUNT_KEY = "GoldCount";
    public static final int ADDITIONAL_DURABILITY_FOR_TIN_INGOT = 10;
    public static final float ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT = 0.1f;
    
    public static int getTinCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TIN_COUNT_KEY) ? tag.getInt(TIN_COUNT_KEY) : 0;
    }

    public static void addTinCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount + count * ADDITIONAL_DURABILITY_FOR_TIN_INGOT);
    }

    public static float getGoldCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(GOLD_COUNT_KEY) ? tag.getFloat(GOLD_COUNT_KEY) : 0;
    }

    public static void addGoldCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        float currentCount = tag.getFloat(GOLD_COUNT_KEY);
        tag.putFloat(GOLD_COUNT_KEY, currentCount + count * ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT);
    }

    public static void onUse(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount - 1);
    }
    
    static ArrayList<Component> getTinComponents(ItemStack stack) {
        ArrayList<Component> components = new ArrayList<>();
        var count = getTinCount(stack);
        if(count == 0) return components;

        var part1 = Component.translatable("tooltip.better_progression.tined_item.tooltip_general").getString();
        var part2 = Component.translatable("tooltip.better_progression.tined_item.tooltip_additional").getString();
        part1 = part1.replace("<v1>", String.valueOf((int) Math.ceil((double)count / ADDITIONAL_DURABILITY_FOR_TIN_INGOT)));
        part1 = part1.replace("<v2>", count > 1 ? "s" : "");
        part2 = part2.replace("<v1>", String.valueOf(count));

        var comp1 = Component.literal(part1).setStyle(
                Style.EMPTY.withColor(TextColor.parseColor("#b9a59b"))
                        .withBold(true)
        );
        var comp2 = Component.literal(part2).setStyle(
                Style.EMPTY.withColor(TextColor.parseColor("#b9a59b"))
        );

        return new ArrayList<>(Arrays.asList(comp1, comp2));
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    static ArrayList<Component> getGoldComponents(ItemStack stack) {
        ArrayList<Component> components = new ArrayList<>();
        var count = getGoldCount(stack);
        if(count == 0) return components;

        var part1 = Component.translatable("tooltip.better_progression.golded_item.tooltip_general").getString();
        var part2 = Component.translatable("tooltip.better_progression.golded_item.tooltip_additional").getString();
        part1 = part1.replace("<v1>", String.valueOf((int) Math.ceil((double)count / ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT)));
        part1 = part1.replace("<v2>", count / ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT > 1 ? "s" : "");
        part2 = part2.replace("<v1>", String.valueOf(count));

        var comp1 = Component.literal(part1).setStyle(
                Style.EMPTY.withColor(TextColor.parseColor("#FdF55F"))
                        .withBold(true)
        );
        var comp2 = Component.literal(part2).setStyle(
                Style.EMPTY.withColor(TextColor.parseColor("#FdF55F"))
        );

        return new ArrayList<>(Arrays.asList(comp1, comp2));
    }

    public static ArrayList<Component> getLayeredDescription(ItemStack stack) {
        var components = new ArrayList<Component>();
        
        var tinComponents = getTinComponents(stack);
        var goldComponents = getGoldComponents(stack);
        components.addAll(tinComponents);
        components.addAll(goldComponents);
        
        return components;
    }
}
