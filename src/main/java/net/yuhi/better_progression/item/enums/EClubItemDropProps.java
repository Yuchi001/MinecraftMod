package net.yuhi.better_progression.item.enums;

import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.item.*;
import net.yuhi.better_progression.item.ModTiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public enum EClubItemDropProps {
    WOOD(Tiers.WOOD, 0),
    STONE(Tiers.STONE, 1),
    DIAMOND(ModTiers.BETTER_DIAMOND, 3),
    OBSYDIAN(ModTiers.OBSIDIAN, 6);
    
    private final Tier tier;
    private final int level;
    
    private static List<ClubDropData> drops = new ArrayList<>(List.of(
            new ClubDropData(Items.FLINT, 0),
            new ClubDropData(Items.COAL, 0),
            new ClubDropData(Items.RAW_COPPER, 1),
            new ClubDropData(getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 1),
            new ClubDropData(Items.RAW_IRON, 1),
            new ClubDropData(Items.DIAMOND, 3),
            new ClubDropData(Items.NETHERITE_SCRAP, 6)
    ));
    
    EClubItemDropProps(Tier tier, int level) {
        this.tier = tier;
        this.level = level;
    }
    
    public static List<ItemStack> getDrops(Tier tier) {
        for (EClubItemDropProps type : EClubItemDropProps.values()) {
            if (type.tier == tier) return getDrops(type);
        }
        
        return Collections.emptyList();
    }
    
    private static List<ItemStack> getDrops(EClubItemDropProps clubType) {
        var possibleDrops = drops.stream().filter(d -> d.level <= clubType.level).sorted(Comparator.comparingInt(a -> a.level)).toList();
        var currentChance = 100f;
        var dropsWithChance = new ArrayList<>(List.of(new DropChancePair(ItemStack.EMPTY, currentChance)));
        var chanceSum = 0f;
        var dropMultiplier = 2f;
        var lastDropLevel = 0;
        for (var drop : possibleDrops) {
            if (lastDropLevel != drop.level) {
                currentChance /= dropMultiplier;
                dropMultiplier += (0.1f * drop.level);
                lastDropLevel = drop.level;
            }
            chanceSum += currentChance;
            dropsWithChance.add(new DropChancePair(new ItemStack(drop.drop), currentChance));
        }

        var items = new ArrayList<ItemStack>();
        for (var i = 0; i < clubType.level + 1; i++) {
            var itemStack = getDrop(dropsWithChance, chanceSum);
            addOrUpdateItemStack(items, itemStack);
        }

        return items;
    }

    private static void addOrUpdateItemStack(List<ItemStack> items, ItemStack newStack) {
        for (ItemStack existingStack : items) {
            if (ItemStack.isSameItemSameTags(existingStack, newStack)) {
                existingStack.grow(1);
                return;
            }
        }

        items.add(newStack);
    }
    
    private static ItemStack getDrop(List<DropChancePair> drops, float chanceSum) {
        var randomNum = UniformFloat.of(0f, chanceSum).sample(RandomSource.create());
        for(var item : drops) {
            if(randomNum <= item.weight) return new ItemStack(item.drop.getItem());
            randomNum -= item.weight;
        }
        return ItemStack.EMPTY;
    }

    private record ClubDropData(Item drop, int level) {
    }

    private record DropChancePair(ItemStack drop, float weight) {
    }
}
