package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public enum EMaterialType {
    NETHERITE("netherite"),
    ENDERITE("enderite"),
    TIN("tin"),
    GOLD("golden"),
    BRONZE("bronze"),
    STEEL("steel"),
    COPPER("copper"),
    IRON("iron"),
    DIAMOND("diamond"),
    OBSIDIAN("obsidian"),
    STONE("stone"),
    WOOD("wooden");
    private String name;
    EMaterialType(String name) {
        this.name = name;
    }

    public String GetName() {
        return name;
    }

    public static EMaterialType GetMaterialType(ItemStack stack) {
        return GetMaterialType(stack.getItem());
    }

    public static EMaterialType GetMaterialType(Item item) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if(key == null) return null;

        var stringKey = key.toString();
        var itemKey = stringKey.substring(stringKey.indexOf(":") + 1);
        var materialName = itemKey.substring(0, itemKey.indexOf("_"));
        for (var material : EMaterialType.values()) {
            if (material.GetName().equals(materialName)) return material;
        }
        return null;
    }
}