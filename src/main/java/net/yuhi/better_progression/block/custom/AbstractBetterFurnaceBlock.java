package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.yuhi.better_progression.block.entity.AbstractBetterFurnaceBlockEntity;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBetterFurnaceBlock extends AbstractFurnaceBlock {

    protected AbstractBetterFurnaceBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.is(pNewState.getBlock())) return;

        var blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof AbstractBetterFurnaceBlockEntity) {
            if (pLevel instanceof ServerLevel) {
                Containers.dropContents(pLevel, pPos, (AbstractBetterFurnaceBlockEntity)blockentity);
                ((AbstractBetterFurnaceBlockEntity)blockentity).getRecipesToAwardAndPopExperience((ServerLevel)pLevel, Vec3.atCenterOf(pPos));
            }

            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
