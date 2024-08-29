package net.yuhi.better_progression.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.worldgen.feature.ModFeatures;

import java.util.List;
import java.util.OptionalInt;


public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_TIN_ORE_KEY = registerKey("tin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_STANNIN_ORE_KEY = registerKey("stannin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_PINK_QUARTZ_ORE_KEY = registerKey("pink_quartz_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_DRAGON_DEBRIS_SMALL = registerKey("dragon_debris_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_DRAGON_DEBRIS_LARGE = registerKey("dragon_debris_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_TIN_ORE = registerKey("end_tin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_STONE_GRASS = registerKey("end_stone_grass"); 
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_STONE_GRASS_VEGETATION = registerKey("end_stone_grass_vegetation"); 
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_STONE_GRASS_FLOWERS_VEGETATION = registerKey("end_stone_grass_flowers_vegetation"); 
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_STONE_GRASS_TALL_VEGETATION = registerKey("end_stone_grass_tall_vegetation"); 
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_OAK = registerKey("end_oak");

    private static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.END_OAK_LOG.get()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ModBlocks.END_OAK_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest endStoneReplecables = new BlockMatchTest(Blocks.END_STONE);

        // to jest lista bo tutaj jeszcze deepslate moze byc zastapiony
        List<OreConfiguration.TargetBlockState> overworldTinOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.TIN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState())
                );

        List<OreConfiguration.TargetBlockState> overworldStanninOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.STANNIN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_STANNIN_ORE.get().defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> overworldPinkQuartzOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.PINK_QUARTZ_ORE.get().defaultBlockState())
        );
        
        List<OreConfiguration.TargetBlockState> endTinOres = List.of(
                OreConfiguration.target(endStoneReplecables, ModBlocks.END_TIN_ORE.get().defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> endDragonDebrisOres = List.of(
                OreConfiguration.target(endStoneReplecables, ModBlocks.DRAGON_DEBRIS.get().defaultBlockState())
        );

        register(context, END_STONE_GRASS_VEGETATION, Feature.RANDOM_PATCH, new RandomPatchConfiguration(64, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.END_GRASS.get())))));
        register(context, END_STONE_GRASS_FLOWERS_VEGETATION, Feature.RANDOM_PATCH, new RandomPatchConfiguration(16, 3, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.END_GRASS_WITH_FLOWERS.get())))));
        register(context, END_STONE_GRASS_TALL_VEGETATION, Feature.RANDOM_PATCH, new RandomPatchConfiguration(8, 3, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.TALL_END_GRASS.get())))));
        
        register(context, END_STONE_GRASS, ModFeatures.END_STONE_GRASS_REPLACER, FeatureConfiguration.NONE);
        register(context, END_OAK, Feature.TREE, createFancyOak().build());
        register(context, OVERWORLD_TIN_ORE_KEY, Feature.ORE, new OreConfiguration(overworldTinOres, 9));
        register(context, OVERWORLD_STANNIN_ORE_KEY, Feature.ORE, new OreConfiguration(overworldStanninOres, 9));
        register(context, OVERWORLD_PINK_QUARTZ_ORE_KEY, Feature.ORE, new OreConfiguration(overworldPinkQuartzOres, 14));
        register(context, END_TIN_ORE, Feature.ORE, new OreConfiguration(endTinOres, 14));
        register(context, END_DRAGON_DEBRIS_SMALL, Feature.SCATTERED_ORE, new OreConfiguration(endDragonDebrisOres, 2, 1.0f));
        register(context, END_DRAGON_DEBRIS_LARGE, Feature.SCATTERED_ORE, new OreConfiguration(endDragonDebrisOres, 3, 1.0f));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BetterProgression.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
