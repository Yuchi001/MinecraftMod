package net.yuhi.better_progression.item.utils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.*;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.util.function.Supplier;

public class TierItemsCreator {
    private String modId = BetterProgression.MOD_ID;
    private EMaterialType material_type;
    private Tier tier = null;
    private TagKey<Item> tag = null;
    private String basis = "";
    private boolean has_default_basis = false;

    // region constructors
    public TierItemsCreator(EMaterialType material_type) {
        this.modId = BetterProgression.MOD_ID;
        this.material_type = material_type;
    }

    public TierItemsCreator(String mod_id, EMaterialType material_type) {
        this.modId = mod_id;
        this.material_type = material_type;
    }

    public TierItemsCreator(String modId, String basis, EMaterialType material_type, Tier tier) {
        this.modId = modId;
        this.basis = basis;
        this.tier = tier;
        this.material_type = material_type;
    }

    public TierItemsCreator(String basis, EMaterialType material_type, Tier tier) {
        this.tier = tier;
        this.basis = basis;
        this.material_type = material_type;
    }

    public TierItemsCreator(TagKey<Item> tag, EMaterialType material_type, Tier tier) {
        this.tier = tier;
        this.basis = tag.location().getPath().toLowerCase();
        this.tag = tag;
        this.material_type = material_type;
    }

    public TierItemsCreator(String basis, EMaterialType material_type, Tier tier, boolean has_default_basis) {
        this.tier = tier;
        this.has_default_basis = has_default_basis;
        this.basis = basis;
        this.material_type = material_type;
    }

    // endregion

    public void createToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Axe -> new LayerableAxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Sword -> new LayerableSwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case PickAxe -> new LayerablePickaxeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Hoe -> new LayerableHoeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Shovel -> new LayerableShovelItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Knife ->
                    new LootItem<>(tier, (int) damageMod, attackSpeedMod, new Item.Properties(), Animal.class);
            case Dagger -> new DaggerItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Machete ->
                    new LootItem<>(tier, (int) damageMod, attackSpeedMod, new Item.Properties(), Monster.class);
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
        var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createBigToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case LongSword -> new LongSwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case BattleAxe -> new BattleAxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
        var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeldBig, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createSimpleToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Club -> new ClubItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Dagger -> new DaggerItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
        var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createArmorSet() {
        var armorMaterial = ItemsUtilsMethods.materialToArmorMaterial(material_type);
        if (armorMaterial == null) return;

        var helmet = new ArmorItem(armorMaterial, ArmorItem.Type.HELMET, new Item.Properties());
        var chestplate = new ArmorItem(armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties());
        var leggings = new ArmorItem(armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties());
        var boots = new ArmorItem(armorMaterial, ArmorItem.Type.BOOTS, new Item.Properties());

        java.util.function.Consumer<ArmorItem> registerArmorItem = (item) -> {
            var name = material_type.GetName() + "_" + item.getType();
            var itemCategory = ItemsUtilsMethods.armorTypeToItemCategory(item.getType());
            RegistryObject<Item> registryItem = ModItems.ITEMS.register(name, () -> item);
            var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.Armor, modId, basis, tag, material_type, tier, has_default_basis);
            ModItems.REGISTERED_ITEMS.add(itemInfo);
        };

        registerArmorItem.accept(helmet);
        registerArmorItem.accept(chestplate);
        registerArmorItem.accept(leggings);
        registerArmorItem.accept(boots);
    }

    public void createBasicItem(EItemCategory itemCategory) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            default -> new Item(new Item.Properties());
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
        var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.Simple, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }
}