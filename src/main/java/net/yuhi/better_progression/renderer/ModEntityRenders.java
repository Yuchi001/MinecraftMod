package net.yuhi.better_progression.renderer;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SpawnerRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.entity.EssenceSpawnerBlockEntity;
import net.yuhi.better_progression.entity.ModEntityTypes;
import net.yuhi.better_progression.entity.ModModelLayers;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenders {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.END_OAK_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.END_OAK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
    }
    
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.THROWN_WEAPON.get(), ThrownWeaponRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.POLISHED_PINK_QUARTZ.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MOD_BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
        event.registerEntityRenderer(ModEntityTypes.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ESSENCE_SPAWNER.get(), EssenceSpawnerRenderer::new);
    }
}
 