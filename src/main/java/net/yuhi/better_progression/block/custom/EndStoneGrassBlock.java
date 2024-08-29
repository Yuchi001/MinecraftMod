package net.yuhi.better_progression.block.custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.worldgen.ModConfiguredFeatures;
import net.yuhi.better_progression.worldgen.ModPlacedFeatures;

public class EndStoneGrassBlock extends Block implements BonemealableBlock {
    public EndStoneGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean isValidBonemealTarget(LevelReader pLevelReader, BlockPos pBlockPos, BlockState pBlockState, boolean p_256630_) {
        return pLevelReader.getBlockState(pBlockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandomSource, BlockPos pBlockPos, BlockState pBlockState) {
        return true;
    }

    private static boolean canBeGrass(BlockState pState, LevelReader pLevelReader, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pLevelReader.getBlockState(blockpos);
        if (blockstate.getFluidState().getAmount() == 8) return false;

        int i = LayerLightEngine.getLightBlockInto(pLevelReader, pState, pPos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(pLevelReader, blockpos));
        return i < pLevelReader.getMaxLightLevel();    
    }
    
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!canBeGrass(pState, pLevel, pPos)) {
            if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            pLevel.setBlockAndUpdate(pPos, Blocks.END_STONE.defaultBlockState());
        }
    }

    @Override
    public void performBonemeal(ServerLevel pServerLevel, RandomSource pRandomSource, BlockPos pBlockPos, BlockState pBlockState) {
        BlockPos blockpos = pBlockPos.above();
        BlockState blockstate = ModBlocks.END_STONE_GRASS_BLOCK.get().defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = pServerLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);

        int minRange = 1;
        int maxRange = 5;

        for (BlockPos pos : BlockPos.betweenClosed(pBlockPos.offset(-maxRange, -maxRange, -maxRange), pBlockPos.offset(maxRange, maxRange, maxRange))) {
            double distance = pos.distManhattan(pBlockPos);
            if (distance > maxRange || distance < minRange) continue;

            BlockState currentState = pServerLevel.getBlockState(pos);
            if (!currentState.is(Blocks.END_STONE) || !pServerLevel.isEmptyBlock(pos.above())) continue;

            double chance = 1.0 / distance;
            if (pRandomSource.nextDouble() > chance) continue;

            pServerLevel.setBlock(pos, blockstate, 3);
        }
        
        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for(int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.offset(pRandomSource.nextInt(3) - 1, (pRandomSource.nextInt(3) - 1) * pRandomSource.nextInt(3) / 2, pRandomSource.nextInt(3) - 1);
                if (!pServerLevel.getBlockState(blockpos1.below()).is(this) || pServerLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(pServerLevel, blockpos1)) {
                    continue label49;
                }
            }

            BlockState blockstate1 = pServerLevel.getBlockState(blockpos1);
            if (blockstate1.is(blockstate.getBlock()) && pRandomSource.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(pServerLevel, pRandomSource, blockpos1, blockstate1);
            }

            RegistryAccess registryAccess = pServerLevel.registryAccess();
            Registry<ConfiguredFeature<?, ?>> registry = registryAccess.registryOrThrow(Registries.CONFIGURED_FEATURE);

            List<ResourceKey<ConfiguredFeature<?, ?>>> keys = List.of(ModConfiguredFeatures.END_STONE_GRASS_VEGETATION, ModConfiguredFeatures.END_STONE_GRASS_TALL_VEGETATION, ModConfiguredFeatures.END_STONE_GRASS_FLOWERS_VEGETATION);

            List<Holder<ConfiguredFeature<?, ?>>> holders = keys.stream()
                    .map(key -> registry.getHolder(key).orElseThrow())
                    .collect(Collectors.toList());

            List<ConfiguredFeature<?, ?>> features = holders.stream()
                    .map(Holder::value)
                    .collect(Collectors.toList());
            
            if (blockstate1.isAir()) {
                Holder<PlacedFeature> holder;
                if (pRandomSource.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = features;
                    if (list.isEmpty()) {
                        continue;
                    }

                    holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
                } else {
                    if (!optional.isPresent()) {
                        continue;
                    }

                    holder = optional.get();
                }

                holder.value().place(pServerLevel, pServerLevel.getChunkSource().getGenerator(), pRandomSource, blockpos1);
            }
        }

    }
}