package net.yuhi.better_progression;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.events.HeavyItemEventHandler;
import net.yuhi.better_progression.item.ModCreativeModTabs;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.recipe.ModRecipeType;
import net.yuhi.better_progression.recipe.ModRecipes;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterProgression.MOD_ID)
public class BetterProgression
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "better_progression";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public BetterProgression()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModRecipeType.register(modEventBus);


        modEventBus.addListener(ModCreativeModTabs::registerCreativeTab);
        
        modEventBus.addListener(ModCreativeModTabs::onBuildContents);
        
        modEventBus.addListener(this::commonSetup);
        
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(HeavyItemEventHandler.class);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            
        }
    }
}
