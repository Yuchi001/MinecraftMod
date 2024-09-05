package net.yuhi.better_progression.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;import net.yuhi.better_progression.block.custom.ChargedSoulSandBlock;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Unique
    private final ServerPlayer betterProgression$player = (ServerPlayer) (Object) this;

    @Inject(method = "startSleepInBed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startSleepInBed(Lnet/minecraft/core/BlockPos;)Lcom/mojang/datafixers/util/Either;", shift = At.Shift.BEFORE), cancellable = true)
    private void beforeSuperStartSleepInBed(BlockPos pAt, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        if (!betterProgression$IsTotemActive(pAt)) {
            betterProgression$player.displayClientMessage(Component.translatable("block.better_progression.bed_message_no_sleep"), true);
            cir.setReturnValue(Either.left(Player.BedSleepingProblem.OTHER_PROBLEM));
        }
    }

    @Unique
    private boolean betterProgression$IsTotemActive(BlockPos pPos){
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int radius = 100;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutablePos.set(pPos.getX() + x, pPos.getY() + y, pPos.getZ() + z);
                    BlockState blockState = betterProgression$player.level.getBlockState(mutablePos);
                    if (blockState.getBlock() instanceof ChargedSoulSandBlock chargedSoulSandBlock) {
                        BlockEntity blockEntity = betterProgression$player.level.getBlockEntity(mutablePos);
                        if (blockEntity instanceof ChargedSoulSandBlockEntity chargedSoulSandEntity) {
                            if (chargedSoulSandEntity.isCorrectlyBuilt() && chargedSoulSandEntity.isActive()) {
                                int goldBlocks = chargedSoulSandBlock.countGoldBlocksInRange(betterProgression$player.level, mutablePos, 20);
                                int effectiveRadius = 100 + (goldBlocks * 5);
                                if (pPos.distSqr(pPos) <= effectiveRadius * effectiveRadius) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
