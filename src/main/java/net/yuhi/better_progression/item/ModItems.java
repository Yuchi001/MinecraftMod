package net.yuhi.better_progression.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, BetterProgression.MOD_ID);
    public static final List<ItemInfo<Item>>  REGISTERED_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> HILT = ITEMS.register("hilt", () -> new Item.Properties());
    
    // WOOD
    
    /*public static final RegistryObject<Item> WOODEN_CLUB = registerItem(
            ITEMS.register("wooden_club",
                    () -> new ClubItem(Tiers.WOOD, 3.0F, -3.2F, new Item.Properties())),
            EItemType.HandHeld
    );*/


    public static Item getItem(EItemCategory itemCategory, EMaterialType materialType) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory && i.material_type == materialType).findFirst().get().item.get();
    }
    
    public static List<Item> getItems(EItemCategory itemCategory) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static List<Item> getItems(EMaterialType materialType) {
        return REGISTERED_ITEMS.stream().filter(i -> i.material_type == materialType).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static List<Item> getTinnableTools() {
        var toolCategoryList = new ArrayList<>(List.of(
                EItemCategory.Hoe,
                EItemCategory.Sword,
                EItemCategory.Axe,
                EItemCategory.PickAxe,
                EItemCategory.Shovel,
                EItemCategory.Knife,
                EItemCategory.LongSword,
                EItemCategory.Machete,
                EItemCategory.BattleAxe
        ));
        return REGISTERED_ITEMS.stream().filter(i -> toolCategoryList.contains(i.category)).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static void createItems() {
        var copperSupplier = new TierItemsCreator("copper_ingot", EMaterialType.COPPER, ModTiers.COPPER);
        copperSupplier.createToolItem(EItemCategory.Axe, 5.5F, -3.2F);
        copperSupplier.createToolItem(EItemCategory.PickAxe, 1, -2.8f);
        copperSupplier.createToolItem(EItemCategory.Sword, 3, -2.4F);
        copperSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        copperSupplier.createToolItem(EItemCategory.Hoe, -1, -2.0F);
        copperSupplier.createToolItem(EItemCategory.Knife, 0.5F, -1.4F);
        copperSupplier.createBigToolItem(EItemCategory.BattleAxe, 11, -3.4F);
        copperSupplier.createBigToolItem(EItemCategory.LongSword, 6, -3F);

        var steelSupplier = new TierItemsCreator("steel_ingot", EMaterialType.STEEL, ModTiers.STEEL);
        //steelSupplier.createToolItem(EItemCategory.Axe, 5.5F, -3.2F);
        //steelSupplier.createToolItem(EItemCategory.PickAxe, 1, -2.8f);
        steelSupplier.createToolItem(EItemCategory.Sword, 3, -2.4F);
        //steelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        //steelSupplier.createToolItem(EItemCategory.Hoe, -1, -2.0F);
        //steelSupplier.createToolItem(EItemCategory.Knife, 1, -1.4F);
        steelSupplier.createBasicItem(EItemCategory.Ingot);

        var bronzeSupplier = new TierItemsCreator("bronze_ingot", EMaterialType.BRONZE, ModTiers.BRONZE);
        /*steelSupplier.createToolItem(EItemCategory.Axe, 5.5F, -3.2F);
        steelSupplier.createToolItem(EItemCategory.PickAxe, 1, -2.8f);
        steelSupplier.createToolItem(EItemCategory.Sword, 3, -2.4F);
        steelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        steelSupplier.createToolItem(EItemCategory.Hoe, -1, -2.0F);
        steelSupplier.createToolItem(EItemCategory.Knife, 1, -1.4F);*/
        bronzeSupplier.createBasicItem(EItemCategory.Ingot);

        var tinSupplier = new TierItemsCreator(EMaterialType.TIN);
        /*steelSupplier.createToolItem(EItemCategory.Axe, 5.5F, -3.2F);
        steelSupplier.createToolItem(EItemCategory.PickAxe, 1, -2.8f);
        steelSupplier.createToolItem(EItemCategory.Sword, 3, -2.4F);
        steelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        steelSupplier.createToolItem(EItemCategory.Hoe, -1, -2.0F);
        steelSupplier.createToolItem(EItemCategory.Knife, 1, -1.4F);*/
        tinSupplier.createBasicItem(EItemCategory.Ingot);
        tinSupplier.createBasicItem(EItemCategory.RawMaterial);
    }

    public static void register(IEventBus bus) {
        createItems();

        ITEMS.register(bus);
    }

    public static class TierItemsCreator {
        private final String modId;
        private final EMaterialType material_type;
        private final Tier tier;
        private final String basis;

        // region constructors
        public TierItemsCreator(EMaterialType material_type) {
            this.modId = BetterProgression.MOD_ID;
            this.basis = "";
            this.tier = null;
            this.material_type = material_type;
        }

        public TierItemsCreator(String mod_id, EMaterialType material_type) {
            this.modId = mod_id;
            this.basis = "";
            this.tier = null;
            this.material_type = material_type;
        }

        public TierItemsCreator(String modId, String basis, EMaterialType material_type, Tier tier) {
            this.modId = modId;
            this.basis = basis;
            this.tier = tier;
            this.material_type = material_type;
        }

        public TierItemsCreator(String basis, EMaterialType material_type, Tier tier) {
            this.modId = BetterProgression.MOD_ID;
            this.basis = basis;
            this.tier = tier;
            this.material_type = material_type;
        }

        // endregion

        public void createToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
            Supplier<Item> itemSupplier = () -> switch (itemCategory) {
                case Axe -> new TinedAxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
                case Sword -> new TinedSwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case PickAxe -> new TinedPickaxeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case Hoe -> new TinedHoeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case Shovel -> new TinedShovelItem(tier, damageMod, attackSpeedMod, new Item.Properties());
                case Knife -> new KnifeItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case Dagger -> new DaggerItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case Machete -> new MacheteItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
            };

            RegistryObject<Item> registryItem =  ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
            var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeld, modId, basis, material_type, tier);
            REGISTERED_ITEMS.add(itemInfo);
        }

        public void createBigToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
            Supplier<Item> itemSupplier = () -> switch (itemCategory) {
                case LongSword -> new LongSwordItem(tier, (int) damageMod, attackSpeedMod, new Item.Properties());
                case BattleAxe -> new BattleAxeItem(tier, damageMod, attackSpeedMod, new Item.Properties());
                default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
            };

            RegistryObject<Item> registryItem =  ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
            var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeldBig, modId, basis, material_type, tier);
            REGISTERED_ITEMS.add(itemInfo);
        }

        public void createSimpleToolItem(EItemCategory itemCategory, float damageMod, float attackSpeedMod) {
            Supplier<Item> itemSupplier = () -> switch (itemCategory) {
                case Club -> new ClubItem(tier, damageMod, attackSpeedMod, new Item.Properties());
                case Dagger -> new DaggerItem(tier, (int)damageMod, attackSpeedMod, new Item.Properties());
                default -> throw new IllegalArgumentException("Invalid item category: " + itemCategory);
            };

            RegistryObject<Item> registryItem =  ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
            var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.HandHeld, modId, basis, material_type, tier);
            REGISTERED_ITEMS.add(itemInfo);
        }

        public void createBasicItem(EItemCategory itemCategory) {
            Supplier<Item> itemSupplier = () -> switch (itemCategory) {
                default -> new Item(new Item.Properties());
            };

            RegistryObject<Item> registryItem =  ITEMS.register(itemCategory.getFullName(material_type.GetName()), itemSupplier);
            var itemInfo = new ItemInfo(registryItem, itemCategory, EItemType.Simple, modId, basis, material_type, tier);
            REGISTERED_ITEMS.add(itemInfo);
        }
    }
    public static final class ItemInfo<T extends Item> {
        public final RegistryObject<T> item;
        public final EItemCategory category;
        public final EItemType type;
        public final String mod_id;
        public final String basis;
        public final EMaterialType material_type;
        public final Tier tier;

        public ItemInfo(RegistryObject<T> item, EItemCategory category, EItemType type, String modId, String basis, EMaterialType material_type, Tier tier) {
            this.item = item;
            this.category = category;
            this.type = type;
            mod_id = modId;
            this.basis = basis;
            this.tier = tier;
            this.material_type = material_type;
        }
    }

    public enum EItemCategory {
        RawMaterial("raw", true),
        Ingot("ingot"),
        Material(""),
        Axe("axe"),
        PickAxe("pickaxe"),
        Sword("sword"),
        Shovel("shovel"),
        Hoe("hoe"),
        Knife("knife"),
        Dagger("dagger"),
        Club("club"),
        Machete("machete"),
        LongSword("longsword"),
        BattleAxe("battleaxe");

        EItemCategory(String name) {
            this.name = name;
            this.nameFirst = false;
        }

        EItemCategory(String name, boolean nameFirst) {
            this.name = name;
            this.nameFirst = nameFirst;
        }

        private final String name;
        private final boolean nameFirst;

        public String getFullName(String tierName) {
            if(name.isEmpty()) return tierName;
            return nameFirst ? name + "_" + tierName : tierName + "_" + name;
        }
    }

    public enum EMaterialType {
        TIN("tin"),
        BRONZE("bronze"),
        STEEL("steel"),
        COPPER("copper"),
        IRON("iron"),
        DIAMOND("diamond"),
        STONE("stone"),
        WOOD("wooden");
        private String name;
        EMaterialType(String name) {
            this.name = name;
        }

        public String GetName() {
            return name;
        }
    }
    
    public enum EItemType {
        Simple,
        HandHeld,
        HandHeldBig,
    }
}
