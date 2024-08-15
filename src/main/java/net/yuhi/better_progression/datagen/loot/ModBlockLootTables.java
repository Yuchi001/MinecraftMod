package net.yuhi.better_progression.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;

import java.util.Set;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.TIN_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_TIN_BLOCK.get());
        this.dropSelf(ModBlocks.BRAKE_RAIL.get());
        //this.dropSelf(ModBlocks.DIRECTIONAL_BRAKE_RAIL.get());
        
        this.dropSelf(ModBlocks.STANNIN_ORE.get());
        
        this.add(ModBlocks.PINK_QUARTZ_ORE.get(),
                block -> createOreDrops(ModBlocks.PINK_QUARTZ_ORE.get(), ModItems.PINK_QUARTZ.get(), 1, 1));
        
        this.add(ModBlocks.TIN_ORE.get(),
                block -> createOreDrops(ModBlocks.TIN_ORE.get(), getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 1, 3));
    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item, int min, int max) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
