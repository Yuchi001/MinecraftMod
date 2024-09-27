package net.yuhi.better_progression.mixin.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.LinkedList;
import java.util.List;

@Mixin(TridentItem.class)
public class TridentItemMixin implements IForgeItem {
    @Unique
    private static final List<Enchantment> UNIQUE_ENCHANTMENTS = List.of(Enchantments.SHARPNESS,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.MOB_LOOTING,
            Enchantments.SMITE);

    @Unique
    private static final List<Enchantment> DEFAULT_ENCHANTMENTS = List.of(Enchantments.RIPTIDE,
            Enchantments.DEPTH_STRIDER,
            Enchantments.MENDING,
            Enchantments.UNBREAKING,
            Enchantments.CHANNELING,
            Enchantments.LOYALTY
    );

    @Unique
    private static List<Enchantment> betterProgression$getPossibleEnchantments(boolean book) {
        var enchantments = new LinkedList<Enchantment>(UNIQUE_ENCHANTMENTS);
        if (book) enchantments.addAll(DEFAULT_ENCHANTMENTS);
        return enchantments;
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return betterProgression$getPossibleEnchantments(false).contains(enchantment) || enchantment.category.canEnchant(stack.getItem());
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        for (var set : EnchantmentHelper.getEnchantments(book).entrySet()) {
            if (betterProgression$getPossibleEnchantments(true).contains(set.getKey())) return true;
        }
        return false;
    }
}
