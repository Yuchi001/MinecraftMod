package net.yuhi.better_progression.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.entity.custom.ThrownWeaponEntity;
import net.yuhi.better_progression.item.enums.EMaterialType;


public abstract class ThrowableItem extends SwordItem implements Vanishable {
    private final int useTime;
    private final boolean shouldRotate;
    private float scale = 0.88f;

    public ThrowableItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, int useTime, boolean shouldRotate, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.useTime = useTime;
        this.shouldRotate = shouldRotate;
    }

    public ThrowableItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, int useTime, boolean shouldRotate, float scale, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.useTime = useTime;
        this.shouldRotate = shouldRotate;
        this.scale = scale;
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (!(pEntityLiving instanceof Player player)) return;
       
        int i = this.getUseDuration(pStack) - pTimeLeft;
        if (i < useTime) return;

        if (!pLevel.isClientSide) {
            pStack.hurtAndBreak(1, player, (p_43388_) -> {
                p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
            });
            
            var thrownDagger = new ThrownWeaponEntity(pLevel, player, pStack, EMaterialType.GetMaterialType(pStack), shouldRotate, scale);
            thrownDagger.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.getAbilities().instabuild) {
                thrownDagger.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            pLevel.addFreshEntity(thrownDagger);
            pLevel.playSound((Player)null, thrownDagger, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
                player.getInventory().removeItem(pStack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }


    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        var itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.isCrouching())
            return InteractionResultHolder.pass(itemstack);

        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) 
            return InteractionResultHolder.fail(itemstack);

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43414_) -> {
            p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a {@link net.minecraft.world.level.block.Block} is destroyed using this Item. Return {@code true} to
     * trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double)pState.getDestroySpeed(pLevel, pPos) != 0.0D) {
            pStack.hurtAndBreak(2, pEntityLiving, (p_43385_) -> {
                p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
}
