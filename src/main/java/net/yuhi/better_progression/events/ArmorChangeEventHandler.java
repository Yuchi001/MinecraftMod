package net.yuhi.better_progression.events;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;

import java.util.Map;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorChangeEventHandler {
    
    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            EquipmentSlot slot = event.getSlot();
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                if (event.getTo().isEmpty()) {
                    double currentHealth = player.getHealth();
                    
                    player.setHealth(player.getHealth());
                    
                    int additionalHealth = 0;
                    for (var s : player.getArmorSlots()) {
                        if(s.isEmpty()) continue;
                        if(!(s.getItem() instanceof ArmorItem armorItem)) continue;
                        if(!(armorItem.getMaterial() instanceof BetterArmorMaterial material)) continue;
                        additionalHealth += material.getLifeMod(armorItem.getType());
                    }
                    
                    if (currentHealth > 6 + additionalHealth ) {
                        player.setHealth(6 + additionalHealth);
                    }
                }
            }
        }
    }
}