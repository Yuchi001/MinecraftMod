package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class MinecartMixin {

    @Inject(method = "moveAlongTrack", at = @At("HEAD"), cancellable = true)
    private void increaseSpeedAlongTrack(BlockPos pPos, BlockState pState, CallbackInfo ci) {
        AbstractMinecart cart = (AbstractMinecart) (Object) this;
        double normalSpeedMultiplier = 5.0;
        double cornerSpeedMultiplier = 0.5;
        double ascendingSpeedMultiplier = 2.0;

        if (!(pState.getBlock() instanceof BaseRailBlock railBlock)) return;

        RailShape railShape = railBlock.getRailDirection(pState, cart.level, pPos, cart);

        // Ustal prędkość w zależności od kształtu toru
        double speedMultiplier;
        if (railShape.isAscending()) {
            speedMultiplier = ascendingSpeedMultiplier;
        } else if (isCurveShape(railShape)) {
            speedMultiplier = cornerSpeedMultiplier;
        } else {
            speedMultiplier = normalSpeedMultiplier;
        }

        cart.setDeltaMovement(cart.getDeltaMovement().multiply(speedMultiplier, 1.0, speedMultiplier));
    }

    private boolean isCurveShape(RailShape currentShape) {
        return switch (currentShape) {
            case NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST -> true;
            default -> false;
        };
    }

    @Inject(method = "getMaxSpeedWithRail", at = @At("HEAD"), cancellable = true)
    private void getMaxSpeedWithRail(CallbackInfoReturnable<Double> cir) {
        double newMaxSpeed = 0.6; // Domyślnie to jest 0.4D. Możesz dostosować tę wartość
        cir.setReturnValue(newMaxSpeed);
    }
}