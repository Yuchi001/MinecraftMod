package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlocks;

public class TallEndFlowerBlock extends TallFlowerBlock {
    public TallEndFlowerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(ModBlocks.END_STONE_GRASS_BLOCK.get()) || super.mayPlaceOn(pState, pLevel, pPos);
    }
}
