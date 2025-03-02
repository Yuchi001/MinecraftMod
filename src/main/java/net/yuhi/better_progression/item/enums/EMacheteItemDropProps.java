package net.yuhi.better_progression.item.enums;

import com.mojang.datafixers.Typed;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.yuhi.better_progression.item.ModTiers;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;

public enum EMacheteItemDropProps {
    OBSIDIAN(ModTiers.OBSIDIAN, 1),
    DIAMOND(Tiers.DIAMOND, 3),
    STONE(Tiers.STONE, 5);
    
    private final int dropChance;
    private final Tier tier;
    
    EMacheteItemDropProps(Tier tier, int dropChance) {
        this.dropChance = dropChance;
        this.tier = tier;
    }
    
    public static boolean dropSkull(Tier tier) {
        for (EMacheteItemDropProps type : EMacheteItemDropProps.values()) {
            if (type.tier == tier) return ItemsUtilsMethods.getCount(0, type.dropChance) == type.dropChance;
        }
        return false;
    }
}
