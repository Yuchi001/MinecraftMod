package net.yuhi.better_progression.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.menu.custom.*;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterProgression.MOD_ID);
    public static final DeferredRegister<MenuType<?>> VANILLA_MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "minecraft");

    public static final RegistryObject<MenuType<BetterBlastFurnaceMenu>> BLAST_FURNACE =
            VANILLA_MENU_TYPES.register("blast_furnace", () ->
                    IForgeMenuType.create(BetterBlastFurnaceMenu::new));

    public static final RegistryObject<MenuType<BetterEnchantmentMenu>> ENCHANTMENT =
            VANILLA_MENU_TYPES.register("enchantment", () ->
                    IForgeMenuType.create(BetterEnchantmentMenu::new));

    public static final RegistryObject<MenuType<BetterAnvilMenu>> ANVIL =
            VANILLA_MENU_TYPES.register("anvil", () ->
                    IForgeMenuType.create(BetterAnvilMenu::new));

    public static final RegistryObject<MenuType<EssenceSpawnerMenu>> ESSENCE_SPAWNER =
            MENU_TYPES.register("essence_spawner", () ->
                    IForgeMenuType.create(EssenceSpawnerMenu::new));


    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
        VANILLA_MENU_TYPES.register(bus);
    }
}
