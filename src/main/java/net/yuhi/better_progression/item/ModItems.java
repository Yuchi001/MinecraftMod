package net.yuhi.better_progression.item;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.ClubItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, BetterProgression.MOD_ID);
    
    public static final List<ItemTuple<Item>> ITEM_TUPLES = new ArrayList<>(); 
    
    // WOOD
    
    public static final RegistryObject<Item> WOODEN_CLUB = registerItem(
            ITEMS.register("wooden_club",
                    () -> new ClubItem(Tiers.WOOD, 3.0F, -3.2F, new Item.Properties())),
            EItemType.HandHeld
    );
    
    // TIN
    
    public static final RegistryObject<Item> RAW_TIN = registerItem(
            ITEMS.register("raw_tin",
                    () -> new Item(new Item.Properties())),
            EItemType.Simple
    );
    
    public static final RegistryObject<Item> TIN_INGOT = registerItem(
            ITEMS.register("tin_ingot",
                    () -> new Item(new Item.Properties())),
            EItemType.Simple
    );

    // STEEL
    
    public static final RegistryObject<Item> STEEL_INGOT = registerItem(
            ITEMS.register("steel_ingot",
                    () -> new Item(new Item.Properties())),
            EItemType.Simple
    );
    
    // BRONZE

    public static final RegistryObject<Item> BRONZE_INGOT = registerItem(
            ITEMS.register("bronze_ingot",
                    () -> new Item(new Item.Properties())),
            EItemType.Simple
    );

    // COPPER
    
    public static final RegistryObject<Item> COPPER_PICKAXE = registerItem(
            ITEMS.register("copper_pickaxe",
                    () -> new PickaxeItem(ModTiers.COPPER, 1, -2.8f, new Item.Properties())),
            EItemType.HandHeld
    );

    public static final RegistryObject<Item> COPPER_SWORD = registerItem(
            ITEMS.register("copper_sword",
                    () -> new SwordItem(ModTiers.COPPER, 3, -2.4F, new Item.Properties())),
            EItemType.HandHeld
    );

    public static final RegistryObject<Item> COPPER_AXE = registerItem(
            ITEMS.register("copper_axe",
                    () -> new AxeItem(ModTiers.COPPER, 7.0F, -3.2F, new Item.Properties())),
            EItemType.HandHeld
    );

    public static final RegistryObject<Item> COPPER_SHOVEL = registerItem(
            ITEMS.register("copper_shovel",
                    () -> new ShovelItem(ModTiers.COPPER, 1.5F, -3.0F, new Item.Properties())),
            EItemType.HandHeld
    );

    public static final RegistryObject<Item> COPPER_HOE = registerItem(
            ITEMS.register("copper_hoe",
                    () -> new HoeItem(ModTiers.COPPER, -1, -2.0F, new Item.Properties())),
            EItemType.HandHeld
    );
    
    public static RegistryObject<Item> registerItem(RegistryObject<Item> item, EItemType itemType) {
        ITEM_TUPLES.add(new ItemTuple(item, itemType));
        return item;
    }
    
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
    
    public static final class ItemTuple<T extends Item> {
        public final RegistryObject<T> item;
        public final EItemType itemType;
        
        public ItemTuple(RegistryObject<T> item, EItemType itemType) {
            this.item = item;
            this.itemType = itemType;
        }
    }
    
    public enum EItemType {
        Simple,
        HandHeld,
    }
}
