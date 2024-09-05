package net.yuhi.better_progression.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.BlockTags;
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
import net.yuhi.better_progression.block.blockdata.ECustomTag;
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
        for(var blockData : ModBlocks.BLOCKS_DATA) {
            if (blockData.dropSelf) this.dropSelf(blockData.block.get());
            else if (blockData.drop != null) this.dropOther(blockData.block.get(), blockData.drop.get());
            else if (blockData.customTag == ECustomTag.NO_DROP) this.add(blockData.block.get(), block -> LootTable.lootTable());
        }
        
        this.add(ModBlocks.END_OAK_LEAVES.get(), block -> createLeavesDrops(ModBlocks.END_OAK_LEAVES.get(), ModBlocks.END_OAK_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));
        
        this.add(ModBlocks.PINK_QUARTZ_ORE.get(),
                block -> createOreDrops(ModBlocks.PINK_QUARTZ_ORE.get(), ModItems.PINK_QUARTZ.get(), 1, 1));

        this.add(ModBlocks.DRAGON_DEBRIS.get(),
                block -> createOreDrops(ModBlocks.DRAGON_DEBRIS.get(), ModItems.DRAGON_REMAINS.get(), 1, 2));
        
        this.add(ModBlocks.TIN_ORE.get(), block -> createOreDrops(ModBlocks.TIN_ORE.get(), getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 1, 3));
        this.add(ModBlocks.DEEPSLATE_TIN_ORE.get(), block -> createOreDrops(ModBlocks.TIN_ORE.get(), getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 1, 3));
        this.add(ModBlocks.END_TIN_ORE.get(), block -> createOreDrops(ModBlocks.TIN_ORE.get(), getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 2, 5));
    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item, int min, int max) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
