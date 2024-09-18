package net.yuhi.better_progression.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlockEntities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EssenceSpawnerBlockEntity extends BlockEntity {
    private final BaseSpawner spawner = new BaseSpawner() {
        public void broadcastEvent(Level pLevel, @NotNull BlockPos pPos, int p_155769_) {
            pLevel.blockEvent(pPos, Blocks.SPAWNER, p_155769_, 0);
        }

        public void setNextSpawnData(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.setNextSpawnData(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState blockstate = p_155771_.getBlockState(p_155772_);
                p_155771_.sendBlockUpdated(p_155772_, blockstate, blockstate, 4);
            }

        }

        public net.minecraft.world.level.block.entity.@NotNull BlockEntity getSpawnerBlockEntity(){ return EssenceSpawnerBlockEntity.this; }
    };

    public EssenceSpawnerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ESSENCE_SPAWNER.get(), pPos, pBlockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.spawner.save(pTag);
    }

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, EssenceSpawnerBlockEntity pBlockEntity) {
        pBlockEntity.spawner.clientTick(pLevel, pPos);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, EssenceSpawnerBlockEntity pBlockEntity) {
        pBlockEntity.spawner.serverTick((ServerLevel)pLevel, pPos);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = this.saveWithoutMetadata();
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    public boolean triggerEvent(int pId, int pType) {
        return this.spawner.onEventTriggered(this.level, pId) ? true : super.triggerEvent(pId, pType);
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void setEntityId(EntityType<?> pType, RandomSource pRandom) {
        this.spawner.setEntityId(pType, this.level, pRandom, this.worldPosition);
    }

    public BaseSpawner getSpawner() {
        return this.spawner;
    }
}
