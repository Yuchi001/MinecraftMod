package net.yuhi.better_progression.item.utils;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.enums.EModArmorMaterial;

import java.util.ArrayList;
import java.util.List;
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

    public static List<ItemInfo> getItemInfos(EItemCategory itemCategory) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory).collect(Collectors.toList());
    }

    public static EModArmorMaterial materialToArmorMaterial(EMaterialType materialType) {
        switch (materialType) {
            case BRONZE -> {
                return EModArmorMaterial.BRONZE;
            }
            case STEEL -> {
                return EModArmorMaterial.STEEL;
            }
            case COPPER -> {
                return EModArmorMaterial.COPPER;
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
}
