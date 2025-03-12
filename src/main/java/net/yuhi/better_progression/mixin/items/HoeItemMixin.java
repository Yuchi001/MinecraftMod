package net.yuhi.better_progression.mixin.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.item.interfaces.LayerableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin implements LayerableItem{
    
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = pContext.getPlayer();
        Level level = (Level) pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof CropBlock) {
            ItemStack heldItem = player.getMainHandItem();
            if (!(heldItem.getItem() instanceof HoeItem)) return;

            var crop = (CropBlock) state.getBlock();
            if (!crop.isMaxAge(state)) return;
            
            if (level.isClientSide) {
                level.playSound(player, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            } else {
                for (var drop : Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos), player, heldItem)){
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
                }

                heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(pContext.getHand()));
                level.setBlockAndUpdate(pos, state.setValue(CropBlock.AGE, 0));
            }
            
            cir.setReturnValue(InteractionResult.SUCCESS);
            cir.cancel();
        }
    }
}
