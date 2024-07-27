package net.yuhi.better_progression.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class TinnedItemRecipeInterface extends Item {
    public TinnedItemRecipeInterface() {
        super(new Item.Properties());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        var component = Component.translatable("tooltip.better_progression.tined_item_interface_tooltip").setStyle(Style.EMPTY.withColor(TextColor.parseColor("#b9a59b"))
                .withBold(true));
        pToolTipComponents.add(component);
        super.appendHoverText(pStack, pLevel, pToolTipComponents, pIsAdvanced);
    }
}