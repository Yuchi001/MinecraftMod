package net.yuhi.better_progression.item.enums;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;

import java.util.function.Supplier;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public enum EModArmorMaterial implements ArmorMaterial, StringRepresentable, BetterArmorMaterial {
    GOLDEN_CHAINMAIL("golden_chainmail",
            7,
            new int[]{ 3, 4, 4, 3 },
            25, SoundEvents.ARMOR_EQUIP_CHAIN,
            0,
            0, () -> Ingredient.of(Items.GOLD_INGOT),
            new int[]{ 2, 3, 3, 2 }),
    NETHER_BRONZE("nether_bronze",
            22,
            new int[]{ 2, 6, 4, 2 },
            20, SoundEvents.ARMOR_EQUIP_GOLD,
            1.0f,
            0.2f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)),
            new int[]{ 4, 6, 5, 3 }),
    NETHER_BRONZE_CHAINMAIL("nether_bronze_chainmail",
            22,
            new int[]{ 2, 3, 3, 2 },
            20, SoundEvents.ARMOR_EQUIP_CHAIN,
            0,
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)),
            new int[]{ 3, 4, 4, 3 }),
    NETHER_STEEL("nether_steel",
            40,
            new int[]{ 3, 8, 6, 3 },
            10, SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0f,
            0.3f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)),
            new int[]{ 3, 5, 4, 2 }),
    NETHER_STEEL_CHAINMAIL("nether_steel_chainmail",
            40,
            new int[]{ 3, 4, 4, 3 },
            10, SoundEvents.ARMOR_EQUIP_CHAIN,
            0,
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)),
            new int[]{ 2, 3, 3, 2 }),
    ENDER_BRONZE("ender_bronze",
            22,
            new int[]{ 2, 6, 4, 2 },
            20, SoundEvents.ARMOR_EQUIP_GOLD,
            1.0f,
            0.2f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.COPPER)),
            new int[]{ 4, 6, 5, 3 }),
    ENDER_BRONZE_CHAINMAIL("ender_bronze_chainmail",
            22,
            new int[]{ 2, 3, 3, 2 },
            20, SoundEvents.ARMOR_EQUIP_CHAIN,
            0,
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.COPPER)),
            new int[]{ 3, 4, 4, 3 }),
    ENDER_STEEL("ender_steel",
            40,
            new int[]{ 3, 8, 6, 3 },
            10, SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0f,
            0.3f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)),
            new int[]{ 3, 5, 4, 2 }),
    ENDER_STEEL_CHAINMAIL("ender_steel_chainmail",
            40,
            new int[]{ 3, 4, 4, 3 },
            10, SoundEvents.ARMOR_EQUIP_CHAIN,
            0,
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)),
            new int[]{ 2, 3, 3, 2 }),
    BRONZE("bronze",
            15, 
            new int[]{ 2, 6, 4, 2 }, 
            20, SoundEvents.ARMOR_EQUIP_GOLD, 
            1.0f, 
            0.2f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)),
            new int[]{ 4, 6, 5, 3 }),
    BRONZE_CHAINMAIL("bronze_chainmail",
            15, 
            new int[]{ 2, 3, 3, 2 }, 
            20, SoundEvents.ARMOR_EQUIP_CHAIN, 
            0, 
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.BRONZE)),
            new int[]{ 3, 4, 4, 3 }),
    STEEL("steel", 
            33, 
            new int[]{ 3, 8, 6, 3 }, 
            10, SoundEvents.ARMOR_EQUIP_DIAMOND, 
            2.0f, 
            0.3f, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)), 
            new int[]{ 3, 5, 4, 2 }),
    STEEL_CHAINMAIL("steel_chainmail",
            33, 
            new int[]{ 3, 4, 4, 3 }, 
            10, SoundEvents.ARMOR_EQUIP_CHAIN, 
            0, 
            0, () -> Ingredient.of(getItem(EItemCategory.Ingot, EMaterialType.STEEL)), 
            new int[]{ 2, 3, 3, 2 }),
    COPPER("copper", 
            10, 
            new int[]{ 1, 4, 3, 1 }, 
            15, SoundEvents.ARMOR_EQUIP_IRON, 
            0.5f, 
            0.1f, () -> Ingredient.of(Items.COPPER_INGOT), 
            new int[]{ 3, 5, 4, 2 }),
    COPPER_CHAINMAIL("copper_chainmail",
            10, 
            new int[]{ 1, 2, 2, 1 }, 
            15, SoundEvents.ARMOR_EQUIP_CHAIN, 
            0, 
            0, () -> Ingredient.of(Items.COPPER_INGOT), 
            new int[]{ 2, 3, 3, 2 });

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final int[] lifeMod;
    private final static int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    EModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, int[] lifeMod) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
        this.lifeMod = lifeMod;
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

    @Override
    public int getLifeMod(ArmorItem.Type type) {
        return lifeMod[type.ordinal()];
    }
}
