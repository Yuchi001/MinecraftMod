package net.yuhi.better_progression.model_layer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModModelLayers {
    public static final ModelLayerLocation DAGGER_LAYER = new ModelLayerLocation(new ResourceLocation(BetterProgression.MOD_ID, "dagger"), "main");
}