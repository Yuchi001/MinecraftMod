package net.yuhi.better_progression.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.yuhi.better_progression.BetterProgression;

import java.util.List;

import static net.yuhi.better_progression.worldgen.ModOrePlacement.orePlacement;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TIN_ORE_END_PLACED_KEY = registerKey("tin_ore_end_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED_KEY = registerKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_MIDDLE_PLACED_KEY = registerKey("tin_ore_middle_placed");
    public static final ResourceKey<PlacedFeature> STANNIN_ORE_PLACED_KEY = registerKey("stannin_ore_placed");
    public static final ResourceKey<PlacedFeature> STANNIN_ORE_MIDDLE_PLACED_KEY = registerKey("stannin_ore_middle_placed");
    public static final ResourceKey<PlacedFeature> PINK_QUARTZ_ORE_PLACED_KEY = registerKey("pink_quartz_ore_placed");
    public static final ResourceKey<PlacedFeature> DRAGON_DEBRIS_END_SMALL_PLACED_KEY = registerKey("dragon_debris_small_end_placed");
    public static final ResourceKey<PlacedFeature> DRAGON_DEBRIS_END_LARGE_PLACED_KEY = registerKey("dragon_debris_large_end_placed");
    
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DRAGON_DEBRIS_END_LARGE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_DRAGON_DEBRIS_LARGE), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24)), BiomeFilter.biome());
        register(context, DRAGON_DEBRIS_END_SMALL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_DRAGON_DEBRIS_SMALL), InSquarePlacement.spread(), PlacementUtils.RANGE_8_8, BiomeFilter.biome());
        
        register(context, TIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
        register(context, TIN_ORE_MIDDLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        register(context, TIN_ORE_END_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_TIN_ORE),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        
        register(context, STANNIN_ORE_MIDDLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STANNIN_ORE_KEY), 
                ModOrePlacement.orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))));
        register(context, STANNIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STANNIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));

        register(context, PINK_QUARTZ_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_PINK_QUARTZ_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
    }
    
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BetterProgression.MOD_ID, name));
    }
    
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static void register(BootstapContext<PlacedFeature> pContext, ResourceKey<PlacedFeature> pKey, Holder<ConfiguredFeature<?, ?>> pConfiguredFeatures, PlacementModifier... pPlacements) {
        register(pContext, pKey, pConfiguredFeatures, List.of(pPlacements));
    }
}
