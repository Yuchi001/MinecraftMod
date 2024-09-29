package net.yuhi.better_progression.utils;

import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.filterCompatibleEnchantments;

public class BetterEnchantmentHelper {
    private static int getMinLevel(int pSlot) {
        return switch (pSlot) {
            case 0 -> 1;
            case 1 -> 3;
            case 2 -> 4;
            default -> 0;
        };
    }
    
    private static Enchantment.Rarity getMaxRarity(int pSlot) {
        return switch (pSlot) {
            case 1 -> Enchantment.Rarity.UNCOMMON;
            case 2 -> Enchantment.Rarity.VERY_RARE;
            default -> Enchantment.Rarity.COMMON;
        };
    }

    public static List<EnchantmentInstance> selectBookEnchantment(RandomSource pRandom, ItemStack pItemStack, int pLevel, boolean pAllowTreasure, int pEnchantmentSlot) {
        // Ustal minimalny poziom i maksymalną rzadkość dla tego slotu
        int minLevel = getMinLevel(pEnchantmentSlot);
        Enchantment.Rarity maxRarity = getMaxRarity(pEnchantmentSlot);

        // Tworzymy listę na wynikowe enchanty
        List<EnchantmentInstance> resultList = Lists.newArrayList();

        // Filtrujemy dostępne enchanty według rzadkości i innych kryteriów
        List<Enchantment> availableEnchantments = BuiltInRegistries.ENCHANTMENT.stream()
                .filter(enchantment -> enchantment.getRarity().compareTo(maxRarity) <= 0) // Filtrujemy według rzadkości
                .filter(enchantment -> enchantment.isDiscoverable() && (!enchantment.isTreasureOnly() || pAllowTreasure)) // Odkrywalne i zgodne z regułami skarbu
                .filter(enchantment -> enchantment.canApplyAtEnchantingTable(pItemStack) || pItemStack.is(Items.BOOK) && enchantment.isAllowedOnBooks()) // Czy można zastosować na itemie lub książce
                .toList();

        try {
            if (!availableEnchantments.isEmpty()) {
                Enchantment selectedEnchantment = availableEnchantments.get(pRandom.nextInt(availableEnchantments.size()));

                if (selectedEnchantment != null) {
                    var randomLevel = pRandom.nextInt((5 - minLevel) + 1) + minLevel;
                    var maxEnchantLevel = selectedEnchantment.getMaxLevel();
                    if (randomLevel > maxEnchantLevel) randomLevel = maxEnchantLevel;
                    resultList.add(new EnchantmentInstance(selectedEnchantment, randomLevel));
                }
            }
        } catch (Exception ignored) {}

        return resultList;
    }
    
    public static int getEnchantmentCost(int pEnchantmentSlot) {
        return switch (pEnchantmentSlot) {
            case 0 -> 20;
            case 1 -> 25;
            case 2 -> 30;
            default -> 0;
        };
    }
}
