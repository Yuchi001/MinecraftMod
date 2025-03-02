package net.yuhi.better_progression.events;

import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import org.jetbrains.annotations.Debug;

import java.io.Console;
import java.util.Map;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorChangeEventHandler {

    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();

        // Działa tylko na serwerze
        if (!(entity instanceof ServerPlayer player)) return;

        EquipmentSlot slot = event.getSlot();
        if (slot.getType() == EquipmentSlot.Type.ARMOR) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth == null) return;

            // Liczymy bonusowe zdrowie z pancerza
            int additionalHealth = 0;
            for (var s : player.getArmorSlots()) {
                if (s.isEmpty()) continue;
                if (!(s.getItem() instanceof ArmorItem armorItem)) continue;
                if (!(armorItem.getMaterial() instanceof BetterArmorMaterial material)) continue;
                additionalHealth += material.getLifeMod(armorItem.getType());
            }

            System.out.write(6 + additionalHealth);

            // Aktualizacja maksymalnego zdrowia
            maxHealth.setBaseValue(6);

            // Ustawienie nowego aktualnego zdrowia (jeśli było większe niż nowy limit)
            if (player.getHealth() > maxHealth.getBaseValue()) {
                player.setHealth((float) maxHealth.getBaseValue());
            }
        }
    }
}
