package net.yuhi.better_progression.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TIN_ORE_END_PLACED_KEY = registerKey("tin_ore_end_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED_KEY = registerKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_MIDDLE_PLACED_KEY = registerKey("tin_ore_middle_placed");
    public static final ResourceKey<PlacedFeature> STANNIN_ORE_PLACED_KEY = registerKey("stannin_ore_placed");
    public static final ResourceKey<PlacedFeature> STANNIN_ORE_MIDDLE_PLACED_KEY = registerKey("stannin_ore_middle_placed");
    public static final ResourceKey<PlacedFeature> PINK_QUARTZ_ORE_PLACED_KEY = registerKey("pink_quartz_ore_placed");
    public static final ResourceKey<PlacedFeature> DRAGON_DEBRIS_END_SMALL_PLACED_KEY = registerKey("dragon_debris_small_end_placed");
    public static final ResourceKey<PlacedFeature> DRAGON_DEBRIS_END_LARGE_PLACED_KEY = registerKey("dragon_debris_large_end_placed");
    public static final ResourceKey<PlacedFeature> END_STONE_GRASS = registerKey("end_stone_grass");
    public static final ResourceKey<PlacedFeature> END_STONE_GRASS_VEGETATION = registerKey("end_stone_grass_vegetation");
    public static final ResourceKey<PlacedFeature> END_STONE_TALL_GRASS_VEGETATION = registerKey("end_stone_tall_grass_vegetation");
    public static final ResourceKey<PlacedFeature> END_STONE_GRASS_WITH_FLOWERS_VEGETATION = registerKey("end_stone_grass_with_flowers_vegetation");
    public static final ResourceKey<PlacedFeature> END_OAK = registerKey("end_oak");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DRAGON_DEBRIS_END_LARGE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_DRAGON_DEBRIS_LARGE),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(20), VerticalAnchor.absolute(50))));
        register(context, DRAGON_DEBRIS_END_SMALL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_DRAGON_DEBRIS_SMALL),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(60))));
        
        register(context, TIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
        register(context, TIN_ORE_MIDDLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_TIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        register(context, TIN_ORE_END_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_TIN_ORE),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        
        register(context, STANNIN_ORE_MIDDLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STANNIN_ORE_KEY), 
                ModOrePlacement.orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))));
        register(context, STANNIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STANNIN_ORE_KEY),
                ModOrePlacement.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));

        register(context, PINK_QUARTZ_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_PINK_QUARTZ_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));

        register(context, END_STONE_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_STONE_GRASS),
                CountPlacement.of(10),  // Większa liczba, aby pokryć większą powierzchnię
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.matchesBlocks(List.of(Blocks.END_STONE, ModBlocks.END_STONE_GRASS_BLOCK.get()))
                )),
                BiomeFilter.biome());
        
        register(context, END_STONE_GRASS_VEGETATION, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_STONE_GRASS_VEGETATION),
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome());

        register(context, END_STONE_TALL_GRASS_VEGETATION, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_STONE_GRASS_TALL_VEGETATION),
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome());

        register(context, END_STONE_GRASS_WITH_FLOWERS_VEGETATION, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_STONE_GRASS_FLOWERS_VEGETATION),
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome());
        
        register(context, END_OAK, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_OAK),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.05f, 2),
                        ModBlocks.END_OAK_SAPLING.get()));
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
