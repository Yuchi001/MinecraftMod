package net.yuhi.better_progression;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
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
import net.yuhi.better_progression.attribute.ModAttributes;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.block.utils.ModWoodTypes;
import net.yuhi.better_progression.effect.ModEffects;
import net.yuhi.better_progression.entity.ModEntityTypes;
import net.yuhi.better_progression.events.*;
import net.yuhi.better_progression.item.ModCreativeModTabs;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.recipe.ModRecipeType;
import net.yuhi.better_progression.recipe.ModRecipes;
import net.yuhi.better_progression.recipe.RemoveRecipes;
import net.yuhi.better_progression.renderer.ModEntityRenders;
import net.yuhi.better_progression.worldgen.feature.ModFeatures;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixins;

@Mod(BetterProgression.MOD_ID)
public class BetterProgression
{
    public static final String MOD_ID = "better_progression";
    private static final Logger LOGGER = LogUtils.getLogger();
    

    public BetterProgression()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        ModFeatures.register(modEventBus);
        ModAttributes.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModRecipeType.register(modEventBus);

        modEventBus.addListener(ModCreativeModTabs::registerCreativeTab);
        
        modEventBus.addListener(ModCreativeModTabs::onBuildContents);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(HeavyItemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(VillagerTradesEventHandler.class);
        MinecraftForge.EVENT_BUS.register(BlockInteractionHandler.class);
        MinecraftForge.EVENT_BUS.register(RemoveRecipes.class);
        MinecraftForge.EVENT_BUS.register(ModEntityRenders.class);
        MinecraftForge.EVENT_BUS.register(ArmorChangeEventHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerTickHandler.class);
        MinecraftForge.EVENT_BUS.register(HungerBarRenderHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerSleepHandler.class);
        MinecraftForge.EVENT_BUS.register(AnvilRepairHandler.class);
        MinecraftForge.EVENT_BUS.register(TotemUseHandler.class);
        
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Mixins.addConfiguration("better_progression.mixins.json");
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
        @SuppressWarnings("removal")
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            Sheets.addWoodType(ModWoodTypes.END_OAK);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRAKE_RAIL.get(), RenderType.cutout());
            for (var blockData : ModBlocks.BLOCKS_DATA) {
                switch (blockData.textureType) {
                    case CROSS, LEAVES -> ItemBlockRenderTypes.setRenderLayer(blockData.block.get(), RenderType.cutout());
                }
            }

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TALL_END_GRASS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.FOOLS_PEARL_BUSH.get(), RenderType.cutout());
        }
    }
}
