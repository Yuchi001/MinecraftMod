package net.yuhi.better_progression.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.custom.ChargedSoulSandBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ChargedSoulSandBlockEntity extends BlockEntity {
    public static final int MINIMUM_CHARGES_TO_ACTIVATE = 5;
    
    private static final String CHARGES_KEY = "charges";
    private static final String IS_CORRECTLY_BUILT_KEY = "isCorrectlyBuilt";
    private static final String IS_CHARGED_KEY = "is_charged";

    private int charges;
    private boolean isCorrectlyBuilt;
    private boolean isCharged;

    public ChargedSoulSandBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHARGED_SOUL_SAND_BLOCK_ENTITY.get(), pos, state);
    }


    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(CHARGES_KEY, charges);
        tag.putBoolean(IS_CORRECTLY_BUILT_KEY, isCorrectlyBuilt);
        tag.putBoolean(IS_CHARGED_KEY, isCharged);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        charges = tag.getInt(CHARGES_KEY);
        isCorrectlyBuilt = tag.getBoolean(IS_CORRECTLY_BUILT_KEY);
        isCharged = tag.getBoolean(IS_CHARGED_KEY);
    }

    public int getCharges() {
        return charges;
    }
    
    public boolean isActive() {
        return isCharged && isCorrectlyBuilt;
    }

    public boolean addCharges(int charges) {
        this.charges += charges;
        if (this.charges > MINIMUM_CHARGES_TO_ACTIVATE) this.charges = MINIMUM_CHARGES_TO_ACTIVATE;
        this.isCharged = this.charges == MINIMUM_CHARGES_TO_ACTIVATE;
        setChanged();
        return this.isCharged;
    }
    
    public void useCharge() {
        isCharged = this.charges >= MINIMUM_CHARGES_TO_ACTIVATE;
    }

    public boolean isCorrectlyBuilt() {
        return isCorrectlyBuilt;
    }

    public void setCorrectlyBuilt(boolean isCorrectlyBuilt) {
        this.isCorrectlyBuilt = isCorrectlyBuilt;
        setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChargedSoulSandBlockEntity blockEntity) {
        var isBuildCorrectly = ChargedSoulSandBlock.isBuildCorrectly(level, pos);
        blockEntity.setCorrectlyBuilt(isBuildCorrectly);

        boolean isActive = blockEntity.isActive();

        BlockPos abovePos = pos.above();
        BlockState aboveBlockState = level.getBlockState(abovePos);

        if (isActive && isBuildCorrectly) {
            if (aboveBlockState.isAir())  level.setBlock(abovePos, Blocks.SOUL_FIRE.defaultBlockState(), 3);
        } else if (aboveBlockState.is(Blocks.SOUL_FIRE)) level.removeBlock(abovePos, false);
        
        if (!level.isClientSide && blockEntity.isActive()) {
            long timeOfDay = level.getDayTime() % 24000L;

            if (timeOfDay == 0) {
                blockEntity.useCharge();
                if (level.getBlockState(abovePos).is(Blocks.SOUL_FIRE)) {
                    level.removeBlock(abovePos, false);
                }
            }
        }
    }

    @Nullable
    public static <T extends BlockEntity> BlockEntityTicker<T> createChargedSoulSandBlockTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<? extends ChargedSoulSandBlockEntity> pClientType) {
        return pLevel.isClientSide ? null : createTickerHelper(pServerType, pClientType, ChargedSoulSandBlockEntity::tick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
        return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }
}
