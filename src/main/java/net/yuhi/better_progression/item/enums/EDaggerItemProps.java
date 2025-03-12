package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.yuhi.better_progression.item.ModTiers;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;

public enum EDaggerItemProps {
    OBSIDIAN(ModTiers.OBSIDIAN, 5),
    DIAMOND(Tiers.DIAMOND, 3),
    STONE(Tiers.STONE, 1),
    FLINT(ModTiers.FLINT, 2);

    private final int dropCount;
    private final Tier tier;

    EDaggerItemProps(Tier tier, int dropCount) {
        this.dropCount = dropCount;
        this.tier = tier;
    }

    public static int getDropCount(Tier tier) {
        for (EDaggerItemProps type : EDaggerItemProps.values()) {
            if (type.tier == tier) return ItemsUtilsMethods.getCount(0, type.dropCount);
        }
        return 0;
    }
}
