package net.yuhi.better_progression.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.BetterProgression;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TIN_ORE = registerKey("add_tin_ore");
    public static final ResourceKey<BiomeModifier> ADD_STANNIN_ORE = registerKey("add_stannin_ore");
    public static final ResourceKey<BiomeModifier> ADD_PINK_QUARTZ_ORE = registerKey("add_pink_quartz_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_TIN_ORE = registerKey("add_end_tin_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_DRAGON_DEBRIS = registerKey("add_end_dragon_debris");
    public static final ResourceKey<BiomeModifier> ADD_END_STONE_GRASS = registerKey("add_end_stone_grass");
    public static final ResourceKey<BiomeModifier> ADD_END_GRASS = registerKey("add_end_grass");
    public static final ResourceKey<BiomeModifier> ADD_END_OAK_FOREST = registerKey("add_end_oak_forest");
    
    
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        
        context.register(ADD_TIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE_MIDDLE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_STANNIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.STANNIN_ORE_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.STANNIN_ORE_MIDDLE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_PINK_QUARTZ_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PINK_QUARTZ_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_END_TIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE_END_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_END_DRAGON_DEBRIS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.DRAGON_DEBRIS_END_LARGE_PLACED_KEY),
                        placedFeatures.getOrThrow(ModPlacedFeatures.DRAGON_DEBRIS_END_SMALL_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_END_STONE_GRASS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_STONE_GRASS)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        ));
        
        context.register(ADD_END_GRASS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_STONE_GRASS_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        
        context.register(ADD_END_OAK_FOREST, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_OAK)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
    
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(BetterProgression.MOD_ID, name));
    }
}
