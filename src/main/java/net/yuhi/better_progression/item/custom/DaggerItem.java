package net.yuhi.better_progression.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.yuhi.better_progression.item.interfaces.Lootable;
import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.List;


public class DaggerItem extends ThrowableItem implements Lootable<Animal> {
    public static List<Enchantment> POSSIBLE_ENCHANTMENTS = List.of(Enchantments.LOYALTY,
            Enchantments.FIRE_ASPECT,
            Enchantments.MENDING,
            Enchantments.SHARPNESS,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.UNBREAKING,
            Enchantments.MOB_LOOTING,
            Enchantments.SMITE);
    
    public DaggerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, 1, true, pProperties.stacksTo(1));
    }

    @Override
    public Class<Animal> getEntityClass() {
        return Animal.class;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return lootEnemy(pStack, pTarget, pAttacker, super.hurtEnemy(pStack, pTarget, pAttacker));
    }

    @Override
    public boolean lootEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker, boolean defaultRes) {
        return Lootable.super.lootEnemy(pStack, pTarget, pAttacker, defaultRes);
    }

    @Override
    public SwordItem getSwordItem() {
        return this;
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
