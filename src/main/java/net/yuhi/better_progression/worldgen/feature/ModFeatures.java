package net.yuhi.better_progression.worldgen.feature;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.BetterProgression;

public class ModFeatures {
    public static final Feature<NoneFeatureConfiguration> END_STONE_GRASS_REPLACER =
            new EndStoneGrassReplacerFeature(NoneFeatureConfiguration.CODEC);

    public static void register(IEventBus eventBus) {
        //eventBus.register(END_STONE_GRASS_REPLACER);
        ForgeRegistries.FEATURES.register(new ResourceLocation(BetterProgression.MOD_ID, "end_stone_grass_replacer"), END_STONE_GRASS_REPLACER);
    }
}
