package net.yuhi.better_progression.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class HorseMixin {
        
    @Inject(method = "hurtArmor", at = @At("HEAD"))
    public void hurtArmor(DamageSource source, float amount, CallbackInfo ci) {
        if (!((Object)this instanceof Horse horse)) return;

        if (amount <= 0.0F) return;

        if (horse.getArmor().isEmpty()) return;
        
        var armorName = ((HorseArmorItem)horse.getArmor().getItem()).getTexture().getPath();
        var materialName = armorName.substring(armorName.lastIndexOf('_') + 1, armorName.lastIndexOf('.'));
        var isUpgrade = materialName.contains("nether") || materialName.contains("end");

        amount *= switch (materialName) {
            case "iron" -> 4;
            case "copper" -> 5;
            case "bronze" -> isUpgrade ? 2 : 3;
            case "steel" -> isUpgrade ? 1 : 2;
            default -> 1;
        };
        
        ItemStack itemstack = horse.getArmor();
        if ((!source.is(DamageTypeTags.IS_FIRE) || !itemstack.getItem().isFireResistant()) && itemstack.getItem() instanceof HorseArmorItem) {
            itemstack.hurtAndBreak((int)amount, horse, (p_35997_) -> {
                p_35997_.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, 2));
            });
        }
    }
}
