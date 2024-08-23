package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.Tier;

public class SpearItem extends ThrowableItem {
    public SpearItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, 10, false, pProperties);
    }
}
