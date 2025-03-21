package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.entity.AlchemyTableBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlock extends BaseEntityBlock {
    public AlchemyTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlchemyTableBlockEntity(pPos, pState);
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALCHEMY_TABLE.get(), pLevel.isClientSide ? AlchemyTableBlockEntity::clientTick : AlchemyTableBlockEntity::serverTick);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof AlchemyTableBlockEntity alchemyTableBlockEntity) {
            if (pLevel.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                pPlayer.openMenu((MenuProvider)blockentity);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean moved) {
        if (level.isClientSide) return;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AlchemyTableBlockEntity entity)
            entity.setNeighborChanged(level.getBlockState(neighborPos), pos);
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.is(pNewState.getBlock())) return;

        var blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof AlchemyTableBlockEntity) {
            if (pLevel instanceof ServerLevel) {
                Containers.dropContents(pLevel, pPos, (AlchemyTableBlockEntity)blockentity);
            }

            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
