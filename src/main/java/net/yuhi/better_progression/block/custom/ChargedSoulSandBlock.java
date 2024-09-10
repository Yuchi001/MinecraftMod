package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import net.yuhi.better_progression.item.custom.MobEssenceItem;
import org.jetbrains.annotations.Nullable;

import static net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity.createChargedSoulSandBlockTicker;

public class ChargedSoulSandBlock extends SoulSandBlock implements EntityBlock {
    public static final IntegerProperty CHARGES = IntegerProperty.create("charges", 0, 5);
    
    public ChargedSoulSandBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGES, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
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
        if (pLevel.isClientSide) {
            BlockState state = pLevel.getBlockState(pPos);
            int charges = state.getValue(ChargedSoulSandBlock.CHARGES);
            pLevel.playSound(pPlayer, pPos, charges + essenceItem.getStacks() >= ChargedSoulSandBlockEntity.MINIMUM_CHARGES_TO_ACTIVATE ?  SoundEvents.END_PORTAL_SPAWN : SoundEvents.SOUL_SAND_PLACE, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
            return;
        }
        
        var wasCharged = chargedSoulSandBlockEntity.addCharges(essenceItem.getStacks());

        BlockState currentState = pLevel.getBlockState(pPos);
        pLevel.setBlock(pPos, currentState.setValue(ChargedSoulSandBlock.CHARGES, chargedSoulSandBlockEntity.getCharges()), 3);
        
        heldItem.shrink(1);
        if (wasCharged) activateTotem(pLevel, pPos, pPlayer);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof ChargedSoulSandBlockEntity chargedSoulSand) {
            reduceStacksOverTime(chargedSoulSand, pLevel, pPos);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).is(Items.FLINT_AND_STEEL)) return InteractionResult.FAIL;
        
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof ChargedSoulSandBlockEntity chargedSoulSand) {
            System.out.println(chargedSoulSand.isActive());
            if (chargedSoulSand.isActive()) return InteractionResult.FAIL;
            
            ItemStack heldItem = pPlayer.getMainHandItem();
            if (heldItem.getItem() instanceof MobEssenceItem essenceItem) {
                var isBuildCorrectly = isBuildCorrectly(pLevel, pPos);
                if (!pLevel.isClientSide) chargedSoulSand.setCorrectlyBuilt(isBuildCorrectly);
                if (isBuildCorrectly) {
                    addCharges(chargedSoulSand, essenceItem, heldItem, pLevel, pPos, pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
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
        BlockState state = pLevel.getBlockState(pPos);
        int charges = state.getValue(ChargedSoulSandBlock.CHARGES);
        return 0.6F * charges;
    }

    public void reduceStacksOverTime(ChargedSoulSandBlockEntity chargedSoulSandBlockEntity, Level pLevel, BlockPos pPos) {
        if (pLevel.isClientSide()) return;

        int currentStacks = chargedSoulSandBlockEntity.getCharges();

        if (currentStacks <= 0) {
            pLevel.setBlock(pPos, Blocks.SOUL_SAND.defaultBlockState(), 3);
            return;
        }

        int ticksInADay = 24000;
        int minTicks = 7 * ticksInADay;  // 7 dni
        int maxTicks = 14 * ticksInADay; // 14 dni

        int randomTickInterval = pLevel.getRandom().nextInt(maxTicks - minTicks + 1) + minTicks;

        if (pLevel.getGameTime() % randomTickInterval == 0) {
            chargedSoulSandBlockEntity.addCharges(-1);
        }
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createChargedSoulSandBlockTicker(pLevel, pBlockEntityType, ModBlockEntities.CHARGED_SOUL_SAND_BLOCK_ENTITY.get());
    }
}