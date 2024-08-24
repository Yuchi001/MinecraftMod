package net.yuhi.better_progression.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BetterProgression.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(BlockTags.RAILS).add(
                ModBlocks.BRAKE_RAIL.get()
        );
        
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                ModBlocks.TIN_BLOCK.get(),
                ModBlocks.RAW_TIN_BLOCK.get(),
                ModBlocks.TIN_ORE.get(),
                ModBlocks.STANNIN_ORE.get()
        );
        
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.STEEL_BLOCK.get(),
                ModBlocks.BRONZE_BLOCK.get()
        );
        
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                ModBlocks.ENDERITE_BLOCK.get()
        );
        
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.STEEL_BLOCK.get(),
                ModBlocks.TIN_BLOCK.get(),
                ModBlocks.BRONZE_BLOCK.get(),
                ModBlocks.ENDERITE_BLOCK.get(),
                ModBlocks.RAW_TIN_BLOCK.get(),
                ModBlocks.TIN_ORE.get(),
                ModBlocks.PINK_QUARTZ_ORE.get(),
                ModBlocks.STANNIN_ORE.get()
        );

        this.tag(ModTags.Blocks.ORE_TAG).add(
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.COAL_ORE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.GOLD_ORE,
                Blocks.NETHER_GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.EMERALD_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.REDSTONE_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                ModBlocks.STANNIN_ORE.get(),
                ModBlocks.TIN_ORE.get(),
                ModBlocks.PINK_QUARTZ_ORE.get()
        );
    }
}
