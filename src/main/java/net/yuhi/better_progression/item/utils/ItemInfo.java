package net.yuhi.better_progression.item.utils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;

public final class ItemInfo<T extends Item> {
    public final RegistryObject<T> item;
    public final EItemCategory category;
    public final EItemType type;
    public final String mod_id;
    public final String basis;
    public final EMaterialType material_type;
    public final EMaterialType sub_material_type;
    public final Tier tier;
    public final TagKey<Item> tag;
    public final boolean has_default_basis;
    public final boolean is_upgrade;

    public ItemInfo(RegistryObject<T> item, EItemCategory category, EItemType type, String modId, String basis, TagKey<Item> tag, EMaterialType material_type, Tier tier, boolean has_default_basis) {
        this.item = item;
        this.category = category;
        this.type = type;
        this.mod_id = modId;
        this.basis = basis;
        this.tier = tier;
        this.tag = tag;
        this.material_type = material_type;
        this.has_default_basis = has_default_basis;
        this.is_upgrade = false;
        this.sub_material_type = null;
    }

    public ItemInfo(RegistryObject<T> item, EItemCategory category, EItemType type, String modId, String basis, TagKey<Item> tag, EMaterialType material_type, EMaterialType sub_material_type, Tier tier, boolean has_default_basis) {
        this.item = item;
        this.category = category;
        this.type = type;
        this.mod_id = modId;
        this.basis = basis;
        this.tier = tier;
        this.tag = tag;
        this.material_type = material_type;
        this.sub_material_type = sub_material_type;
        this.has_default_basis = has_default_basis;
        this.is_upgrade = true;
    }


    public ItemInfo(RegistryObject<T> item) {
        this.item = item;
        this.category = EItemCategory.Ingot;
        this.type = EItemType.Simple;
        this.mod_id = BetterProgression.MOD_ID;
        this.basis = "";
        this.tier = null;
        this.tag = null;
        this.material_type = null;
        this.has_default_basis = false;
        this.is_upgrade = false;
        this.sub_material_type = null;
    }
}