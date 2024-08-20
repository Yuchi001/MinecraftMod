package net.yuhi.better_progression.mixin;

import com.ibm.icu.impl.Trie;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import net.yuhi.better_progression.block.custom.BrakeRail;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class MinecartMixin {
    private boolean isBraking = false;

    @Inject(method = "moveAlongTrack", at = @At("TAIL"))
    private void increaseSpeedAlongTrack(BlockPos pPos, BlockState pState, CallbackInfo ci) {
        var cart = (AbstractMinecart) (Object) this;
        if (!(cart instanceof Minecart)) return;

        double normalSpeedMultiplier = 5.0;

        if (!(pState.getBlock() instanceof BaseRailBlock railBlock)) return;
        
        isBraking = shouldBrake(cart, railBlock, pState);
        
        cart.setDeltaMovement(cart.getDeltaMovement().multiply(normalSpeedMultiplier, 1.0, normalSpeedMultiplier));
    }

    private boolean shouldBrake(AbstractMinecart cart, BaseRailBlock railBlock, BlockState pState) {
        if(railBlock.getClass() == BrakeRail.class) return true;

        var railShape = pState.getValue(((BaseRailBlock) pState.getBlock()).getShapeProperty());

        switch (railShape) {
            case ASCENDING_NORTH -> {
                return cart.getDeltaMovement().z < 0; // Jedzie na północ pod górę
            }
            case ASCENDING_SOUTH -> {
                return cart.getDeltaMovement().z > 0; // Jedzie na południe pod górę
            }
            case ASCENDING_EAST -> {
                return cart.getDeltaMovement().x > 0; // Jedzie na wschód pod górę
            }
            case ASCENDING_WEST -> {
                return  cart.getDeltaMovement().x < 0; // Jedzie na zachód pod górę
            }
        }
        
        if (!(railBlock instanceof BrakeRail)) return false;

        Vec3 motion = cart.getDeltaMovement();

        return switch (railShape) {
            case NORTH_SOUTH -> motion.z > 0 || motion.z < 0;
            case EAST_WEST -> motion.x > 0 || motion.x < 0;
            default -> false;
        };
    }

    @Inject(method = "getMaxSpeedWithRail", at = @At("HEAD"), cancellable = true)
    private void getMaxSpeedWithRail(CallbackInfoReturnable<Double> cir) {
        double newMaxSpeed = isBraking ? 0.4 : 1.1;
        cir.setReturnValue(newMaxSpeed);
    }
}