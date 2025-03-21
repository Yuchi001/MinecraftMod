package net.yuhi.better_progression.client.handler;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.client.screen.*;
import net.yuhi.better_progression.events.HeavyItemEventHandler;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.recipe.RemoveRecipes;
import net.yuhi.better_progression.renderer.ModEntityRenders;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.BLAST_FURNACE.get(), BetterFurnaceMenuScreen::new);
            MenuScreens.register(ModMenus.ESSENCE_SPAWNER.get(), EssenceSpawnerMenuScreen::new);
            MenuScreens.register(ModMenus.ALCHEMY_TABLE.get(), AlchemyTableMenuScreen::new);
            MenuScreens.register(ModMenus.ENCHANTMENT.get(), BetterEnchantingScreen::new);
            MenuScreens.register(ModMenus.ANVIL.get(), BetterAnvilScreen::new);
            MinecraftForge.EVENT_BUS.register(ModEntityRenders.class);
            MinecraftForge.EVENT_BUS.register(HeavyItemEventHandler.class);
        });
    }
}
