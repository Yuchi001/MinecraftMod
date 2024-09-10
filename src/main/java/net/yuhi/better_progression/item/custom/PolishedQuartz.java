package net.yuhi.better_progression.item.custom;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.entity.custom.ThrownPolishedQuartzEntity;

public class PolishedQuartz extends Item {
    private final Type type;
    
    public PolishedQuartz(Properties pProperties, Type type) {
        super(pProperties);
        this.type = type;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            ThrownPolishedQuartzEntity thrownQuartzEntity = new ThrownPolishedQuartzEntity(pLevel, pPlayer, type);
            thrownQuartzEntity.setItem(itemstack);
            thrownQuartzEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), -20.0F, 0.5F, 1.0F);
            pLevel.addFreshEntity(thrownQuartzEntity);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
    
    public enum Type {
        PINK(14725307, 1),
        NETHER(15066582, 2);
        
        private final int color;
        private final int amplifier;
        
        Type(int color, int amplifier) {
            this.color = color;
            this.amplifier = amplifier;
        }
        
        public int getColor() {
            return color;
        }
        
        public int getAmplifier() {
            return amplifier;
        }
    }
}
