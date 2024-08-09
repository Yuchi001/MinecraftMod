package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.interfaces.TwoHandedItem;

public class BattleAxeItem extends AxeItem implements TwoHandedItem {
    public BattleAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
