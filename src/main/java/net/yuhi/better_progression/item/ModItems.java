package net.yuhi.better_progression.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.*;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.enums.EModArmorMaterial;
import net.yuhi.better_progression.item.utils.ItemInfo;
import net.yuhi.better_progression.item.utils.TierItemsCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, BetterProgression.MOD_ID);

    public static final DeferredRegister<Item> VANILLA_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    
    public static final List<ItemInfo<Item>>  REGISTERED_ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> TIN_ITEM_INTERFACE = ITEMS.register("tin_item_interface", TinnedItemRecipeInterface::new);
    public static final RegistryObject<Item> HILT = register("hilt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURE_DIAMOND = register("pure_diamond", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PINK_QUARTZ = register("pink_quartz", () -> new Item(new Item.Properties()));
    
    //public static final RegistryObject<Item> Test = ITEMS.register("copper_helmet", () -> new ArmorItem(EModArmorMaterial.COPPER, ArmorItem.Type.HELMET, new Item.Properties()));
    
    private static RegistryObject<Item> register(String name, Supplier<Item> item) {
        var toReturn = ITEMS.register(name, item);
        var itemInfo = new ItemInfo<>(toReturn);
        REGISTERED_ITEMS.add(itemInfo);
        return itemInfo.item;
    }

    public static void createItems() {
        var woodenSupplier = new TierItemsCreator(ItemTags.PLANKS, EMaterialType.WOOD, Tiers.WOOD);
        woodenSupplier.createSimpleToolItem(EItemCategory.Club, 3.0F, -3.2F);

        var stoneSupplier = new TierItemsCreator(Tags.Items.COBBLESTONE, EMaterialType.STONE, Tiers.STONE);
        stoneSupplier.createSimpleToolItem(EItemCategory.Club, 3.5F, -3.2F);
        stoneSupplier.createSimpleToolItem(EItemCategory.Dagger, 0.5F, -1.4F);

        var diamondSupplier = new TierItemsCreator("diamond", EMaterialType.DIAMOND, ModTiers.BETTER_DIAMOND, true);
        diamondSupplier.createSimpleToolItem(EItemCategory.Club, 5.0F, -2.8F);
        diamondSupplier.createSimpleToolItem(EItemCategory.Dagger, 2.0F, -0.8F);

        var copperSupplier = new TierItemsCreator("copper_ingot", EMaterialType.COPPER, ModTiers.COPPER, true);
        copperSupplier.createToolItem(EItemCategory.Axe, 6.5F, -3.1F);
        copperSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        copperSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        copperSupplier.createToolItem(EItemCategory.Shovel, 2F, -3.0F);
        copperSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1.0F);
        copperSupplier.createToolItem(EItemCategory.Knife, 1.5F, -1.4F);
        copperSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        copperSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.6F);
        copperSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.9F);
        copperSupplier.createBasicItem(EItemCategory.Plate);
        copperSupplier.createBasicItem(EItemCategory.Chainmail);
        copperSupplier.createBasicItem(EItemCategory.Nugget);
        copperSupplier.createArmorSet();
        copperSupplier.createChainmailArmorSet();

        var bronzeSupplier = new TierItemsCreator("bronze_ingot", EMaterialType.BRONZE, ModTiers.BRONZE);
        bronzeSupplier.createToolItem(EItemCategory.Axe, 6.5F, -3.1F);
        bronzeSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        bronzeSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        bronzeSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -3.0F);
        bronzeSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1.0F);
        bronzeSupplier.createToolItem(EItemCategory.Knife, 1.5f, -1.4F);
        bronzeSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        bronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.6F);
        bronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.9F);
        bronzeSupplier.createBasicItem(EItemCategory.Ingot);
        bronzeSupplier.createBasicItem(EItemCategory.Chainmail);
        bronzeSupplier.createBasicItem(EItemCategory.Plate);
        bronzeSupplier.createBasicItem(EItemCategory.Nugget);
        bronzeSupplier.createArmorSet();
        bronzeSupplier.createChainmailArmorSet();

        var steelSupplier = new TierItemsCreator("steel_ingot", EMaterialType.STEEL, ModTiers.STEEL);
        steelSupplier.createToolItem(EItemCategory.Axe, 6.0f, -3.1F);
        steelSupplier.createToolItem(EItemCategory.PickAxe, 1.0f, -2.8f);
        steelSupplier.createToolItem(EItemCategory.Sword, 3.0f, -2.4F);
        steelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -3.0F);
        steelSupplier.createToolItem(EItemCategory.Hoe, -2.0f, -1F);
        steelSupplier.createToolItem(EItemCategory.Knife, 1.0f, -1.4F);
        steelSupplier.createToolItem(EItemCategory.Machete, 1.0F, -1.4F);
        steelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.6F);
        steelSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.9F);
        steelSupplier.createBasicItem(EItemCategory.Ingot);
        steelSupplier.createBasicItem(EItemCategory.Chainmail);
        steelSupplier.createBasicItem(EItemCategory.Plate);
        steelSupplier.createBasicItem(EItemCategory.Nugget);
        steelSupplier.createArmorSet();
        steelSupplier.createChainmailArmorSet();
        
        var tinSupplier = new TierItemsCreator(EMaterialType.TIN);
        tinSupplier.createBasicItem(EItemCategory.Ingot);
        tinSupplier.createBasicItem(EItemCategory.RawMaterial);
        
        var obsidianSupplier = new TierItemsCreator("obsidian", EMaterialType.OBSIDIAN, ModTiers.OBSIDIAN, true);
        obsidianSupplier.createToolItem(EItemCategory.Dagger, 2.0F, -0.8F);
        obsidianSupplier.createSimpleToolItem(EItemCategory.Club, 4.0F, -3F);

        /*var netherSteelSupplier = new TierItemsCreator("netherite_ingot", EMaterialType.NETHERITE, ModTiers.ENDGAMESTEEL, true);
        netherSteelSupplier.createToolItem(EItemCategory.Axe, 6.5f, -3.1F);
        netherSteelSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.8f);
        netherSteelSupplier.createToolItem(EItemCategory.Sword, 3.5f, -2.4F);
        netherSteelSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -3.0F);
        netherSteelSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -1F);
        netherSteelSupplier.createToolItem(EItemCategory.Knife, 1.5f, -1.4F);
        netherSteelSupplier.createToolItem(EItemCategory.Machete, 1.5F, -1.4F);
        netherSteelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.5f, -3.6F);
        netherSteelSupplier.createBigToolItem(EItemCategory.LongSword, 4.5f, -2.9F);
        netherSteelSupplier.createBasicItem(EItemCategory.Chainmail);
        netherSteelSupplier.createBasicItem(EItemCategory.Plate);
        netherSteelSupplier.createArmorSet();
        netherSteelSupplier.createChainmailArmorSet();

        var netherBronzeSupplier = new TierItemsCreator("netherite_ingot", EMaterialType.NETHERITE, ModTiers.ENDGAMEBRONZE, true);
        netherBronzeSupplier.createToolItem(EItemCategory.Axe, 7.0F, -3.1F);
        netherBronzeSupplier.createToolItem(EItemCategory.PickAxe, 2.0f, -2.8f);
        netherBronzeSupplier.createToolItem(EItemCategory.Sword, 4.0f, -2.4F);
        netherBronzeSupplier.createToolItem(EItemCategory.Shovel, 2.5F, -3.0F);
        netherBronzeSupplier.createToolItem(EItemCategory.Hoe, -1.0f, -1.0F);
        netherBronzeSupplier.createToolItem(EItemCategory.Knife, 2.0f, -1.4F);
        netherBronzeSupplier.createToolItem(EItemCategory.Machete, 2.0F, -1.4F);
        netherBronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.5f, -3.6F);
        netherBronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.5f, -2.9F);
        netherBronzeSupplier.createBasicItem(EItemCategory.Chainmail);
        netherBronzeSupplier.createBasicItem(EItemCategory.Plate);
        netherBronzeSupplier.createArmorSet();
        netherBronzeSupplier.createChainmailArmorSet();

        var endSteelSupplier = new TierItemsCreator("enderite_ingot", EMaterialType.ENDERITE, ModTiers.ENDGAMESTEEL);
        endSteelSupplier.createToolItem(EItemCategory.Axe, 6.0f, -2.6F);
        endSteelSupplier.createToolItem(EItemCategory.PickAxe, 1.0f, -2.3f);
        endSteelSupplier.createToolItem(EItemCategory.Sword, 3.0f, -1.9F);
        endSteelSupplier.createToolItem(EItemCategory.Shovel, 1.5F, -2.5F);
        endSteelSupplier.createToolItem(EItemCategory.Hoe, -2.0f, -0.5F);
        endSteelSupplier.createToolItem(EItemCategory.Knife, 1.0f, -0.9F);
        endSteelSupplier.createToolItem(EItemCategory.Machete, 1.F, -0.9F);
        endSteelSupplier.createBigToolItem(EItemCategory.BattleAxe, 7.0f, -3.1F);
        endSteelSupplier.createBigToolItem(EItemCategory.LongSword, 4.0f, -2.4F);
        endSteelSupplier.createBasicItem(EItemCategory.Chainmail);
        endSteelSupplier.createBasicItem(EItemCategory.Plate);
        endSteelSupplier.createArmorSet();
        endSteelSupplier.createChainmailArmorSet();

        var endBronzeSupplier = new TierItemsCreator("enderite_ingot", EMaterialType.ENDERITE, ModTiers.ENDGAMEBRONZE);
        endBronzeSupplier.createToolItem(EItemCategory.Axe, 6.5F, -2.6F);
        endBronzeSupplier.createToolItem(EItemCategory.PickAxe, 1.5f, -2.3f);
        endBronzeSupplier.createToolItem(EItemCategory.Sword, 3.5f, -1.9F);
        endBronzeSupplier.createToolItem(EItemCategory.Shovel, 2.0F, -2.5F);
        endBronzeSupplier.createToolItem(EItemCategory.Hoe, -1.5f, -0.5F);
        endBronzeSupplier.createToolItem(EItemCategory.Knife, 1.5f, -0.9F);
        endBronzeSupplier.createToolItem(EItemCategory.Machete, 1.5F, -0.9F);
        endBronzeSupplier.createBigToolItem(EItemCategory.BattleAxe, 8.0f, -3.1F);
        endBronzeSupplier.createBigToolItem(EItemCategory.LongSword, 5.0f, -2.4F);
        endBronzeSupplier.createBasicItem(EItemCategory.Chainmail);
        endBronzeSupplier.createBasicItem(EItemCategory.Plate);
        endBronzeSupplier.createArmorSet();
        endBronzeSupplier.createChainmailArmorSet();*/
    }

    public static void register(IEventBus bus) {
        createItems();
        
        ITEMS.register(bus);
        VANILLA_ITEMS.register(bus);
    }
}
