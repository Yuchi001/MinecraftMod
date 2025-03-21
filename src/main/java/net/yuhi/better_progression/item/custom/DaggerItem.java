package net.yuhi.better_progression.item.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.yuhi.better_progression.item.ModTiers;
import net.yuhi.better_progression.item.enums.EDaggerItemProps;
import net.yuhi.better_progression.item.interfaces.Lootable;
import org.jetbrains.annotations.NotNull;
import java.util.List;


public class DaggerItem extends ThrowableItem implements Lootable<Animal> {
    private static final List<Enchantment> UNIQUE_ENCHANTMENTS = List.of(Enchantments.LOYALTY);

    private static final List<Enchantment> DEFAULT_ENCHANTMENTS = List.of(Enchantments.UNBREAKING,
            Enchantments.BLOCK_EFFICIENCY,
            Enchantments.BLOCK_FORTUNE, 
            Enchantments.FIRE_ASPECT,
            Enchantments.SHARPNESS,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.MOB_LOOTING,
            Enchantments.SMITE);
    
    private static final List<Class> NON_LEATHER_ANIMALS = List.of(
            Bee.class,
            Axolotl.class,
            SkeletonHorse.class
    );

    private static List<Enchantment> getPossibleEnchantments(boolean book) {
        var enchantments = UNIQUE_ENCHANTMENTS;
        if (book) enchantments.addAll(DEFAULT_ENCHANTMENTS);
        return enchantments;
    }

    @Override
    public int modifyDropCount(Tier tier) {
        if (tier == Tiers.STONE) return 0;
        if (tier == Tiers.DIAMOND) return 1;
        if (tier == ModTiers.OBSIDIAN) return 3;
        return 0;
    }

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
    public ObjectArrayList<ItemStack> modifyDrop(ObjectArrayList<ItemStack> current, LivingEntity target) {
        if (target instanceof Chicken || target instanceof Parrot) {
            var randomNum = EDaggerItemProps.getDropCount(getTier()) + 1;
            current.add(new ItemStack(Items.FEATHER, randomNum));
            return current;
        }
        
        if(target instanceof ZombieHorse) {
            var randomNum = EDaggerItemProps.getDropCount(getTier()) + 1;
            current.add(new ItemStack(Items.ROTTEN_FLESH, randomNum));
            return current;
        }

        if(target instanceof Turtle) {
            var randomNum = EDaggerItemProps.getDropCount(getTier()) + 1;
            current.add(new ItemStack(Items.SCUTE, randomNum));
            return current;
        }
        
        if (NON_LEATHER_ANIMALS.contains(target.getClass())) return current;

        var randomNum = EDaggerItemProps.getDropCount(getTier()) + 1;
        current.add(new ItemStack(Items.LEATHER, randomNum));
        return current;
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
