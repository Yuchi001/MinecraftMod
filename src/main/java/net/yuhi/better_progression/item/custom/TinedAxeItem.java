package net.yuhi.better_progression.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class TinedAxeItem extends AxeItem implements TinedItem {
    public TinedAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        var components = TinedItem.getTinedDescription(pStack);
        if (components != null) pToolTipComponents.addAll(components);
        super.appendHoverText(pStack, pLevel, pToolTipComponents, pIsAdvanced);
    }
}
