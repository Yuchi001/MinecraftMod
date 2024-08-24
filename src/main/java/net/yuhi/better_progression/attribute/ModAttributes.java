package net.yuhi.better_progression.attribute;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

import java.util.UUID;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, BetterProgression.MOD_ID);

    public static final UUID ATTACK_REACH_UUID = UUID.fromString("845DB27C-1234-495F-8C9F-6020A9A58B6B");
    public static String ATTACK_REACH_KEY = "attack_reach";
    public static final RegistryObject<Attribute> ATTACK_REACH = ATTRIBUTES.register(ATTACK_REACH_KEY,  () -> (Attribute) new RangedAttribute("attribute.better_progression.attack_reach", 0.0D, 0.0D, 10.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
