package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.menu.custom.BetterEnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTableBlock.class)
public class EnchantmentTableBlockMixin {

    @Inject(method = "getMenuProvider", at = @At("HEAD"), cancellable = true)
    public void getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos, CallbackInfoReturnable<MenuProvider> cir) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof EnchantmentTableBlockEntity) {
            Component component = ((Nameable)blockentity).getDisplayName();
            cir.setReturnValue(new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> new BetterEnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(pLevel, pPos)), component));
        } else {
            cir.setReturnValue(null);
        }
        cir.cancel();
    }
}
