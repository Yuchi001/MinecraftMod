package net.yuhi.better_progression.item.enums;

public enum EItemCategory {
    Plate("plate"),
    HorseArmor("horse_armor"),
    Nugget("nugget"),
    Chainmail("chainmail"),
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
    Spear("spear"),
    Machete("machete"),
    LongSword("longsword"),
    BattleAxe("battleaxe"),
    Boots("boots"),
    Chestplate("chestplate"),
    Leggings("leggings"),
    Helmet("helmet"),
    ChainmailBoots("chainmail_boots"),
    ChainmailChestplate("chainmail_chestplate"),
    ChainmailLeggings("chainmail_leggings"),
    ChainmailHelmet("chainmail_helmet");

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

    public String getFullName(EMaterialType materialType) {
        var materialTypeName = materialType.GetName();
        if(name.isEmpty()) return materialTypeName;
        return nameFirst ? name.toLowerCase() + "_" + materialTypeName : materialTypeName + "_" + name.toLowerCase();
    }

    public String getFullName(EMaterialType materialType, EMaterialType sub_material) {
        if(name.isEmpty()) return materialType.GetName();
        return materialType.GetName(true) + "_" + sub_material.GetName() + "_" + name.toLowerCase();
    }

    public String getName() {
        return name.toLowerCase();
    }
}
