package net.yuhi.better_progression.item.enums;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.yuhi.better_progression.BetterProgression;

import java.util.function.Supplier;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public enum EModArmorMaterial implements ArmorMaterial, StringRepresentable {
    BRONZE("bronze", 15, new int[]{ 4, 9, 7, 4 }, 20, SoundEvents.ARMOR_EQUIP_GOLD, 3.0f, 0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.COPPER))),
    STEEL("steel", 33, new int[]{ 3, 8, 6, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.1f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL))),
    COPPER("copper", 10, new int[]{ 1, 4, 3, 1 }, 15,SoundEvents.ARMOR_EQUIP_IRON, 0, 0, () -> Ingredient.of(Items.COPPER_INGOT));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final static int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    EModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return BetterProgression.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public String getSerializedName() {
        return BetterProgression.MOD_ID + ":" + this.name;
    }
}
