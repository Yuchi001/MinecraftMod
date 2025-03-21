package net.yuhi.better_progression.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.mixin.accessor.LootItemAccessor;
import net.yuhi.better_progression.mixin.accessor.LootPoolAccessor;
import net.yuhi.better_progression.mixin.accessor.LootPoolSingletonContainerAccessor;
import net.yuhi.better_progression.mixin.accessor.LootTableAccessor;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootTableHandler {
    
    private static final Map<String, ResourceLocation[]> SUBSTITUTE_ITEMS = Map.ofEntries(
            Map.entry("diamond_sword", asArr("diamond_spear")),
            Map.entry("diamond_axe", asArr("diamond_knife")),
            Map.entry("diamond_pickaxe", asArr("diamond_club")),
            Map.entry("diamond_hoe", asArr("diamond_club")),
            Map.entry("diamond_shovel", asArr("diamond_club")),
            Map.entry("stone_sword", asArr("stone_spear")),
            Map.entry("stone_axe", asArr("stone_knife")),
            Map.entry("stone_pickaxe", asArr("stone_club")),
            Map.entry("stone_hoe", asArr("stone_club")),
            Map.entry("stone_shovel", asArr("wooden_club")),
            Map.entry("wooden_sword", asArr("wooden_club")),
            Map.entry("wooden_axe", asArr("wooden_club")),
            Map.entry("wooden_pickaxe", asArr("wooden_club")),
            Map.entry("wooden_hoe", asArr("wooden_club")),
            Map.entry("wooden_shovel", asArr("wooden_club")),
            Map.entry("diamond_helmet", asArr("steel_helmet", "steel_chainmail_helmet", "bronze_helmet", "bronze_chainmail_helmet")),
            Map.entry("diamond_chestplate", asArr("steel_chestplate", "steel_chainmail_chestplate", "bronze_chestplate", "bronze_chainmail_chestplate")),
            Map.entry("diamond_leggings", asArr("steel_leggings", "steel_chainmail_leggings", "bronze_leggings", "bronze_chainmail_leggings")),
            Map.entry("diamond_boots", asArr("steel_boots", "steel_chainmail_boots", "bronze_boots", "bronze_chainmail_boots")),
            Map.entry("iron_helmet", asArr("iron_helmet:", "iron_chainmail_helmet:", "copper_helmet", "bronze_chainmail_helmet")),
            Map.entry("iron_chestplate", asArr("iron_chestplate:", "iron_chainmail_chestplate:", "copper_chestplate", "copper_chainmail_chestplate")),
            Map.entry("iron_leggings", asArr("iron_leggings:", "iron_chainmail_leggings:", "copper_leggings", "copper_chainmail_leggings")),
            Map.entry("iron_boots", asArr("iron_boots:", "iron_chainmail_boots:", "copper_boots", "copper_chainmail_boots")),
            Map.entry("iron_shovel", asArr("iron_shovel:", "copper_shovel")),
            Map.entry("iron_pickaxe", asArr("iron_pickaxe:", "copper_pickaxe")),
            Map.entry("iron_axe", asArr("iron_axe:", "copper_axe")),
            Map.entry("iron_sword", asArr("iron_sword:", "copper_sword")),
            Map.entry("iron_hoe", asArr("iron_hoe:", "copper_hoe"))
    );
    
    private static ResourceLocation[] asArr(String ...names){
        var arr = new ResourceLocation[names.length];
        for(int i = 0; i < names.length; i++){
            var name = names[i];
            var nameClear = name.replace(":", "");
            arr[i] = new ResourceLocation(name.contains(":") ? "minecraft" : "better_progression", nameClear);
        }
        return arr;
    }
    
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTable lootTable = event.getTable();
        LootTableAccessor lootTableAccessor = (LootTableAccessor) lootTable;

        for (LootPool pool : lootTableAccessor.getPools()) {
            LootPoolAccessor lootPoolAccessor = (LootPoolAccessor) pool;
            List<LootPoolEntryContainer> entries = new ArrayList<>(Arrays.asList(lootPoolAccessor.getEntries()));

            var toAdd = new ArrayList<LootPoolEntryContainer>();
            for (var entry : entries) {
                if (!hasSubstitute(entry)) continue;

                var key = lootToString(entry);
                if (key == null) continue;
                var randomLoot = getRandomLootItem(key, entry);
                toAdd.add(randomLoot);
            }
            entries.removeIf(LootTableHandler::hasSubstitute);
            entries.addAll(toAdd);

            lootPoolAccessor.setEntries(entries.toArray(new LootPoolEntryContainer[0]));
        }
    }

    private static boolean hasSubstitute(LootPoolEntryContainer entry) {
        var key = lootToString(entry);
        return key != null && SUBSTITUTE_ITEMS.containsKey(key);
    }

    private static LootItemFunction[] getFunctionsFromEntry(LootPoolEntryContainer entry) {
        if (entry instanceof LootItem) {
            return ((LootPoolSingletonContainerAccessor) entry).getFunctions();
        }
        return new LootItemFunction[0];
    }

    private static String lootToString(LootPoolEntryContainer pool) {
        if (pool instanceof LootItem item) {
            var accessor = (LootItemAccessor) item;
            return accessor.getItem().toString();
        }
        
        return null;
    }

    private static LootPoolEntryContainer getRandomLootItem(String key, LootPoolEntryContainer original) {
        var valueArr = SUBSTITUTE_ITEMS.get(key);
        var randomNum = ThreadLocalRandom.current().nextInt(0, valueArr.length);
        var item = ForgeRegistries.ITEMS.getValue(valueArr[randomNum]);
        if (item == null) {
            System.err.println("Could not find suitable loot item for " + key);
            return null;
        }
        var originalFunctions = getFunctionsFromEntry(original);
        var lootItem = LootItem.lootTableItem(item);
        //((LootPoolSingletonContainerAccessor)lootItem).setFunctions(originalFunctions);
        return lootItem.build();
    }

}
