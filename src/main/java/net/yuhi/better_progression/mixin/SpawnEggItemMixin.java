package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.block.entity.EssenceSpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void modifySpawnerUse(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            cir.setReturnValue(InteractionResult.SUCCESS);
            return;
        }

        ItemStack itemstack = pContext.getItemInHand();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        
        if (blockstate.is(Blocks.SPAWNER)) {
            BlockEntity blockentity = level.getBlockEntity(blockpos);
            if (blockentity instanceof SpawnerBlockEntity) {
                SpawnerBlockEntity spawnerBlockEntity = (SpawnerBlockEntity) blockentity;
                EntityType<?> entitytype = ((SpawnEggItem) (Object) this).getType(itemstack.getTag());
                spawnerBlockEntity.setEntityId(entitytype, level.getRandom());
                blockentity.setChanged();
                level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
                itemstack.shrink(1);
                cir.setReturnValue(InteractionResult.CONSUME);
            }
        }
        else if (blockstate.is(ModBlocks.ESSENCE_SPAWNER.get())) {
            BlockEntity blockentity = level.getBlockEntity(blockpos);
            if (blockentity instanceof EssenceSpawnerBlockEntity essenceSpawnerBlockEntity) {
                EntityType<?> entitytype = ((SpawnEggItem) (Object) this).getType(itemstack.getTag());
                System.out.println("Dupa2");
                essenceSpawnerBlockEntity.setEntityId(entitytype, level.getRandom());
                blockentity.setChanged();
                level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
                itemstack.shrink(1);
                cir.setReturnValue(InteractionResult.CONSUME);
            }
        }
    }
}