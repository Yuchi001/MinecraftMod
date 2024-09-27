package net.yuhi.better_progression.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.enums.EHoeItemDropProps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.yuhi.better_progression.item.enums.EHoeItemDropProps.hoeDropCount;

@Mixin(Block.class)
public class BlockMixin {

    @Shadow @Nullable private Item item;

    @Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void getDrops(BlockState pState, ServerLevel pLevel, BlockPos pPos, BlockEntity pBlockEntity, Entity pEntity, ItemStack pTool, CallbackInfoReturnable<List<ItemStack>> cir) {
        if(pLevel.isClientSide) return;

        if(!(pTool.getItem() instanceof HoeItem hoe)) return;
        
        var drops = new ArrayList<ItemStack>();

        if (pState.is(BlockTags.LEAVES)) {
            try {
                var maxTries = 100;
                List<ItemStack> randomDropList = new ArrayList<>();
                for (var y = 0; y < maxTries; y++) {
                    LootContext.Builder lootcontext$builder = (new LootContext.Builder(pLevel)).withRandom(pLevel.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pPos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, pBlockEntity);
                    randomDropList = pState.getDrops(lootcontext$builder);
                    if (!randomDropList.isEmpty()) break;
                }

                var randomDrop = new Random(0).nextInt(randomDropList.size());
                drops.add(randomDropList.get(randomDrop));
            } catch (Exception ignored) {}
            
            var isOak = pState.is(Blocks.OAK_LEAVES) || pState.is(Blocks.DARK_OAK_LEAVES) || pState.is(ModBlocks.END_OAK_LEAVES.get());
            
            var shouldSpawnApple = hoeDropCount(hoe.getTier(), isOak);
            for (var i = 0; i < shouldSpawnApple; i++) drops.add(new ItemStack(Items.APPLE));
        }

        var isTallGrass = pState.is(Blocks.TALL_GRASS);
        if (pState.is(Blocks.GRASS) || isTallGrass) {
            drops.add(new ItemStack(Items.WHEAT_SEEDS, isTallGrass ? 2 : 1));
        }
        
        cir.setReturnValue(drops);
        cir.cancel();
    }
}