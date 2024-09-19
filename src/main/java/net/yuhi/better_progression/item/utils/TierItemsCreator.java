package net.yuhi.better_progression.item.utils;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.*;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TierItemsCreator {
    private final EMaterialType material_type;
    private Tier tier;
    private String basis;
    private String modId = BetterProgression.MOD_ID;
    private EMaterialType sub_material_type;
    private TagKey<Item> basis_tag = null;
    private List<ItemTagPair> tags = new ArrayList<>();
    private boolean has_default_basis = false;
    private boolean is_upgrade_tier = false;
    
    public record ItemTagPair(EItemCategory category, TagKey<Item> tag) {}
    
    public static RegistryObject<Item> RegisterSimpleItem(RegistryObject<Item> supplier) {
        var itemInfo = new ItemInfo(supplier);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
        return supplier;
    }

    public static RegistryObject<Item> RegisterSimpleItem(RegistryObject<Item> supplier, List<TagKey<Item>> tags) {
        var itemInfo = new ItemInfo(supplier);
        itemInfo.tags.addAll(tags);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
        return supplier;
    }
    
    public TierItemsCreator(EMaterialType material_type) {
        this.material_type = material_type;
    }

    public TierItemsCreator(String basis, EMaterialType material_type, Tier tier) {
        this.basis = basis;
        this.tier = tier;
        this.material_type = material_type;
    }

    public TierItemsCreator(TagKey<Item> basis_tag, EMaterialType material_type, Tier tier) {
        this.basis_tag = basis_tag;
        this.tier = tier;
        this.material_type = material_type;
    }
    
    public TierItemsCreator SetModId(String modId) {
        this.modId = modId;
        return this;
    }
    
    public TierItemsCreator SetSubMaterialType(EMaterialType sub_material_type) {
        this.sub_material_type = sub_material_type;
        is_upgrade_tier = true;
        return this;
    }
    
    public TierItemsCreator SetHasDefaultBasis() {
        has_default_basis = true;
        return this;
    }
    
    public TierItemsCreator AddTag(EItemCategory category, TagKey<Item> tag) {
        this.tags.add(new ItemTagPair(category, tag));
        return this;
    }

    public TierItemsCreator AddTag(List<EItemCategory> categories, TagKey<Item> tag) {
        for(EItemCategory category : categories) {
            this.tags.add(new ItemTagPair(category, tag));
        }
        return this;
    }
    
    private String getItemName(EItemCategory category) {
        return is_upgrade_tier ? category.getFullName(material_type, sub_material_type) : category.getFullName(material_type);
    }

    public void createToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Axe -> new AxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Sword -> new SwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case PickAxe -> new PickaxeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Hoe -> new HoeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            case Shovel -> new ShovelItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Machete -> new MacheteItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };
        
        var itemName = getItemName(itemCategory);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeld, this);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createBigToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod, double attackReach) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case LongSword -> new LongSwordItem(tier, (int) damageMod, attackSpeedMod, attackReach, new Item.Properties());
            case BattleAxe -> new BattleAxeItem(tier, damageMod, attackSpeedMod, attackReach, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        var itemName = getItemName(itemCategory);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeldBig, this);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createSpearItem(float damageMod, float attackSpeedMod, double attackReach) {
        var itemCategory = EItemCategory.Spear;
        var itemName = is_upgrade_tier ? itemCategory.getFullName(material_type, sub_material_type) : itemCategory.getFullName(material_type);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, () ->
                new SpearItem(tier, (int)damageMod, attackSpeedMod, attackReach, new Item.Properties()));
        var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.Spear, this);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    public void createSimpleToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            case Club -> new ClubItem(tier, damageMod, attackSpeedMod, new Item.Properties());
            case Dagger -> new DaggerItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
            default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
        };

        var itemName = getItemName(itemCategory);        
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeld, this);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }
    
    public void createHorseArmor(int protection) {
        var armorResourceLocation = new ResourceLocation(BetterProgression.MOD_ID, "textures/entity/horse/armor/horse_armor_" + material_type.GetName() + ".png");
        Supplier<Item> itemSupplier = () -> new HorseArmorItem(protection, armorResourceLocation, new Item.Properties());
        var itemName = getItemName(EItemCategory.HorseArmor);
        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, itemSupplier);
        var itemInfo = new ItemInfo(registryItem, EItemCategory.HorseArmor, EItemType.Simple, this);
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
            var itemName = getItemName(category);
            RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, item);
            var itemInfo = new ItemInfo(registryItem, category, EItemType.Armor, this);  
            ModItems.REGISTERED_ITEMS.add(itemInfo);
        };
        
        createArmorSet(registerArmorItem, false);
    }

    public void createChainmailArmorSet() {
        java.util.function.BiConsumer<Supplier<ArmorItem>, EItemCategory> registerArmorItem = (item, category) -> {
            var itemName = getItemName(category);   
            RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemName, item);
            var itemInfo = new ItemInfo(registryItem, category, EItemType.Chainmail, this);
            ModItems.REGISTERED_ITEMS.add(itemInfo);
        };

        createArmorSet(registerArmorItem, true);
    }

    public void createBasicItem(EItemCategory itemCategory) {
        Supplier<Item> itemSupplier = () -> switch (itemCategory) {
            default -> new Item(new Item.Properties());
        };

        RegistryObject<Item> registryItem = ModItems.ITEMS.register(itemCategory.getFullName(material_type), itemSupplier);
        var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.Simple, this);
        ModItems.REGISTERED_ITEMS.add(itemInfo);
    }

    @SuppressWarnings("removal")
    public static final class ItemInfo {
        public final RegistryObject<Item> item;
        public final EItemCategory category;
        public final EItemType type;
        public final String mod_id;
        public final String basis;
        public final EMaterialType material_type;
        public final EMaterialType sub_material_type;
        private final ETieredItemCraftingCategory crafting_category;
        public final Tier tier;
        public final TagKey<Item> basis_tag;
        public final List<TagKey<Item>> tags;
        public final boolean has_default_basis;
        public final boolean is_upgrade;

        public ItemInfo(RegistryObject<Item> item, EItemCategory category, EItemType itemType, TierItemsCreator creator) {
            this.item = item;
            this.category = category;
            this.type = itemType;
            this.mod_id = creator.modId;
            this.material_type = creator.material_type;
            this.sub_material_type = creator.sub_material_type;
            this.basis = creator.basis;
            this.tier = creator.tier;
            this.basis_tag = creator.basis_tag;
            this.has_default_basis = creator.has_default_basis;
            this.is_upgrade = creator.is_upgrade_tier;
            this.tags = creator.tags.stream().filter(e -> e.category == category).map(e -> e.tag).toList();
            this.crafting_category = Arrays.stream(ETieredItemCraftingCategory.values()).filter(c -> c.IsValidRecipeForItem(this)).findFirst().orElse(null);
        }

        public ItemInfo(RegistryObject<Item> item) {
            this.item = item;
            this.category = null;
            this.type = EItemType.Simple;
            this.mod_id = BetterProgression.MOD_ID;
            this.basis =  null;
            this.tier = null;
            this.basis_tag =  null;
            this.material_type =  null;
            this.sub_material_type = null;
            this.has_default_basis = false;
            this.is_upgrade = false;
            this.crafting_category = null;
            this.tags = new ArrayList<>();
        }
        
        public void SaveRecipes(Consumer<FinishedRecipe> pWriter) {
            if (crafting_category == null) return;

            crafting_category.SaveRecipes(pWriter, this);
        }
    }
}