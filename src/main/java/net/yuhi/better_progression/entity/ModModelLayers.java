package net.yuhi.better_progression.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.yuhi.better_progression.BetterProgression;

public class ModModelLayers {
    public static final ModelLayerLocation END_OAK_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(BetterProgression.MOD_ID, "boat/end_oak"), "main");

    public static final ModelLayerLocation END_OAK_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(BetterProgression.MOD_ID, "chest_boat/end_oak"), "main");
}
