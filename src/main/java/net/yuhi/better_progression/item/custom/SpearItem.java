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
    private static final List<Enchantment> UNIQUE_ENCHANTMENTS = List.of(Enchantments.LOYALTY);

    private static final List<Enchantment> DEFAULT_ENCHANTMENTS = List.of(Enchantments.UNBREAKING,
            Enchantments.BLOCK_EFFICIENCY,
            Enchantments.BLOCK_FORTUNE,
            Enchantments.FIRE_ASPECT,
            Enchantments.SHARPNESS,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.MOB_LOOTING,
            Enchantments.SMITE);

    private static List<Enchantment> getPossibleEnchantments(boolean book) {
        var enchantments = UNIQUE_ENCHANTMENTS;
        if (book) enchantments.addAll(DEFAULT_ENCHANTMENTS);
        return enchantments;
    }
    
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
        return getPossibleEnchantments(false).contains(enchantment) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        for (var set : EnchantmentHelper.getEnchantments(book).entrySet()) {
            if (getPossibleEnchantments(true).contains(set.getKey())) return true;
        }
        return super.isBookEnchantable(stack, book);
    }
}
