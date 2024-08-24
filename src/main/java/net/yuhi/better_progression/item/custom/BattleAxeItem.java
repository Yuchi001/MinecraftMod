package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.interfaces.TwoHandedItem;

public class BattleAxeItem extends AxeItem implements TwoHandedItem {
    private final double attack_reach;
    
    public BattleAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, double pAttackReach, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.attack_reach = pAttackReach;
    }


    @Override
    public double getReach(ItemStack stack) {
        return attack_reach;
    }
}
