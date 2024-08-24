package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.interfaces.ReachItem;
import net.yuhi.better_progression.item.interfaces.TwoHandedItem;

public class LongSwordItem extends SwordItem implements TwoHandedItem {
    private final double attack_reach;
    
    public LongSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, double pAttackReach, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.attack_reach = pAttackReach;
    }

    @Override
    public double getReach(ItemStack stack) {
        return this.attack_reach;
    }
}
