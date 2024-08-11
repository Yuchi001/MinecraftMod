package net.yuhi.better_progression.item.utils;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.item.custom.BattleAxeItem;
import net.yuhi.better_progression.item.custom.LongSwordItem;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.enums.EModArmorMaterial;
import net.yuhi.better_progression.item.interfaces.LayerableItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<ItemInfo> getItemInfos(EItemCategory itemCategory) {
        return REGISTERED_ITEMS.stream().filter(i -> i.category == itemCategory).collect(Collectors.toList());
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
