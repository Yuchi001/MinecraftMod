package net.yuhi.better_progression.item.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public interface LayerableItem {
    public static final String TIN_COUNT_KEY = "TinCount";
    public static final String GOLD_COUNT_KEY = "GoldCount";
    public static final String ENDERITE_COUNT_KEY = "EnderiteCount";
    public static final String NETHERITE_COUNT_KEY = "NetheriteCount";
    public static final int ADDITIONAL_DURABILITY_FOR_TIN_INGOT = 10;
    public static final float ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT = 0.1f;
    public static final float ADDITIONAL_ATTACK_SPEED_FOR_ENDERITE_INGOT = 0.1f;
    public static final float ADDITIONAL_ATTACK_DAMAGE_FOR_NETHERITE_INGOT = 0.1f;
    
    static int getTinCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TIN_COUNT_KEY) ? tag.getInt(TIN_COUNT_KEY) : 0;
    }

    static void addTinCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount + count * ADDITIONAL_DURABILITY_FOR_TIN_INGOT);
    }

    static float getGoldCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(GOLD_COUNT_KEY) ? tag.getFloat(GOLD_COUNT_KEY) : 0;
    }

    static void addGoldCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        float currentCount = tag.getFloat(GOLD_COUNT_KEY);
        tag.putFloat(GOLD_COUNT_KEY, currentCount + count * ADDITIONAL_ENCHANTABILITY_FOR_GOLD_INGOT);
    }

    static float getNetheriteCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(NETHERITE_COUNT_KEY) ? tag.getFloat(NETHERITE_COUNT_KEY) : 0;
    }

    static void addNetheriteCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        float currentCount = tag.getFloat(NETHERITE_COUNT_KEY);
        tag.putFloat(NETHERITE_COUNT_KEY, currentCount + count * ADDITIONAL_ATTACK_DAMAGE_FOR_NETHERITE_INGOT);
    }

    static float getEnderiteCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(ENDERITE_COUNT_KEY) ? tag.getFloat(ENDERITE_COUNT_KEY) : 0;
    }

    static void addEnderiteCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        float currentCount = tag.getFloat(ENDERITE_COUNT_KEY);
        tag.putFloat(ENDERITE_COUNT_KEY, currentCount + count * ADDITIONAL_ATTACK_SPEED_FOR_ENDERITE_INGOT);
    }

    static void onUse(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int currentCount = tag.getInt(TIN_COUNT_KEY);
        tag.putInt(TIN_COUNT_KEY, currentCount - 1);
    }
    
    static Component getTinComponent(ItemStack stack) {
        var count = getTinCount(stack);
        if(count == 0) return null;

        var component = Component.translatable("tooltip.better_progression.tined_item.tooltip").getString();
        component = component.replace("<v1>", String.valueOf(count));

        return Component.literal(component).setStyle(Style.EMPTY.withColor(TextColor.parseColor("#b9a59b")));
    }
    
    static Component getGoldComponent(ItemStack stack) {
        var count = getGoldCount(stack);
        if(count == 0) return null;

        var component = Component.translatable("tooltip.better_progression.golded_item.tooltip").getString();
        component = component.replace("<v1>", String.format("%.1f", count));

        return Component.literal(component).setStyle(Style.EMPTY.withColor(TextColor.parseColor("#FdF55F")));
    }

    static Component getNetheriteComponent(ItemStack stack) {
        var count = getNetheriteCount(stack);
        if(count == 0) return null;

        var component = Component.translatable("tooltip.better_progression.netherited_item.tooltip").getString();
        component = component.replace("<v1>", String.format("%.1f", count));

        return Component.literal(component).setStyle(Style.EMPTY.withColor(TextColor.parseColor("#31292a")));
    }

    static Component getEnderiteComponent(ItemStack stack) {
        var count = getEnderiteCount(stack);
        if(count == 0) return null;

        var component = Component.translatable("tooltip.better_progression.enderited_item.tooltip").getString();
        component = component.replace("<v1>", String.format("%.1f", count));

        return Component.literal(component).setStyle(Style.EMPTY.withColor(TextColor.parseColor("#652a50")));
    }

    static ArrayList<Component> getLayeredDescription(ItemStack stack) {
        var components = new ArrayList<Component>();
        
        var tinComponent = getTinComponent(stack);
        var goldComponent = getGoldComponent(stack);
        var netheriteComponent = getNetheriteComponent(stack);
        var enderiteComponent = getEnderiteComponent(stack);
        
        if (tinComponent != null) components.add(tinComponent);
        if (goldComponent != null) components.add(goldComponent);
        if (enderiteComponent != null) components.add(enderiteComponent);
        if (netheriteComponent != null) components.add(netheriteComponent);
        
        return components;
    }
    
    static List<Item> getLayerItems(){
        return List.of(
                getItem(EItemCategory.Ingot, EMaterialType.TIN),
                getItem(EItemCategory.Ingot, EMaterialType.ENDERITE),
                Items.GOLD_INGOT,
                Items.NETHERITE_INGOT
        );
    }

    static List<Item> getEndGameLayerItems(){
        return List.of(
                getItem(EItemCategory.Ingot, EMaterialType.ENDERITE),
                Items.NETHERITE_INGOT
        );
    }
    
    static boolean isLayerableWithEndGameIngots(ItemStack itemStack) {
        var resourceLocation = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if(resourceLocation == null) return false;
        
        var path = resourceLocation.getPath();
        System.out.println(itemStack);
        return path.startsWith("ender") || path.startsWith("nether");
    }
}
