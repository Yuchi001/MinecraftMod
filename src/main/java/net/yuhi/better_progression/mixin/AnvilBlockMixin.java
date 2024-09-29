package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.menu.custom.BetterAnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
    
    @Inject(method = "getMenuProvider", at = @At("HEAD"), cancellable = true)
    private void getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos, CallbackInfoReturnable<MenuProvider> cir) {
        cir.setReturnValue(new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> new BetterAnvilMenu(p_48785_, p_48786_, ContainerLevelAccess.create(pLevel, pPos)), Component.translatable("container.repair")));
        cir.cancel();
    }
}
