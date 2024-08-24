package net.yuhi.better_progression.item.utils;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.item.custom.BattleAxeItem;
import net.yuhi.better_progression.item.custom.LongSwordItem;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.ELootItemDropProps;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.enums.EModArmorMaterial;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static net.yuhi.better_progression.item.ModItems.REGISTERED_ITEMS;

public final class ItemsUtilsMethods {
    public static List<EItemCategory> getVanillaTools() {
        return List.of(
                EItemCategory.Axe,
                EItemCategory.PickAxe,
                EItemCategory.Shovel,
                EItemCategory.Hoe,
                EItemCategory.Sword
        );
    }

    public static List<Item> getItems(EMaterialType materialType) {
        return REGISTERED_ITEMS.stream().filter(i -> i.material_type == materialType).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static List<Item> getLayerableTools() {
        List<Type> layerableClasses = List.of(
                SwordItem.class,
                AxeItem.class,
                PickaxeItem.class,
                ShovelItem.class,
                HoeItem.class,
                LongSwordItem.class,
                BattleAxeItem.class);

        var vanillaLayeredItems = new ArrayList<Item>();
        for(var item : ForgeRegistries.ITEMS) {
            if(layerableClasses.contains(item.getClass())) vanillaLayeredItems.add(item);
        }
        return vanillaLayeredItems;
    }

    public static List<Item> getSmeltableItems() {
        var smeltableMaterials = new ArrayList<EMaterialType>(List.of(
                EMaterialType.TIN
        ));
        return REGISTERED_ITEMS.stream().filter(itemItemInfo -> smeltableMaterials.contains(itemItemInfo.material_type)).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static Item getItem(EItemCategory itemCategory, EMaterialType materialType) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory && i.material_type == materialType).findFirst().get().item.get();
    }

    public static List<Item> getItems(EItemCategory itemCategory) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory).map(i -> i.item.get()).collect(Collectors.toList());
    }

    public static List<ItemInfo> getItemInfosForCraftingRecipes(EItemCategory itemCategory) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory && !i.is_upgrade).collect(Collectors.toList());
    }

    public static List<ItemInfo> getItemInfosForCraftingRecipes(EItemCategory itemCategory, boolean isUpgrade) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory && i.is_upgrade == isUpgrade).collect(Collectors.toList());
    }

    public static EModArmorMaterial materialToArmorMaterial(EMaterialType materialType, boolean chainmail) {
        switch (materialType) {
            case BRONZE -> {
                return chainmail ? EModArmorMaterial.BRONZE_CHAINMAIL : EModArmorMaterial.BRONZE;
            }
            case STEEL -> {
                return chainmail ? EModArmorMaterial.STEEL_CHAINMAIL : EModArmorMaterial.STEEL;
            }
            case COPPER -> {
                return chainmail ? EModArmorMaterial.COPPER_CHAINMAIL : EModArmorMaterial.COPPER;
            }
        }
        return null;
    }

    public static EModArmorMaterial materialToArmorMaterial(EMaterialType materialType, EMaterialType subMaterialType, boolean chainmail) {
        switch (materialType) {
            case NETHERITE -> {
                return switch (subMaterialType) {
                    case BRONZE -> chainmail ? EModArmorMaterial.NETHER_BRONZE_CHAINMAIL : EModArmorMaterial.NETHER_BRONZE;
                    case STEEL -> chainmail ? EModArmorMaterial.NETHER_STEEL_CHAINMAIL : EModArmorMaterial.NETHER_STEEL;
                    default -> null;
                };
            }
            case ENDERITE -> {
                return switch (subMaterialType) {
                    case BRONZE -> chainmail ? EModArmorMaterial.ENDER_BRONZE_CHAINMAIL : EModArmorMaterial.ENDER_BRONZE;
                    case STEEL -> chainmail ? EModArmorMaterial.ENDER_STEEL_CHAINMAIL : EModArmorMaterial.ENDER_STEEL;
                    default -> null;
                };
            }
        }
        return null;
    }

    public static EItemCategory armorTypeToItemCategory(ArmorItem.Type armorType) {
        switch (armorType) {
            case HELMET -> {
                return EItemCategory.Helmet;
            }
            case BOOTS -> {
                return EItemCategory.Boots;
            }
            case CHESTPLATE -> {
                return EItemCategory.Chestplate;
            }
            case LEGGINGS -> {
                return EItemCategory.Leggings;
            }
        }
        return null;
    }

    public static int getCount(int defaultCount, SwordItem sword){
        class DropTuple {
            public final int count;
            public final float weight;
            DropTuple(int count, float weight) {
                this.count = count;
                this.weight = weight;
            }
        }

        var dropList = new ArrayList<DropTuple>();
        var maxCount = ELootItemDropProps.getTierDrop(sword.getTier());
        var weightSum = 0.0f;
        var baseWeight = 100.0f;
        for (var i = 0; i <= maxCount; i++) {
            dropList.add(new DropTuple(i, baseWeight));
            weightSum += baseWeight;
            baseWeight /= 1.5f;
        }

        dropList.sort((o1, o2) -> Float.compare(o1.weight, o2.weight));

        var randomNum = ThreadLocalRandom.current().nextFloat(0.0f, weightSum + 1);
        for (var dropItem : dropList) {
            if (randomNum <= dropItem.weight) return dropItem.count + defaultCount;
            randomNum -= dropItem.weight;
        }

        return defaultCount;
    }
}
