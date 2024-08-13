package net.yuhi.better_progression.item.enums;

public enum EItemCategory {
    Plate("plate"),
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

    public String getFullName(String tierName) {
        if(name.isEmpty()) return tierName;
        return nameFirst ? name.toLowerCase() + "_" + tierName : tierName + "_" + name.toLowerCase();
    }

    public String getName() {
        return name.toLowerCase();
    }
}
