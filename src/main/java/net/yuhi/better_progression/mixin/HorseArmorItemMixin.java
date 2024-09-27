package net.yuhi.better_progression.mixin;

import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(HorseArmorItem.class)
public class HorseArmorItemMixin implements IForgeItem {
    @Unique
    private static List<Enchantment> POSSIBLE_ENCHANTMENTS = List.of(Enchantments.UNBREAKING,
            Enchantments.PROJECTILE_PROTECTION,
            Enchantments.BLAST_PROTECTION,
            Enchantments.MENDING,
            Enchantments.THORNS,
            Enchantments.FIRE_PROTECTION,
            Enchantments.ALL_DAMAGE_PROTECTION);

    @ModifyArg(
            method = "<init>*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/HorseArmorItem;<init>(ILnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/Item$Properties;)V"
            ),
            index = 2
    )
    private static Item.Properties modifyProperties(Item.Properties pProperties) {
        return pProperties.durability(200);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return POSSIBLE_ENCHANTMENTS.contains(enchantment);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        for (var set : EnchantmentHelper.getEnchantments(book).entrySet()) {
            if (POSSIBLE_ENCHANTMENTS.contains(set.getKey())) return true;
        }
        return false;
    }
}
