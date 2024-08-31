package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.yuhi.better_progression.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class ModFlammableRotatePillarBlock extends RotatedPillarBlock {
    public ModFlammableRotatePillarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }
    
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            var dataMatch = ModBlocks.BLOCKS_DATA.stream().filter(data -> data.isLog && state.is(data.block.get())).findFirst().orElse(null);
            if (dataMatch != null) {
                return dataMatch.strippedLogBlock.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
