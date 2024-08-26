package net.yuhi.better_progression.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterProgression.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (ModBlocks.BlockRegistryPair<Block> blockData : ModBlocks.BLOCKS_DATA) {
            switch (blockData.blockType) {
                case Simple -> blockWithItem(blockData.block);
                case Stairs -> stairsWithItem(blockData.block);
                case Slab -> slabWithItem(blockData.block);
                //case Rail -> blockWithItem(blockData.block);
            }
        }
    }
    
    private ResourceLocation getBlockTexture(Block block) {
        var currentResourceLocation = ForgeRegistries.BLOCKS.getKey(block);
        if (currentResourceLocation == null) return null;
        var blockRawName = currentResourceLocation.getPath();
        return new ResourceLocation(currentResourceLocation.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + blockRawName.substring(0, blockRawName.lastIndexOf('_')) + "_block");
    } 

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
    
    private void stairsWithItem(RegistryObject<Block> block) {
        var blockTexture = getBlockTexture(block.get());
        var stairRegistryKey = ForgeRegistries.BLOCKS.getKey(block.get());
        if(stairRegistryKey == null) return;
        var stairModel = models().stairs(stairRegistryKey.getPath(), blockTexture, blockTexture, blockTexture);
        simpleBlockItem(block.get(), stairModel);
        stairsBlock((StairBlock) block.get(), getBlockTexture(block.get()));
    }

    private void slabWithItem(RegistryObject<Block> block) {
        var blockTexture = getBlockTexture(block.get());
        var stairRegistryKey = ForgeRegistries.BLOCKS.getKey(block.get());
        if(stairRegistryKey == null) return;
        var stairModel = models().slab(stairRegistryKey.getPath(), blockTexture, blockTexture, blockTexture);
        simpleBlockItem(block.get(), stairModel);
        slabBlock((SlabBlock) block.get(), getBlockTexture(block.get()), getBlockTexture(block.get()));
    }
}
