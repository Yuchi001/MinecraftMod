package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.yuhi.better_progression.block.custom.IBrewingStand;
import net.yuhi.better_progression.mixin.accessor.BrewingStandBlockEntityAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(BrewingStandBlock.class)
public class BrewingStandBlockMixin implements IBrewingStand {
    @Override
    public ItemStack getFuel() {
        return new ItemStack(Items.BLAZE_POWDER);
    }
    
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir) {
        var entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof BrewingStandBlockEntity brewingStand) {
            var accessor = (BrewingStandBlockEntityAccessor)brewingStand;
            var item = pPlayer.getMainHandItem();
            if (!item.is(Items.POTION)) {
                var dropped = false;
                for (var i : accessor.getItems()) {
                    if (i.isEmpty()) continue;

                    pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f, i.copy()));
                    i.shrink(1);
                    dropped = true;
                }
                
                updateState(accessor, brewingStand, pLevel, pPos);
                if (pLevel.isClientSide && dropped) {
                    pLevel.playSound(pPlayer, pPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }
                
                cir.setReturnValue(dropped ? InteractionResult.CONSUME : InteractionResult.PASS);
                return;
            }

            if (PotionUtils.getPotion(item) != Potions.WATER) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }
            
            var items = accessor.getItems();
            var emptySpaceIndex = -1;
            for (var i = 0; i < 3; i++) {
                if (!items.get(i).isEmpty()) continue;
                emptySpaceIndex = i;
                break;
            }
            if (emptySpaceIndex == -1) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }

            ItemStack waterBottle = new ItemStack(Items.POTION);
            CompoundTag tag = new CompoundTag();
            tag.putString("Potion", "minecraft:water");
            waterBottle.setTag(tag);
            items.set(emptySpaceIndex, waterBottle);
            
            accessor.setItems(items);
            
            pPlayer.awardStat(Stats.INTERACT_WITH_BREWINGSTAND);
            item.shrink(1);
            
            updateState(accessor, brewingStand, pLevel, pPos);
            if (pLevel.isClientSide) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.GLASS_STEP, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }
            
            cir.setReturnValue(InteractionResult.CONSUME);
        }
    }
    
    private void updateState(BrewingStandBlockEntityAccessor accessor, BrewingStandBlockEntity entity, Level pLevel, BlockPos pPos){
        boolean[] aboolean = getPotionBits(accessor.getItems());
        accessor.setLastPotionCount(aboolean);
        
        var pState = pLevel.getBlockState(pPos);

        for(int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; ++i) {
            pState = pState.setValue(BrewingStandBlock.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
        }

        pLevel.setBlock(pPos, pState, 2);

        entity.setChanged();
        pLevel.sendBlockUpdated(pPos, pState, pState, 3);
    }

    private boolean[] getPotionBits(NonNullList<ItemStack> items){
        boolean[] aboolean = new boolean[3];

        for(int i = 0; i < 3; ++i) {
            if (!items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }
}
