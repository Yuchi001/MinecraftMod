package net.yuhi.better_progression.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.block.custom.ChargedSoulSandBlock;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import net.yuhi.better_progression.tag.ModTags;

import static net.minecraftforge.event.entity.player.PlayerInteractEvent.*;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID)
public class BlockInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock event) {
        Player player = event.getEntity();
        Level world = player.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);

        if (!state.getBlock().getClass().equals(SoulSandBlock.class)) return;

        ItemStack heldItem = player.getItemInHand(event.getHand());
        if (!heldItem.is(ModTags.Items.SOUL_SHARD)) return;

        if(!ChargedSoulSandBlock.isBuildCorrectly(world, pos)) return;

        if (world.isClientSide) {
            world.playSound(player, pos, SoundEvents.SOUL_SAND_BREAK, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
            event.setCancellationResult(InteractionResult.SUCCESS);
            return;
        }
        
        world.setBlock(pos, ModBlocks.CHARGED_SOUL_SAND.get().defaultBlockState(), 3);
        
        var entity = world.getBlockEntity(pos);
        var block = world.getBlockState(pos);
        if (!(block.getBlock() instanceof ChargedSoulSandBlock chargedSoulSandBlock)) return;
        if (!(entity instanceof ChargedSoulSandBlockEntity chargedSoulSandBlockEntity)) return;
        
        chargedSoulSandBlock.addCharges(chargedSoulSandBlockEntity, heldItem, world, pos, player);
        event.setCancellationResult(InteractionResult.CONSUME);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof CropBlock &&
                !level.isClientSide) {
            ItemStack heldItem = player.getMainHandItem();
            if (!(heldItem.getItem() instanceof HoeItem)) return;
            
            var crop = (CropBlock) state.getBlock();
            if (!crop.isMaxAge(state)) return;

            event.setCanceled(true);

            for (var drop : Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos), player, heldItem)){
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
            }
            
            level.setBlockAndUpdate(pos, event.getState().setValue(CropBlock.AGE, 0));
        }
    }
}
