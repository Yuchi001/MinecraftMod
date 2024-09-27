package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.yuhi.better_progression.item.ModTiers;

import java.util.Random;

public enum EHoeItemDropProps {
    IRON(Tiers.IRON, 0.1f),
    COPPER(ModTiers.COPPER, 0.2f),
    STEEL(ModTiers.STEEL, 0.2f),
    BRONZE(ModTiers.BRONZE, 0.4f),
    ENDGAMESTEEL(ModTiers.ENDGAMESTEEL, 0.4f),
    ENDGAMEBRONZE(ModTiers.ENDGAMEBRONZE, 0.8f);


    private final Tier tier;
    private final float dropChance;

    EHoeItemDropProps(Tier tier, float dropChance) {
        this.tier = tier;
        this.dropChance = dropChance;
    }

    public Tier getTier() {
        return tier;
    }

    public float getDrop() {
        return dropChance;
    }

    private static float getChance(Tier tier, boolean isOak) {
        for (EHoeItemDropProps tierLevel : values()) {
            if (tierLevel.getTier() == tier) {
                return isOak ? tierLevel.getDrop() * 2f : tierLevel.getDrop();
            }
        }
        return 0;
    }

    public static int hoeDropCount(Tier tier, boolean isOak) {
        var chance = getChance(tier, isOak);
        var random = new Random();
        var successes = (int) chance;
        var fractionalChance = chance - successes;
        if (random.nextFloat() < fractionalChance) successes += 1;
        return successes;
    }
}
