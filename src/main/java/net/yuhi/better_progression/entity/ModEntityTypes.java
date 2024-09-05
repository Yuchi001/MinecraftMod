package net.yuhi.better_progression.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.entity.custom.ModBoatEntity;
import net.yuhi.better_progression.entity.custom.ModChestBoatEntity;
import net.yuhi.better_progression.entity.custom.ThrownWeapon;


public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BetterProgression.MOD_ID);

    public static final RegistryObject<EntityType<ThrownWeapon>> THROWN_WEAPON = ENTITY_TYPES.register("thrown_weapon",
            () -> EntityType.Builder.<ThrownWeapon>of(ThrownWeapon::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BetterProgression.MOD_ID, "thrown_weapon").toString()));
    
    public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = ENTITY_TYPES.register("mod_boat",
            () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build("mod_boat"));

    public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = ENTITY_TYPES.register("mod_chest_boat",
            () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build("mod_chest_boat"));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
