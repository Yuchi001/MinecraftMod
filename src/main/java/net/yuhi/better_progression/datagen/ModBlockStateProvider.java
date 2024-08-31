package net.yuhi.better_progression.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.block.blockdata.BlockDataCreator;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterProgression.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var blockData : ModBlocks.BLOCKS_DATA) {
            switch (blockData.textureType) {
                case CUBE_ALL -> blockWithItem(blockData);
                case STAIRS -> stairsWithItem(blockData);
                case SLAB -> slabWithItem(blockData);
                case CROSS -> crossBlockWithItem(blockData);
                case PRESSURE_PLATE -> pressurePlateWithItem(blockData);
                case BUTTON -> button(blockData);
                case TRAPDOOR -> trapdoorWithItem(blockData);
                case DOOR -> door(blockData);
                case PILLAR_TOP -> pillarWithItem(blockData);
                case LOG -> logWithItem(blockData);
                case AXIS -> axisBlockWithItem(blockData);
                case PILLAR_TOP_BOTTOM -> pillarTopBottomWithItem(blockData);
                case FENCE -> fenceWithItem(blockData);
                case FENCE_GATE -> fenceGateWithItem(blockData);
                case SIGN -> sign(blockData);
            }
        }
    }

    private void blockWithItem(BlockDataCreator.BlockData blockData) {
        simpleBlockWithItem(blockData.block.get(), cubeAll(blockData.block.get()));
    }
    
    private void sign(BlockDataCreator.BlockData signBlockData) {
        signBlock((StandingSignBlock) signBlockData.block.get(), (WallSignBlock) signBlockData.twinBlockSupplier.get(), signBlockData.textureSide);
    }

    private void fenceWithItem(BlockDataCreator.BlockData blockData) {
        var fenceModel = models().fenceInventory(blockData.name, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), fenceModel);
        fenceBlock((FenceBlock) blockData.block.get(), blockData.textureSide);
    }

    private void fenceGateWithItem(BlockDataCreator.BlockData blockData) {
        var fenceGateModel = models().fenceGate(blockData.name, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), fenceGateModel);
        fenceGateBlock((FenceGateBlock) blockData.block.get(), blockData.textureSide);
    }
    
    private void pressurePlateWithItem(BlockDataCreator.BlockData blockData) {
        var pressurePlateModel = models().pressurePlate(blockData.name, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), pressurePlateModel);
        pressurePlateBlock((PressurePlateBlock) blockData.block.get(), blockData.textureSide);
    }
    
    private void button(BlockDataCreator.BlockData blockData) {
        buttonBlock((ButtonBlock) blockData.block.get(), blockData.textureSide);
    }
    
    private void crossBlockWithItem(BlockDataCreator.BlockData blockData) {
        simpleBlock(blockData.block.get(), models().cross(blockData.name, blockData.textureItem));
    }
    
    private void stairsWithItem(BlockDataCreator.BlockData blockData) {
        var stairModel = models().stairs(blockData.name, blockData.textureSide, blockData.textureSide, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), stairModel);
        stairsBlock((StairBlock) blockData.block.get(), blockData.textureSide);
    }

    private void slabWithItem(BlockDataCreator.BlockData blockData) {
        var slabModel = models().slab(blockData.name, blockData.textureSide, blockData.textureSide, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), slabModel);
        slabBlock((SlabBlock) blockData.block.get(), blockData.textureSide, blockData.textureSide);
    }

    private void pillarWithItem(BlockDataCreator.BlockData blockData) {
        var logModel = models().cubeTop(blockData.name, blockData.textureSide, blockData.textureTop);
        simpleBlockWithItem(blockData.block.get(), logModel);
    }

    private void pillarTopBottomWithItem(BlockDataCreator.BlockData blockData) {
        var pillarTopBottom = models().cubeBottomTop(blockData.name, blockData.textureSide, blockData.textureBottom, blockData.textureTop);
        simpleBlockWithItem(blockData.block.get(), pillarTopBottom);
    }
    
    private void logWithItem(BlockDataCreator.BlockData blockData) {
        var logModel = models().cubeTop(blockData.name, blockData.textureSide, blockData.textureTop);
        logBlock((RotatedPillarBlock) blockData.block.get());
        simpleBlockItem(blockData.block.get(), logModel);
    }

    private void axisBlockWithItem(BlockDataCreator.BlockData blockData) {
        var logModel = models().cubeTop(blockData.name, blockData.textureSide, blockData.textureTop);
        axisBlock((RotatedPillarBlock) blockData.block.get(), blockData.textureSide, blockData.textureSide);
        simpleBlockItem(blockData.block.get(), logModel);    
    }

    private void door(BlockDataCreator.BlockData blockData) {
        doorBlockWithRenderType((DoorBlock) blockData.block.get(), blockData.textureBottom, blockData.textureTop, "cutout");
    }

    private void trapdoorWithItem(BlockDataCreator.BlockData blockData) {
        var trapdoorModel = models().trapdoorBottom(blockData.name, blockData.textureSide);
        trapdoorBlockWithRenderType((TrapDoorBlock) blockData.block.get(), blockData.textureSide, true, "cutout");
        simpleBlockItem(blockData.block.get(), trapdoorModel);
    }
}
