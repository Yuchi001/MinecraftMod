package net.yuhi.better_progression.model_layer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.renderer.DaggerModel;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
    public static final ModelLayerLocation DAGGER_LAYER = new ModelLayerLocation(new ResourceLocation(BetterProgression.MOD_ID, "dagger"), "main");

    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DAGGER_LAYER, DaggerModel::createLayer);
    }

    public static LayerDefinition createDaggerLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        // Define the model parts here
        partDefinition.addOrReplaceChild("dagger", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}