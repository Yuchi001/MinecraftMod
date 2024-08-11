package net.yuhi.better_progression.mixin.accessor;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.EnumMap;
import java.util.UUID;

@Mixin(ArmorItem.class)
public interface ArmorItemAccessor {

    @Accessor("defaultModifiers")
    Multimap<Attribute, AttributeModifier> getDefaultModifiers();

    @Accessor("type")
    ArmorItem.Type getType();
    
    @Accessor("ARMOR_MODIFIER_UUID_PER_TYPE")
    EnumMap<ArmorItem.Type, UUID> getArmorModifierUUID();
}