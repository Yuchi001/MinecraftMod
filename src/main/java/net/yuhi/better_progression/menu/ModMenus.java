package net.yuhi.better_progression.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.menu.BetterFurnaceMenu.BetterFurnaceMenu;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterProgression.MOD_ID);
    
    public static final RegistryObject<MenuType<BetterFurnaceMenu>> FURNACE =
            MENU_TYPES.register("furnace_menu", () ->
                    IForgeMenuType.create(BetterFurnaceMenu::new));
    
    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }
}
