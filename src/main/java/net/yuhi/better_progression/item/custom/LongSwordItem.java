package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.interfaces.TwoHandedItem;

public class LongSwordItem extends SwordItem implements TwoHandedItem {
    public LongSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
