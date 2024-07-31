package net.yuhi.better_progression;

import com.mojang.logging.LogUtils;
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
import net.yuhi.better_progression.entity.ModEntityTypes;
import net.yuhi.better_progression.events.HeavyItemEventHandler;
import net.yuhi.better_progression.item.ModCreativeModTabs;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.model_layer.ModModelLayers;
import net.yuhi.better_progression.recipe.ModRecipeType;
import net.yuhi.better_progression.recipe.ModRecipes;
import net.yuhi.better_progression.recipe.RemoveRecipes;
import net.yuhi.better_progression.renderer.ModEntityRenders;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixins;

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

        modEventBus.addListener(this::commonSetup);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        ModEntityTypes.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModRecipeType.register(modEventBus);

        modEventBus.addListener(ModCreativeModTabs::registerCreativeTab);
        
        modEventBus.addListener(ModCreativeModTabs::onBuildContents);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ModModelLayers.class);
        MinecraftForge.EVENT_BUS.register(HeavyItemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(RemoveRecipes.class);
        MinecraftForge.EVENT_BUS.register(ModEntityRenders.class);
        
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Mixins.addConfiguration("mixin/better_progression.mixins.json");
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
