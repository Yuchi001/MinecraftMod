package net.yuhi.better_progression.item.utils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.*;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.interfaces.Lootable;

import java.util.function.Supplier;

public class TierItemsCreator {
    private String modId = BetterProgression.MOD_ID;
    private EMaterialType material_type;
    private EMaterialType sub_material_type;
    private Tier tier = null;
    private TagKey<Item> tag = null;
    private String basis = "";
    private boolean has_default_basis = false;
    private boolean is_upgrade_tier = false;

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

    public TierItemsCreator(String basis, EMaterialType material_type, EMaterialType sub_material_type, Tier tier) {
        this.tier = tier;
        this.basis = basis;
        this.material_type = material_type;
        this.sub_material_type = sub_material_type;
        this.is_upgrade_tier = true;
    }

    public TierItemsCreator(String basis, EMaterialType material_type, EMaterialType sub_material_type, Tier tier, boolean has_default_basis) {
        this.tier = tier;
        this.basis = basis;
        this.material_type = material_type;
        this.sub_material_type = sub_material_type;
        this.is_upgrade_tier = true;
        this.has_default_basis = has_default_basis;
        this.modId = "minecraft";
    }

    // endregion

    public void createToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Axe -> new AxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Sword -> new SwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case PickAxe -> new PickaxeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Hoe -> new HoeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Shovel -> new ShovelItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            //case Knife -> new Lootable<>(tier, (int) damageMod, attackSpeedMod, new Item.Properties(), Animal.class);
            case Machete -> new MacheteItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        var itemName = is_upgrade_tier ? itemCategory.getFullName(material_type, sub_material_type) : itemCategory.getFullName(material_type);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                : new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createBigToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod, double attackReach) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case LongSword -> new LongSwordItem(tier, (int) damageMod, attackSpeedMod, attackReach, new Item.Properties());
            case BattleAxe -> new BattleAxeItem(tier, damageMod, attackSpeedMod, attackReach, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        var itemName = is_upgrade_tier ? itemCategory.getFullName(material_type, sub_material_type) : itemCategory.getFullName(material_type);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeldBig, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                : new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeldBig, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createSpearItem(float damageMod, float attackSpeedMod, double attackReach) {
        var itemCategory = EItemCategory.Spear;
        var itemName = is_upgrade_tier ? itemCategory.getFullName(material_type, sub_material_type) : itemCategory.getFullName(material_type);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, () ->
                new SpearItem(tier, (int)damageMod, attackSpeedMod, attackReach, new Item.Properties()));
        var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, itemCategory, EItemType.Spear, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                : new ItemInfo<>(registryItem, itemCategory, EItemType.Spear, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createSimpleToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Club -> new ClubItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Dagger -> new DaggerItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        var itemName = is_upgrade_tier ? itemCategory.getFullName(material_type, sub_material_type) : itemCategory.getFullName(material_type);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                : new ItemInfo<>(registryItem, itemCategory, EItemType.HandHeld, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }
    
    private void createArmorSet(java.util.function.BiConsumer<Supplier<ArmorItem>, EItemCategory> registerArmorItem, boolean chainmail) {
        var armorMaterial = is_upgrade_tier ? ItemsUtilsMethods.materialToArmorMaterial(material_type, sub_material_type, chainmail) : ItemsUtilsMethods.materialToArmorMaterial(material_type, chainmail);
        if (armorMaterial == null) return;

        Supplier<ArmorItem> helmet = () -> new ArmorItem(armorMaterial, ArmorItem.Type.HELMET, new Item.Properties());
        Supplier<ArmorItem> chestplate = () -> new ArmorItem(armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties());
        Supplier<ArmorItem> leggings = () -> new ArmorItem(armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties());
        Supplier<ArmorItem> boots = () -> new ArmorItem(armorMaterial, ArmorItem.Type.BOOTS, new Item.Properties());

        registerArmorItem.accept(helmet, chainmail ? EItemCategory.ChainmailHelmet : EItemCategory.Helmet);
        registerArmorItem.accept(chestplate, chainmail ? EItemCategory.ChainmailChestplate : EItemCategory.Chestplate);
        registerArmorItem.accept(leggings, chainmail ? EItemCategory.ChainmailLeggings : EItemCategory.Leggings);
        registerArmorItem.accept(boots, chainmail ? EItemCategory.ChainmailBoots : EItemCategory.Boots);
    }

    public void createArmorSet() {
        java.util.function.BiConsumer<Supplier<ArmorItem>, EItemCategory> registerArmorItem = (item, category) -> {
            var itemName = is_upgrade_tier ? category.getFullName(material_type, sub_material_type) : category.getFullName(material_type);
            RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, item);
            var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, category, EItemType.Armor, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                    : new ItemInfo<>(registryItem, category, EItemType.Armor, modId, basis, tag, material_type, tier, has_default_basis);            
            ModItems.REGISTERED_ITEMS.add(itemInfo);
        };
        
        createArmorSet(registerArmorItem, false);
    }

    public void createChainmailArmorSet() {
        java.util.function.BiConsumer<Supplier<ArmorItem>, EItemCategory> registerArmorItem = (item, category) -> {
            var itemName = is_upgrade_tier ? category.getFullName(material_type, sub_material_type) : category.getFullName(material_type);
            RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, item);
            var itemInfo = is_upgrade_tier ? new ItemInfo<>(registryItem, category, EItemType.Chainmail, modId, basis, tag, material_type, sub_material_type, tier, has_default_basis)
                    : new ItemInfo<>(registryItem, category, EItemType.Chainmail, modId, basis, tag, material_type, tier, has_default_basis);
            ModItems.REGISTERED_ITEMS.add(itemInfo);
        };

        createArmorSet(registerArmorItem, true);
    }

    public void createBasicItem(EItemCategory itemCategory) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            default -> new Item(new Item.Properties());
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type), itemSupplier);
        var itemInfo = new ItemInfo<>(registryItem, itemCategory, EItemType.Simple, modId, basis, tag, material_type, tier, has_default_basis);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }
}