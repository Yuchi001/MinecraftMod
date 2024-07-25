package net.yuhi.better_progression.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public interface TinedItem {
    public static final String TIN_COUNT_KEY = "TinCount";
    public static final int ADDITIONAL_DURABILITY_FOR_TIN_INGOT = 10;
    public static int getTinCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TIN_COUNT_KEY) ? tag.getInt(TIN_COUNT_KEY) : 0;
    }

    public static void addTinCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount + count * ADDITIONAL_DURABILITY_FOR_TIN_INGOT);
    }

    public static void onUse(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount - 1);
    }

    public static ArrayList<Component> getTinedDescription(ItemStack stack) {
        var count = getTinCount(stack);
        if(count == 0) return null;

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
}
