package net.yuhi.better_progression.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.yuhi.better_progression.block.ModBlocks;

public class EndStoneGrassReplacerFeature extends Feature<NoneFeatureConfiguration> {
    public EndStoneGrassReplacerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel world = context.level();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutablePosAir = new BlockPos.MutableBlockPos();

        int radius = 10; // Define a radius to apply the replacement

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                mutablePosAir.set(pos.getX() + x, pos.getY() + 1, pos.getZ() + z);
                BlockState airTestState = world.getBlockState(mutablePosAir);
                if (!airTestState.isAir()) continue;
                
                mutablePos.set(pos.getX() + x, pos.getY(), pos.getZ() + z);
                BlockState state = world.getBlockState(mutablePos);
                
                if (state.is(Blocks.END_STONE) || state.is(ModBlocks.END_TIN_ORE.get())) {
                    world.setBlock(mutablePos, ModBlocks.END_STONE_GRASS_BLOCK.get().defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}
