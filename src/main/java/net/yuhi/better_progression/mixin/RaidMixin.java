package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Raid.class)
public class RaidMixin {

    @Inject(method = "joinRaid", at = @At("HEAD"), cancellable = true)
    private void onSpawnNextWave(int pWave, Raider pRaider, @Nullable BlockPos pPos, boolean pIsRecruited, CallbackInfo info) {
        if(pRaider instanceof Evoker){
            pRaider.remove(Entity.RemovalReason.DISCARDED);
            info.cancel();
        }
    }
}