package net.yuhi.better_progression.events;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.block.custom.ChargedSoulSandBlock;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import net.yuhi.better_progression.item.custom.MobEssenceItem;

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
        if (!(heldItem.getItem() instanceof MobEssenceItem mobEssenceItem)) return;

        if(!ChargedSoulSandBlock.isBuildCorrectly(world, pos)) return;

        if (world.isClientSide) {
            world.playSound(player, pos, SoundEvents.SOUL_SAND_BREAK, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
            return;
        }
        
        world.setBlock(pos, ModBlocks.CHARGED_SOUL_SAND.get().defaultBlockState(), 3);
    }
}
