package net.yuhi.better_progression.item.enums;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public enum EMaterialType {
    NETHERITE("netherite", true),
    ENDERITE("enderite", false),
    TIN("tin", false),
    GOLD("golden", true),
    BRONZE("bronze", false),
    STEEL("steel", false),
    COPPER("copper", true),
    IRON("iron", true),
    DIAMOND("diamond", true),
    OBSIDIAN("obsidian", true),
    STONE("stone", true, true),
    WOOD("wooden", true, true);
    private final String name;
    private final boolean is_vanilla;
    private final boolean is_tag;
    
    EMaterialType(String name, boolean is_vanilla) {
        this.name = name;
        this.is_vanilla = is_vanilla;
        this.is_tag = false;
    }

    EMaterialType(String name, boolean is_vanilla, boolean is_tag) {
        this.name = name;
        this.is_vanilla = is_vanilla;
        this.is_tag = is_tag;
    }

    public String GetName() {
        return name;
    }
    
    public boolean IsVanilla() {
        return is_vanilla;
    }

    public boolean IsTag() {
        return is_tag;
    }
    
    public String GetName(boolean get_prefix) {
        if(!get_prefix) return name;

        return switch (this) {
            case NETHERITE -> "nether";
            case ENDERITE -> "ender";
            default -> name;
        };

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