package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.interfaces.ReachItem;

public class SpearItem extends ThrowableItem implements ReachItem {
    private final double attack_reach;
    
    public SpearItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, double pAttackReach, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, 10, false, 1.25f, pProperties);
        this.attack_reach = pAttackReach;
    }

    @Override
    public double getReach(ItemStack stack) {
        return this.attack_reach;
    }
}
