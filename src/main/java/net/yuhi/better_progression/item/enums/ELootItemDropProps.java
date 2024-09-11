package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.yuhi.better_progression.item.ModTiers;

public enum ELootItemDropProps {
    STONE(Tiers.STONE, 1),
    IRON(Tiers.IRON, 2),
    DIAMOND(Tiers.DIAMOND, 5),
    OBSIDIAN(ModTiers.OBSIDIAN, 8),
    COPPER(ModTiers.COPPER, 2),
    STEEL(ModTiers.STEEL, 4),
    BRONZE(ModTiers.BRONZE, 6);

    private final Tier tier;
    private final int level;

    ELootItemDropProps(Tier tier, int level) {
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
        for (ELootItemDropProps tierLevel : values()) {
            if (tierLevel.getTier() == tier) {
                return tierLevel.getDrop();
            }
        }
        return 0;
    }
}