package net.yuhi.better_progression.mixin.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import net.yuhi.better_progression.mixin.accessor.ArmorItemAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {

    @Inject(method = "getDefaultAttributeModifiers", at = @At("HEAD"), cancellable = true)
    public void getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        try {
            ArmorItem armorItem = (ArmorItem) (Object) this;
            ArmorItemAccessor accessor = (ArmorItemAccessor) armorItem;
            if(slot != accessor.getType().getSlot()) {
                cir.setReturnValue(ImmutableMultimap.of());
                return;
            }

            ArmorMaterial material = armorItem.getMaterial();

            Multimap<Attribute, AttributeModifier> existingModifiers = accessor.getDefaultModifiers();
            Multimap<Attribute, AttributeModifier> modifiedModifiers = HashMultimap.create();

            int lifeMod = ((BetterArmorMaterial) material).getLifeMod(armorItem.getType());
            if (lifeMod > 0 && slot == armorItem.getType().getSlot()) {
                UUID uuid = accessor.getArmorModifierUUID().get(armorItem.getType());
                AttributeModifier lifeModifier = new AttributeModifier(uuid, "Life Modifier", lifeMod, AttributeModifier.Operation.ADDITION);
                modifiedModifiers.put(Attributes.MAX_HEALTH, lifeModifier);
            }

            if (slot == armorItem.getType().getSlot()) {
                modifiedModifiers.putAll(existingModifiers);
            }

            cir.setReturnValue(modifiedModifiers);
        }
        catch(Exception ignored) {
            
        }
    }
}
