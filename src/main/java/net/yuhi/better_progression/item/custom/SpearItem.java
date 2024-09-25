package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.yuhi.better_progression.item.interfaces.ReachItem;

import java.util.List;

public class SpearItem extends ThrowableItem implements ReachItem {
    public static List<Enchantment> POSSIBLE_ENCHANTMENTS = List.of(Enchantments.LOYALTY,
            Enchantments.FIRE_ASPECT,
            Enchantments.MENDING,
            Enchantments.SHARPNESS,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.UNBREAKING,
            Enchantments.MOB_LOOTING,
            Enchantments.SMITE);
    
    private final double attack_reach;
    
    public SpearItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, double pAttackReach, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, 10, false, 1.25f, pProperties.stacksTo(1));
        this.attack_reach = pAttackReach;
    }

    @Override
    public double getReach(ItemStack stack) {
        return this.attack_reach;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SWEEPING_EDGE) return false;
        return POSSIBLE_ENCHANTMENTS.contains(enchantment) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        for (var set : EnchantmentHelper.getEnchantments(book).entrySet()) {
            if (POSSIBLE_ENCHANTMENTS.contains(set.getKey())) return true;
        }
        return super.isBookEnchantable(stack, book);
    }
}
