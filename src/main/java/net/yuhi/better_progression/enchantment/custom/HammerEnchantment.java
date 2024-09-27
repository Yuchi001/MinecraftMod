package net.yuhi.better_progression.enchantment.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HammerEnchantment extends Enchantment {
    private static final List<Enchantment> NOT_COMPATIBILE_ENCHANTMENTS = List.of(
            Enchantments.BLOCK_FORTUNE,
            Enchantments.BLOCK_EFFICIENCY,
            Enchantments.SILK_TOUCH
    );
    
    public HammerEnchantment(Enchantment.Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, EnchantmentCategory.DIGGER, pApplicableSlots);
    }

    @Override
    public boolean checkCompatibility(@NotNull Enchantment pEnchantment) {
        return super.checkCompatibility(pEnchantment) && NOT_COMPATIBILE_ENCHANTMENTS.stream().noneMatch(pEnchantment::equals);
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 15 + (pEnchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof PickaxeItem;
    }
}
