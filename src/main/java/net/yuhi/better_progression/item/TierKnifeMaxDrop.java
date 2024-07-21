package net.yuhi.better_progression.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public enum TierKnifeMaxDrop {
    STONE(Tiers.STONE, 1),
    IRON(Tiers.IRON, 2),
    DIAMOND(Tiers.DIAMOND, 5),
    COPPER(ModTiers.COPPER, 2),
    STEEL(ModTiers.STEEL, 4),
    BRONZE(ModTiers.BRONZE, 6);

    private final Tier tier;
    private final int level;

    TierKnifeMaxDrop(Tier tier, int level) {
        this.tier = tier;
        this.level = level;
    }

    public Tier getTier() {
        return tier;
    }

    public int getDrop() {
        return level;
    }

    public static int getTierDrop(Tier tier) {
        for (TierKnifeMaxDrop tierLevel : values()) {
            if (tierLevel.getTier() == tier) {
                return tierLevel.getDrop();
            }
        }
        return 0;
    }
}