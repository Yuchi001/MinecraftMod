package net.yuhi.better_progression.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.entity.custom.ModBoatEntity;
import net.yuhi.better_progression.item.custom.*;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.food.ModFoods;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;
import net.yuhi.better_progression.item.utils.TierItemsCreator;
import net.yuhi.better_progression.tag.ModTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterProgression.MOD_ID);

    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    
    public static final List<TierItemsCreator.ItemInfo> REGISTERED_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> ZOMBIE_ESSENCE = register("zombie_essence", () -> new MobEssenceItem("zombie", 1, () -> Items.ROTTEN_FLESH, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> MAGMA_CUBE_ESSENCE = register("magma_cube_essence", () -> new MobEssenceItem("magma_cube", 4, () -> Items.MAGMA_CREAM, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> SKELETON_ESSENCE = register("skeleton_essence", () -> new MobEssenceItem("skeleton", 2, () -> Items.BONE, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> SLIME_ESSENCE = register("slime_essence", () -> new MobEssenceItem("slime", 1, () -> Items.SLIME_BALL, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> ENDERMAN_ESSENCE = register("enderman_essence", () -> new MobEssenceItem("enderman", 5, () -> Items.ENDER_EYE, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> BLAZE_ESSENCE = register("blaze_essence", () -> new MobEssenceItem("blaze", 4, () -> Items.BLAZE_ROD, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> CREEPER_ESSENCE = register("creeper_essence", () -> new MobEssenceItem("creeper", 2, () -> Items.GUNPOWDER, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> SPIDER_ESSENCE = register("spider_essence", () -> new MobEssenceItem("spider", 3, () -> Items.SPIDER_EYE, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> PHANTOM_ESSENCE = register("phantom_essence", () -> new MobEssenceItem("phantom", 4, () -> Items.PHANTOM_MEMBRANE, new Item.Properties().stacksTo(16)), ModTags.Items.ESSENCE_ITEM);
    public static final RegistryObject<Item> END_OAK_BOAT = register("end_oak_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.END_OAK, new Item.Properties()));
    public static final RegistryObject<Item> END_OAK_CHEST_BOAT = register("end_oak_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.END_OAK, new Item.Properties()));
    public static final RegistryObject<Item> TIN_ITEM_INTERFACE = ITEMS.register("tin_item_interface", TinnedItemRecipeInterface::new);
    public static final RegistryObject<Item> HILT = register("hilt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_COIN = register("emerald_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16)));
    public static final RegistryObject<Item> BROKEN_TOTEM_OF_UNDYING = register("broken_totem_of_undying", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DRAGON_REMAINS = register("dragon_remains", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PINK_QUARTZ = register("pink_quartz", () -> new Item(new Item.Properties().stacksTo(256)));
    public static final RegistryObject<Item> POLISHED_PINK_QUARTZ = register("polished_pink_quartz", () -> new PolishedQuartz(new Item.Properties().stacksTo(16), PolishedQuartz.Type.PINK));
    public static final RegistryObject<Item> POLISHED_QUARTZ = register("polished_quartz", () -> new PolishedQuartz(new Item.Properties().stacksTo(16), PolishedQuartz.Type.NETHER));
    public static final RegistryObject<Item> END_OAK_SIGN = register("end_oak_sign", () -> new SignItem(new Item.Properties().stacksTo(16),
            ModBlocks.END_OAK_SIGN.get(), ModBlocks.END_OAK_WALL_SIGN.get()));
    public static final RegistryObject<Item> BEAR_MEAT = register("bear_meat", () -> new Item(new Item.Properties().food(ModFoods.BEAR_MEAT).stacksTo(16)));
    public static final RegistryObject<Item> COOKED_BEAR_MEAT = register("cooked_bear_meat", () -> new Item(new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT).stacksTo(16)));
    
    private static RegistryObject<Item> register(String name, Supplier<Item> item) {
        var toReturn = ITEMS.register(name, item);
        return TierItemsCreator.RegisterSimpleItem(toReturn);
    }

    private static RegistryObject<Item> register(String name, Supplier<Item> item, TagKey<Item>... tags) {
        var toReturn = ITEMS.register(name, item);
        return TierItemsCreator.RegisterSimpleItem(toReturn, Arrays.stream(tags).toList());
    }

    public static void createItems() {
        var woodenSupplier = new TierItemsCreator(ItemTags.PLANKS, EMaterialType.WOOD, Tiers.WOOD);
        woodenSupplier.createSimpleToolItem(EItemCategory.Club, 3.0F, -3.2F);

        var stoneSupplier = new TierItemsCreator(Tags.Items.COBBLESTONE, EMaterialType.STONE, Tiers.STONE);
        stoneSupplier.createSimpleToolItem(EItemCategory.Club, 3.5F, -3.2F);
        stoneSupplier.createSimpleToolItem(EItemCategory.Dagger, 0.5F, -1.4F);
        stoneSupplier.createSpearItem(4F, -2.9F, 1.5D);

        var diamondSupplier = new TierItemsCreator("diamond", EMaterialType.DIAMOND, ModTiers.BETTER_DIAMOND).SetHasDefaultBasis();
        diamondSupplier.createSimpleToolItem(EItemCategory.Club, 5.0F, -2.8F);
        diamondSupplier.createSimpleToolItem(EItemCategory.Dagger, 2.0F, -0.8F);
        diamondSupplier.createSpearItem(6F, -2.9F, 1.5D);

        var obsidianSupplier = new TierItemsCreator("obsidian", EMaterialType.OBSIDIAN, ModTiers.OBSIDIAN).SetHasDefaultBasis();
        obsidianSupplier.createSimpleToolItem(EItemCategory.Dagger, 2.0F, -0.8F);
        obsidianSupplier.createSimpleToolItem(EItemCategory.Club, 4.0F, -3F);
        obsidianSupplier.createSpearItem(7F, -2.9F, 1.5D);

        var copperSupplier = new TierItemsCreator("copper_ingot", EMaterialType.COPPER, ModTiers.COPPER)
                .SetHasDefaultBasis()
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.COPPER_TOOLS_ARMOR)
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG);
        copperSupplier.createToolItem(EItemCategory.Axe, 6.5F, -3.1F);
        copperSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        copperSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        copperSupplier.createToolItem(EItemCategory.Shovel, 2F, -3.0F);
        copperSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1.0F);
        copperSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        copperSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.6F, 1.5D);
        copperSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.9F, 1.5D);
        copperSupplier.createBasicItem(EItemCategory.Plate);
        copperSupplier.createBasicItem(EItemCategory.Chainmail);
        copperSupplier.createBasicItem(EItemCategory.Nugget);
        copperSupplier.createArmorSet();
        copperSupplier.createHorseArmor(5);
        copperSupplier.createChainmailArmorSet();

        var bronzeSupplier = new TierItemsCreator("bronze_ingot", EMaterialType.BRONZE, ModTiers.BRONZE)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.BRONZE_TOOLS_ARMOR)
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG);
        bronzeSupplier.createToolItem(EItemCategory.Axe, 6.5F, -3.1F);
        bronzeSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        bronzeSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        bronzeSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -3.0F);
        bronzeSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1.0F);
        bronzeSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        bronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.6F, 1.5D);
        bronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.9F, 1.5D);
        bronzeSupplier.createBasicItem(EItemCategory.Ingot);
        bronzeSupplier.createBasicItem(EItemCategory.Chainmail);
        bronzeSupplier.createBasicItem(EItemCategory.Plate);
        bronzeSupplier.createBasicItem(EItemCategory.Nugget);
        bronzeSupplier.createArmorSet();
        bronzeSupplier.createHorseArmor(7);
        bronzeSupplier.createChainmailArmorSet();

        var steelSupplier = new TierItemsCreator("steel_ingot", EMaterialType.STEEL, ModTiers.STEEL)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.STEEL_TOOLS_ARMOR)
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG);
        steelSupplier.createToolItem(EItemCategory.Axe, 6.0f, -3.1F);
        steelSupplier.createToolItem(EItemCategory.PickAxe, 1.0f, -2.8f);
        steelSupplier.createToolItem(EItemCategory.Sword, 3.0f, -2.4F);
        steelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        steelSupplier.createToolItem(EItemCategory.Hoe, -2.0f, -1F);
        steelSupplier.createToolItem(EItemCategory.Machete, 1.0F, -1.4F);
        steelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.6F, 1.5D);
        steelSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.9F, 1.5D);
        steelSupplier.createBasicItem(EItemCategory.Ingot);
        steelSupplier.createBasicItem(EItemCategory.Chainmail);
        steelSupplier.createBasicItem(EItemCategory.Plate);
        steelSupplier.createBasicItem(EItemCategory.Nugget);
        steelSupplier.createHorseArmor(7);
        steelSupplier.createArmorSet();
        steelSupplier.createChainmailArmorSet();
        
        var ironSupplier = new TierItemsCreator("iron_ingot", EMaterialType.IRON, Tiers.IRON)
                .SetHasDefaultBasis()
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.CUSTOM_IRON_TOOLS);
        ironSupplier.createToolItem(EItemCategory.Machete, 1.0F, -1.4F);
        ironSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.6F, 1.5D);
        ironSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.9F, 1.5D);
        ironSupplier.createBasicItem(EItemCategory.Plate);
        ironSupplier.createBasicItem(EItemCategory.Chainmail);

        var goldSupplier = new TierItemsCreator("gold_ingot", EMaterialType.GOLD, Tiers.GOLD)
                .SetHasDefaultBasis()
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.CUSTOM_GOLD_TOOLS);
        goldSupplier.createToolItem(EItemCategory.Machete, 1.0F, -1.4F);
        goldSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.6F, 1.5D);
        goldSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.9F, 1.5D);
        goldSupplier.createBasicItem(EItemCategory.Plate);
        goldSupplier.createBasicItem(EItemCategory.Chainmail);
        goldSupplier.createChainmailArmorSet();
        
        var tinSupplier = new TierItemsCreator(EMaterialType.TIN);
        tinSupplier.createBasicItem(EItemCategory.Ingot);
        tinSupplier.createBasicItem(EItemCategory.RawMaterial);
        
        var enderiteSupplier = new TierItemsCreator("enderite_ingot", EMaterialType.ENDERITE, ModTiers.ENDGAMESTEEL);
        enderiteSupplier.createBasicItem(EItemCategory.Ingot);

        var netherSteelSupplier = new TierItemsCreator("netherite_ingot", EMaterialType.NETHERITE, ModTiers.ENDGAMESTEEL)
                .SetSubMaterialType(EMaterialType.STEEL)
                .SetHasDefaultBasis()
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getAllArmorCategories(), ModTags.Items.NETHERITE_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.NETHERITE_TOOLS_ARMOR);
        netherSteelSupplier.createToolItem(EItemCategory.Axe, 6.5f, -3.1F);
        netherSteelSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        netherSteelSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        netherSteelSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -3.0F);
        netherSteelSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1F);
        netherSteelSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        netherSteelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.5f, -3.6F, 1.5D);
        netherSteelSupplier.createBigToolItem(EItemCategory.LongSword, 4.5f, -2.9F, 1.5D);
        netherSteelSupplier.createArmorSet();
        netherSteelSupplier.createChainmailArmorSet();

        var netherBronzeSupplier = new TierItemsCreator("netherite_ingot", EMaterialType.NETHERITE, ModTiers.ENDGAMEBRONZE)
                .SetSubMaterialType(EMaterialType.BRONZE)
                .SetHasDefaultBasis()
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getAllArmorCategories(), ModTags.Items.NETHERITE_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.NETHERITE_TOOLS_ARMOR);
        netherBronzeSupplier.createToolItem(EItemCategory.Axe, 7.0F, -3.1F);
        netherBronzeSupplier.createToolItem(EItemCategory.PickAxe, 2.0f, -2.8f);
        netherBronzeSupplier.createToolItem(EItemCategory.Sword, 4.0f, -2.4F);
        netherBronzeSupplier.createToolItem(EItemCategory.Shovel, 2.5F, -3.0F);
        netherBronzeSupplier.createToolItem(EItemCategory.Hoe, -1.0f, -1.0F);
        netherBronzeSupplier.createToolItem(EItemCategory.Machete, 2.0F, -1.4F);
        netherBronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.5f, -3.6F, 1.5D);
        netherBronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.5f, -2.9F, 1.5D);
        netherBronzeSupplier.createArmorSet();
        netherBronzeSupplier.createChainmailArmorSet();

        var endSteelSupplier = new TierItemsCreator("enderite_ingot", EMaterialType.ENDERITE, ModTiers.ENDGAMESTEEL)
                .SetSubMaterialType(EMaterialType.STEEL)
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getAllArmorCategories(), ModTags.Items.ENDERITE_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.ENDERITE_TOOLS_ARMOR);
        endSteelSupplier.createToolItem(EItemCategory.Axe, 6.0f, -2.6F);
        endSteelSupplier.createToolItem(EItemCategory.PickAxe, 1.0f, -2.3f);
        endSteelSupplier.createToolItem(EItemCategory.Sword, 3.0f, -1.9F);
        endSteelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -2.5F);
        endSteelSupplier.createToolItem(EItemCategory.Hoe, -2.0f, -0.5F);
        endSteelSupplier.createToolItem(EItemCategory.Machete, 1.F, -0.9F);
        endSteelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.1F, 1.5D);
        endSteelSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.4F, 1.5D);
        endSteelSupplier.createArmorSet();
        endSteelSupplier.createChainmailArmorSet();

        var enderBronzeSupplier = new TierItemsCreator("enderite_ingot", EMaterialType.ENDERITE, ModTiers.ENDGAMEBRONZE)
                .SetSubMaterialType(EMaterialType.BRONZE)                
                .AddTag(ItemsUtilsMethods.getHeavyArmorCategories(), ModTags.Items.HEAVY_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getAllArmorCategories(), ModTags.Items.ENDERITE_ARMOR_TAG)
                .AddTag(ItemsUtilsMethods.getToolArmorCategories(true), ModTags.Items.ENDERITE_TOOLS_ARMOR);
        enderBronzeSupplier.createToolItem(EItemCategory.Axe, 6.5F, -2.6F);
        enderBronzeSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.3f);
        enderBronzeSupplier.createToolItem(EItemCategory.Sword, 3.5f, -1.9F);
        enderBronzeSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -2.5F);
        enderBronzeSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -0.5F);
        enderBronzeSupplier.createToolItem(EItemCategory.Machete, 1.5F, -0.9F);
        enderBronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.1F, 1.5D);
        enderBronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.4F, 1.5D);
        enderBronzeSupplier.createArmorSet();
        enderBronzeSupplier.createChainmailArmorSet();
    }

    public static void register(IEventBus bus) {
        createItems();
        
        ITEMS.register(bus);
        VANILLA_ITEMS.register(bus);
    }
}
