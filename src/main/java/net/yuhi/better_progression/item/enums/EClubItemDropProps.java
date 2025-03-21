package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.*;
import net.yuhi.better_progression.item.ModTiers;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public enum EClubItemDropProps {
    WOOD(Tiers.WOOD, 1, 0),
    STONE(Tiers.STONE, 2, 3),
    FLINT(ModTiers.FLINT, 3, 3),
    DIAMOND(Tiers.DIAMOND, 3, 4),
    OBSYDIAN(ModTiers.OBSIDIAN, 4, 10);
    
    private final Tier tier;
    private final int count;
    private final int level;
    
    private static List<ClubDropData> drops = new ArrayList<>(List.of(
            new ClubDropData(Items.FLINT, 0),
            new ClubDropData(Items.COAL, 1),
            new ClubDropData(getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 2),
            new ClubDropData(Items.RAW_IRON, 3),
            new ClubDropData(Items.RAW_COPPER, 3),
            new ClubDropData(Items.RAW_GOLD, 4),
            new ClubDropData(Items.DIAMOND, 10)
    ));
    
    EClubItemDropProps(Tier tier, int count, int level) {
        this.tier = tier;
        this.level = level;
        this.count = count;
    }
    
    public static List<ItemStack> getDrops(Tier tier) {
        for (EClubItemDropProps type : EClubItemDropProps.values()) {
            if (type.tier == tier) return getDrops(type);
        }
        
        return Collections.emptyList();
    }
    
    private static List<ItemStack> getDrops(EClubItemDropProps clubType) {
        var items = new ArrayList<ItemStack>();
        for (var i = 0; i < ItemsUtilsMethods.getCount(0, clubType.count); i++) {
            var level = ItemsUtilsMethods.getCount(0, clubType.level);
            var possibleDrops = drops.stream().filter(d -> d.level <= clubType.level).sorted(Comparator.comparingInt(a -> level)).toList();
            var randomIndex = ThreadLocalRandom.current().nextInt(0, possibleDrops.size());
            var itemStack = possibleDrops.get(randomIndex).drop;
            addOrUpdateItemStack(items, new ItemStack(itemStack));
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

    private record ClubDropData(Item drop, int level) {
    }

    private record DropChancePair(ItemStack drop, float weight) {
    }
}
