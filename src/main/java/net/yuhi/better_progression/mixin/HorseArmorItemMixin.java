package net.yuhi.better_progression.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.extensions.IForgeItem;
import net.yuhi.better_progression.mixin.accessor.ItemAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseArmorItem.class)
public class HorseArmorItemMixin implements IForgeItem {

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
        return enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.PROJECTILE_PROTECTION ||
                enchantment == Enchantments.BLAST_PROTECTION || enchantment == Enchantments.FIRE_PROTECTION ||
                enchantment == Enchantments.ALL_DAMAGE_PROTECTION;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }
}
