package net.yuhi.better_progression.datagen;

import com.google.gson.JsonElement;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModBlockModelGenerators extends BlockModelGenerators {
    private final Consumer<BlockStateGenerator> blockStateOutput;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
    public ModBlockModelGenerators(Consumer<BlockStateGenerator> pBlockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput, Consumer<Item> pSkippedAutoModelsOutput) {
        super(pBlockStateOutput, pModelOutput, pSkippedAutoModelsOutput);
        blockStateOutput = pBlockStateOutput;
        modelOutput = pModelOutput;
    }

    private void createSimpleFlatItemModel(Block pFlatBlock) {
        Item item = pFlatBlock.asItem();
        if (item != Items.AIR) {
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(pFlatBlock), this.modelOutput);
        }
    }

    private void createPassiveRail(Block pRailBlock) {
        TextureMapping texturemapping = TextureMapping.rail(pRailBlock);
        TextureMapping texturemapping1 = TextureMapping.rail(TextureMapping.getBlockTexture(pRailBlock, "_corner"));
        ResourceLocation resourcelocation = ModelTemplates.RAIL_FLAT.create(pRailBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.RAIL_CURVED.create(pRailBlock, texturemapping1, this.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.RAIL_RAISED_NE.create(pRailBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation3 = ModelTemplates.RAIL_RAISED_SW.create(pRailBlock, texturemapping, this.modelOutput);
        this.createSimpleFlatItemModel(pRailBlock);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(pRailBlock).with(PropertyDispatch.property(BlockStateProperties.RAIL_SHAPE).select(RailShape.NORTH_SOUTH, Variant.variant().with(VariantProperties.MODEL, resourcelocation)).select(RailShape.EAST_WEST, Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(RailShape.ASCENDING_EAST, Variant.variant().with(VariantProperties.MODEL, resourcelocation2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(RailShape.ASCENDING_WEST, Variant.variant().with(VariantProperties.MODEL, resourcelocation3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(RailShape.ASCENDING_NORTH, Variant.variant().with(VariantProperties.MODEL, resourcelocation2)).select(RailShape.ASCENDING_SOUTH, Variant.variant().with(VariantProperties.MODEL, resourcelocation3)).select(RailShape.SOUTH_EAST, Variant.variant().with(VariantProperties.MODEL, resourcelocation1)).select(RailShape.SOUTH_WEST, Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(RailShape.NORTH_WEST, Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(RailShape.NORTH_EAST, Variant.variant().with(VariantProperties.MODEL, resourcelocation1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }
}
