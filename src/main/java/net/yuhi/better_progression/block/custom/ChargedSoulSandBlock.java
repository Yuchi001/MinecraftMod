package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import net.yuhi.better_progression.item.custom.MobEssenceItem;
import org.jetbrains.annotations.Nullable;

import static net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity.createChargedSoulSandBlockTicker;

public class ChargedSoulSandBlock extends SoulSandBlock implements EntityBlock {
    public ChargedSoulSandBlock(Properties pProperties) {
        super(pProperties);
    }
    
    public static boolean isBuildCorrectly(Level pLevel, BlockPos pPos) {
        var netherrackBlock = pLevel.getBlockState(pPos.below());
        var goldBlock1 = pLevel.getBlockState(pPos.below().below());
        var goldBlock2 = pLevel.getBlockState(pPos.below().below().below());
        
        return goldBlock2.is(Blocks.GOLD_BLOCK) && goldBlock1.is(Blocks.GOLD_BLOCK) && netherrackBlock.is(Blocks.NETHERRACK);
    }

    private void activateTotem(Level pLevel, BlockPos pPos, Player pPlayer) {
        if (pLevel instanceof ServerLevel serverLevel) {
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
            if(lightningbolt == null) return;
            lightningbolt.moveTo(pPos.getX(), pPos.getY() + 1, pPos.getZ());
            lightningbolt.setVisualOnly(true);
            serverLevel.addFreshEntity(lightningbolt);
            
            BlockState fireState = Blocks.SOUL_FIRE.defaultBlockState();
            BlockPos firePos = pPos.above();
            if (pLevel.getBlockState(firePos).canBeReplaced() || pLevel.getBlockState(firePos).isAir()) {
                serverLevel.setBlock(firePos, fireState, 3);
            }

            pPlayer.displayClientMessage(Component.translatable("block.better_progression.totem_message_sleep_well"), true);
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        if (pLevel.isClientSide) return;
        
        var blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof ChargedSoulSandBlockEntity chargedSoulSandBlockEntity) {
            chargedSoulSandBlockEntity.setCorrectlyBuilt(isBuildCorrectly(pLevel, pPos));
        }

        pLevel.scheduleTick(pPos, this, 20);
    }
    
    public void addCharges(ChargedSoulSandBlockEntity chargedSoulSandBlockEntity, MobEssenceItem essenceItem, ItemStack heldItem, Level pLevel, BlockPos pPos, Player pPlayer) {
        var wasCharged = chargedSoulSandBlockEntity.addCharges(essenceItem.getStacks());
        heldItem.shrink(1);
        if (!wasCharged) pLevel.playSound(pPlayer, pPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
        else {
            activateTotem(pLevel, pPos, pPlayer);
            pLevel.playSound(pPlayer, pPos, SoundEvents.SOUL_SAND_PLACE, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) return InteractionResult.CONSUME;
        
        if (pPlayer.getItemInHand(pHand).is(Items.FLINT_AND_STEEL)) return InteractionResult.PASS;
        
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof ChargedSoulSandBlockEntity chargedSoulSand) {
            if (chargedSoulSand.isActive()) return InteractionResult.PASS;
            
            ItemStack heldItem = pPlayer.getMainHandItem();
            if (heldItem.getItem() instanceof MobEssenceItem essenceItem) {
                var isBuildCorrectly = isBuildCorrectly(pLevel, pPos);
                chargedSoulSand.setCorrectlyBuilt(isBuildCorrectly);
                if (isBuildCorrectly) {
                    addCharges(chargedSoulSand, essenceItem, heldItem, pLevel, pPos, pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public int countGoldBlocksInRange(Level level, BlockPos center, int radius) {
        int goldBlockCount = 0;
        for (BlockPos offset : BlockPos.betweenClosed(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius))) {
            if (level.getBlockState(offset).getBlock() == Blocks.GOLD_BLOCK) {
                goldBlockCount++;
            }
        }
        return goldBlockCount;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ChargedSoulSandBlockEntity(pPos, pState);
    }
    
    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0.6F;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createChargedSoulSandBlockTicker(pLevel, pBlockEntityType, ModBlockEntities.CHARGED_SOUL_SAND_BLOCK_ENTITY.get());
    }
}