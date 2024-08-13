package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.yuhi.better_progression.block.custom.BrakeRail;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class MinecartMixin {

    @Inject(method = "moveAlongTrack", at = @At("HEAD"))
    private void increaseSpeedAlongTrack(BlockPos pPos, BlockState pState, CallbackInfo ci) {
        var cart = (AbstractMinecart) (Object) this;
        if(cart instanceof Minecart) return;

        double normalSpeedMultiplier = 2.0;
        double brakeMultiplier = 0.5;

        if (!(pState.getBlock() instanceof BaseRailBlock railBlock)) return;

        var speedMultiplier = railBlock instanceof BrakeRail brakeRail && shouldBrake(brakeRail) ? brakeMultiplier : normalSpeedMultiplier;

        cart.setDeltaMovement(cart.getDeltaMovement().multiply(speedMultiplier, 1.0, speedMultiplier));
    }

    private boolean shouldBrake(BrakeRail brakeRail, RailShape railShape) {
        if (brakeRail == null) return false;

    }

    @Inject(method = "getMaxSpeedWithRail", at = @At("HEAD"), cancellable = true)
    private void getMaxSpeedWithRail(CallbackInfoReturnable<Double> cir) {
        double newMaxSpeed = 0.8;
        cir.setReturnValue(newMaxSpeed);
    }
}