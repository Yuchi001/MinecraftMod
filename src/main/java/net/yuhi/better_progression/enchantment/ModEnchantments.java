package net.yuhi.better_progression.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.enchantment.custom.HammerEnchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BetterProgression.MOD_ID);

    public static final RegistryObject<HammerEnchantment> HAMMER =
            ENCHANTMENTS.register("hammer", () -> new HammerEnchantment(
                    Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    
    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
