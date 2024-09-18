package net.yuhi.better_progression.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.custom.BetterBlastFurnaceBlock;
import net.yuhi.better_progression.block.blockdata.*;
import net.yuhi.better_progression.block.custom.*;
import net.yuhi.better_progression.block.grower.EndOakGrower;
import net.yuhi.better_progression.block.utils.ModWoodTypes;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.mixin.accessor.BlockAccessor;
import net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType;
import net.yuhi.better_progression.recipe.utils.ESmeltingRecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.Blocks.*;
import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;
import static net.yuhi.better_progression.mixin.accessor.BlockAccessor.*;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterProgression.MOD_ID);
    
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    
    public static final List<BlockDataCreator.BlockData> BLOCKS_DATA = new ArrayList<>();

    public static final RegistryObject<Block> ESSENCE_SPAWNER = new BlockDataCreator("essence_spawner",
            () -> new EssenceSpawnerBlock(BlockBehaviour.Properties.copy(SPAWNER).strength(4.0F, 6.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetTextureType(ETextureType.PILLAR_TOP_BOTTOM)
            .SetTexture(EBlockSide.TOP, "essence_spawner_top")
            .SetTexture(EBlockSide.SIDE, "essence_spawner")
            .SetTexture(EBlockSide.BOTTOM, "essence_spawner")
            .AddRecipe(EBlockCraftingRecipeType.ESSENCE_SPAWNER_1, List.of(() -> Items.STICK))
            .Register();
    
    public static final RegistryObject<Block> TIN_BLOCK = new BlockDataCreator("tin_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(4.0F, 6.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetSpecialTag(ECustomTag.ORE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_SHAPELESS_1, List.of(() -> getItem(EItemCategory.Ingot, EMaterialType.TIN)))
            .Register();

    public static final RegistryObject<Block> STEEL_BLOCK = new BlockDataCreator("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6.0F, 6.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_SHAPELESS_1, List.of(() -> getItem(EItemCategory.Ingot, EMaterialType.STEEL)))
            .Register();

    public static final RegistryObject<Block> BRONZE_BLOCK = new BlockDataCreator("bronze_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).strength(6.0F, 6.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_SHAPELESS_1, List.of(() -> getItem(EItemCategory.Ingot, EMaterialType.BRONZE)))
            .Register();

    // region END 

    public static final RegistryObject<Block> ENDERITE_BLOCK = new BlockDataCreator("enderite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(50.0F, 1200.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_SHAPELESS_1, List.of(() -> getItem(EItemCategory.Ingot, EMaterialType.ENDERITE)))
            .Register();

    public static final RegistryObject<Block> END_STONE_GRASS_BLOCK = new BlockDataCreator("end_stone_grass_block",
            () -> new EndStoneGrassBlock(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(1.0F, 3.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetTextureType(ETextureType.PILLAR_TOP_BOTTOM)
            .SetTexture(EBlockSide.BOTTOM, "minecraft", "end_stone")
            .SetTexture(EBlockSide.TOP, "end_stone_grass_block_top")
            .SetTexture(EBlockSide.SIDE, "end_stone_grass_block_side")
            .Register();

    public static final RegistryObject<Block> CHISELED_END_STONE_BRICKS = new BlockDataCreator("chiseled_end_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(3.0F, 9.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetTextureType(ETextureType.PILLAR_TOP)
            .SetTexture(EBlockSide.TOP, "minecraft", "end_stone_bricks")
            .SetTexture(EBlockSide.SIDE, "chiseled_end_stone_bricks")
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_FROM_2_1, List.of(() -> END_STONE_BRICK_SLAB))
            .Register();

    public static final RegistryObject<Block> END_TIN_ORE = new BlockDataCreator("end_tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .DontDropSelf()
            .SetSpecialTag(ECustomTag.ORE)
            .SetRequireTier(ERequireTier.DIAMOND)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_RARE, () -> getItem(EItemCategory.Ingot, EMaterialType.TIN))
            .Register();

    public static final RegistryObject<Block> TALL_END_GRASS = new BlockDataCreator("end_tall_grass",
            () -> new EndDoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)))
            .SetTextureType(ETextureType.CUSTOM)
            .DontDropSelf()
            .SetSpecialTag(ECustomTag.NO_DROP)
            .SetTexture(EBlockSide.ITEM, "end_tall_grass_top")
            .SetTexture(EBlockSide.TOP, "end_tall_grass_top")
            .SetTexture(EBlockSide.BOTTOM, "end_tall_grass_bottom")
            .Register();

    public static final RegistryObject<Block> END_GRASS = new BlockDataCreator("end_short_grass",
            () -> new TallEndGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ), true))
            .SetTextureType(ETextureType.CROSS)
            .DontDropSelf()
            .SetSpecialTag(ECustomTag.NO_DROP)
            .SetTexture(EBlockSide.ITEM, "end_short_grass")
            .Register();

    public static final RegistryObject<Block> END_WHITE_TULIP = new BlockDataCreator("end_white_tulip",
            () -> new EndFlowerBlock(() -> MobEffects.POISON, 9, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)))
            .SetTextureType(ETextureType.CROSS)
            .SetTexture(EBlockSide.ITEM, "end_white_tulip")
            .AddItemTag(ItemTags.SMALL_FLOWERS)
            .AddBlockTag(BlockTags.SMALL_FLOWERS)
            .Register();

    public static final RegistryObject<Block> END_BLACK_TULIP = new BlockDataCreator("end_black_tulip",
            () -> new EndFlowerBlock(() -> MobEffects.DAMAGE_RESISTANCE, 9, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)))
            .SetTextureType(ETextureType.CROSS)
            .SetTexture(EBlockSide.ITEM, "end_black_tulip")
            .AddItemTag(ItemTags.SMALL_FLOWERS)
            .AddBlockTag(BlockTags.SMALL_FLOWERS)
            .Register();

    public static final RegistryObject<Block> FOOLS_PEARL_BUSH = new BlockDataCreator("fools_pearl_bush",
            () -> new TallEndFlowerBlock((BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ))))
            .SetTextureType(ETextureType.CUSTOM)
            .DontDropSelf()
            .SetSpecialTag(ECustomTag.NO_DROP)
            .SetTexture(EBlockSide.ITEM, "fools_pearl_bush_top")
            .SetTexture(EBlockSide.TOP, "fools_pearl_bush_top")
            .SetTexture(EBlockSide.BOTTOM, "fools_pearl_bush_bottom")
            .Register();

    public static final RegistryObject<Block> END_GRASS_WITH_FLOWERS = new BlockDataCreator("end_short_grass_with_flowers",
            () -> new TallEndGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ), false))
            .SetTextureType(ETextureType.CROSS)
            .DontDropSelf()
            .SetSpecialTag(ECustomTag.NO_DROP)
            .SetTexture(EBlockSide.ITEM, "end_short_grass_with_flowers")
            .Register();

    public static final RegistryObject<Block> END_OAK_SAPLING = new BlockDataCreator("end_oak_sapling",
            () -> new EndSaplingBlock(new EndOakGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)))
            .SetTextureType(ETextureType.CROSS)
            .SetTexture(EBlockSide.ITEM, "end_oak_sapling")
            .Register();

    public static final RegistryObject<Block> STRIPPED_END_OAK_LOG = new BlockDataCreator("stripped_end_oak_log",
            () -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)))
            .SetMineableWith(EMineableWith.AXE)
            .SetTextureType(ETextureType.LOG)
            .SetSpecialTag(ECustomTag.LOGS_THAT_BURN)
            .SetTexture(EBlockSide.TOP, "stripped_end_oak_log_top")
            .Register();

    public static final RegistryObject<Block> END_OAK_LOG = new BlockDataCreator("end_oak_log",
            () -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)))
            .SetMineableWith(EMineableWith.AXE)
            .SetLogProperties(STRIPPED_END_OAK_LOG::get)
            .SetTextureType(ETextureType.LOG)
            .SetSpecialTag(ECustomTag.LOGS_THAT_BURN)
            .SetTexture(EBlockSide.TOP, "end_oak_log_top")
            .Register();

    public static final RegistryObject<Block> STRIPPED_END_OAK_WOOD = new BlockDataCreator("stripped_end_oak_wood",
            () -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)))
            .SetTextureType(ETextureType.AXIS)
            .SetTexture(EBlockSide.TOP, "stripped_end_oak_log")
            .SetTexture(EBlockSide.SIDE, "stripped_end_oak_log")
            .SetSpecialTag(ECustomTag.LOGS_THAT_BURN)
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.WOOD_3, List.of(STRIPPED_END_OAK_LOG::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_WOOD = new BlockDataCreator("end_oak_wood",
            () -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)))
            .SetTextureType(ETextureType.AXIS)
            .SetLogProperties(STRIPPED_END_OAK_WOOD::get)
            .SetTexture(EBlockSide.TOP, "end_oak_log")
            .SetTexture(EBlockSide.SIDE, "end_oak_log")
            .SetMineableWith(EMineableWith.AXE)
            .SetSpecialTag(ECustomTag.LOGS_THAT_BURN)
            .AddRecipe(EBlockCraftingRecipeType.WOOD_3, List.of(END_OAK_LOG::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_PLANKS = new BlockDataCreator("end_oak_planks",
            () -> new Block(BlockBehaviour.Properties.copy(OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
            })
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.PLANKS_4, List.of(END_OAK_LOG::get))
            .AddRecipe(EBlockCraftingRecipeType.PLANKS_4, List.of(STRIPPED_END_OAK_LOG::get))
            .AddRecipe(EBlockCraftingRecipeType.PLANKS_4, List.of(END_OAK_WOOD::get))
            .AddRecipe(EBlockCraftingRecipeType.PLANKS_4, List.of(STRIPPED_END_OAK_WOOD::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_LEAVES = new BlockDataCreator("end_oak_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(BlockAccessor::ocelotOrParrot).isSuffocating(BlockAccessor::never).isViewBlocking(BlockAccessor::never)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }
        
                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
        
                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }
            })
            .DontDropSelf()
            .AddRecipe(EBlockCraftingRecipeType.STICK_4, List.of(END_OAK_PLANKS::get))
            .SetSpecialTag(ECustomTag.LEAVES)
            .Register();
    
    public static final RegistryObject<Block> END_OAK_FENCE = new BlockDataCreator("end_oak_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)))
            .SetTextureType(ETextureType.FENCE)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .SetSpecialTag(ECustomTag.WOODEN_FENCES)
            .AddRecipe(EBlockCraftingRecipeType.WOODEN_FENCE_3, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_WALL_SIGN = new BlockDataCreator("end_oak_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(OAK_WALL_SIGN), ModWoodTypes.END_OAK))
            .SetTextureType(ETextureType.CUSTOM)
            .SetMineableWith(EMineableWith.AXE)
            .RegisterWithoutItem();

    public static final RegistryObject<Block> END_OAK_SIGN = new BlockDataCreator("end_oak_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(OAK_SIGN), ModWoodTypes.END_OAK))
            .SetTextureType(ETextureType.SIGN)
            .SetTexture(EBlockSide.SIDE, "end_oak_planks")
            .SetTexture(EBlockSide.ITEM, "end_oak_sign")
            .SetTwinBlockSupplier(END_OAK_WALL_SIGN::get)
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.SIGN_3, List.of(END_OAK_PLANKS::get))
            .RegisterWithoutItem();

    public static final RegistryObject<Block> END_OAK_FENCE_GATE = new BlockDataCreator("end_oak_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.END_OAK))
            .SetTextureType(ETextureType.FENCE_GATE)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .SetSpecialTag(ECustomTag.FENCE_GATES)
            .AddRecipe(EBlockCraftingRecipeType.WOODEN_FENCE_GATE_1, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_SLAB = new BlockDataCreator("end_oak_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(OAK_SLAB)))
            .SetTextureType(ETextureType.SLAB)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.SLAB_6, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_STAIRS = new BlockDataCreator("end_oak_stairs",
            () -> new StairBlock(END_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(OAK_STAIRS)))
            .SetTextureType(ETextureType.STAIRS)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.STAIRS_4, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_PRESSURE_PLATE = new BlockDataCreator("end_oak_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, OAK_PLANKS.defaultMaterialColor()).noCollission().strength(0.5F), BlockSetType.OAK))
            .SetTextureType(ETextureType.PRESSURE_PLATE)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.PRESSURE_PLATE_1, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_BUTTON = new BlockDataCreator("end_oak_button",
            () -> woodenButton(BlockSetType.OAK))
            .SetTextureType(ETextureType.BUTTON)
            .SetTexture(EBlockSide.ALL, "end_oak_planks")
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.BUTTON_1, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_DOOR = new BlockDataCreator("end_oak_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, OAK_PLANKS.defaultMaterialColor()).strength(3.0F).noOcclusion(), BlockSetType.OAK))
            .SetTextureType(ETextureType.DOOR)
            .SetTexture(EBlockSide.TOP, "end_oak_door_top")
            .SetTexture(EBlockSide.BOTTOM, "end_oak_door_bottom")
            .SetTexture(EBlockSide.ITEM, "end_oak_door")
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.DOOR_3, List.of(END_OAK_PLANKS::get))
            .Register();

    public static final RegistryObject<Block> END_OAK_TRAPDOOR = new BlockDataCreator("end_oak_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).noOcclusion().isValidSpawn((blockState,  blockGetter,  blockPos, type) -> never(blockState, blockGetter, blockPos)), BlockSetType.OAK))
            .SetTextureType(ETextureType.TRAPDOOR)
            .SetMineableWith(EMineableWith.AXE)
            .AddRecipe(EBlockCraftingRecipeType.TRAPDOOR_2, List.of(END_OAK_PLANKS::get))
            .Register();

    // endregion

    public static final RegistryObject<Block> RAW_TIN_BLOCK = new BlockDataCreator("raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(4.0F, 5.0F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_SHAPELESS_1, List.of(() -> getItem(EItemCategory.RawMaterial, EMaterialType.TIN)))
            .Register();

    public static final RegistryObject<Block> DRAGON_DEBRIS = new BlockDataCreator("dragon_debris",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(30.0F, 1200.0F), UniformInt.of(0, 2)))
            .SetTextureType(ETextureType.PILLAR_TOP)
            .SetTexture(EBlockSide.TOP, "dragon_debris_top")
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetRequireTier(ERequireTier.DIAMOND)
            .SetSpecialTag(ECustomTag.ORE)
            .Register();
    
    public static final RegistryObject<Block> TIN_ORE = new BlockDataCreator("tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetSpecialTag(ECustomTag.ORE)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_COMMON, () -> getItem(EItemCategory.Ingot, EMaterialType.TIN))
            .Register();

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = new BlockDataCreator("deepslate_tin_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetRequireTier(ERequireTier.IRON)
            .SetSpecialTag(ECustomTag.ORE)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_COMMON, () -> getItem(EItemCategory.Ingot, EMaterialType.TIN))
            .Register();

    public static final RegistryObject<Block> PINK_QUARTZ_ORE = new BlockDataCreator("pink_quartz_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetSpecialTag(ECustomTag.ORE)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_RARE, ModItems.PINK_QUARTZ::get)
            .Register();

    public static final RegistryObject<Block> SMOOTH_PINK_QUARTZ_BLOCK = new BlockDataCreator("smooth_pink_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(0.8F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .Register();

    public static final RegistryObject<Block> PINK_QUARTZ_BLOCK = new BlockDataCreator("pink_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(0.8F)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_FROM_4_4, List.of(ModItems.PINK_QUARTZ::get), 1)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_BLOCK, ModBlocks.SMOOTH_PINK_QUARTZ_BLOCK::get)
            .Register();

    public static final RegistryObject<Block> PINK_QUARTZ_PILLAR = new BlockDataCreator("pink_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(0.8F)))
            .SetTextureType(ETextureType.LOG)
            .SetTexture(EBlockSide.TOP, "pink_quartz_pillar_top")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_FROM_2_1, List.of(PINK_QUARTZ_BLOCK::get), 2)
            .Register();

    public static final RegistryObject<Block> PINK_QUARTZ_BRICKS = new BlockDataCreator("pink_quartz_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(QUARTZ_BLOCK)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_FROM_4_4, List.of(PINK_QUARTZ_BLOCK::get))
            .Register();

    public static final RegistryObject<Block> PINK_QUARTZ_SLAB = new BlockDataCreator("pink_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(QUARTZ_BLOCK)))
            .SetTextureType(ETextureType.SLAB)
            .SetTexture(EBlockSide.ALL, "pink_quartz_block")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.SLAB_6, List.of(PINK_QUARTZ_BLOCK::get))
            .Register();

    public static final RegistryObject<Block> CHISELED_PINK_QUARTZ_BLOCK = new BlockDataCreator("chiseled_pink_quartz_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(0.8F)))
            .SetTextureType(ETextureType.PILLAR_TOP)
            .SetTexture(EBlockSide.TOP, "chiseled_pink_quartz_block_top")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.BLOCK_FROM_2_1, List.of(PINK_QUARTZ_SLAB::get))
            .Register();
    

    public static final RegistryObject<Block> PINK_QUARTZ_STAIRS = new BlockDataCreator("pink_quartz_stairs",
            () -> new StairBlock(QUARTZ_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(QUARTZ_BLOCK)))
            .SetTextureType(ETextureType.STAIRS)
            .SetTexture(EBlockSide.ALL, "pink_quartz_block")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.STAIRS_4, List.of(PINK_QUARTZ_BLOCK::get))
            .Register();

    public static final RegistryObject<Block> SMOOTH_PINK_QUARTZ_SLAB = new BlockDataCreator("smooth_pink_quartz_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(QUARTZ_BLOCK)))
            .SetTextureType(ETextureType.SLAB)
            .SetTexture(EBlockSide.ALL, "smooth_pink_quartz_block")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.SLAB_6, List.of(SMOOTH_PINK_QUARTZ_BLOCK::get))
            .Register();

    public static final RegistryObject<Block> SMOOTH_PINK_QUARTZ_STAIRS = new BlockDataCreator("smooth_pink_quartz_stairs",
            () -> new StairBlock(QUARTZ_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(QUARTZ_BLOCK)))
            .SetTextureType(ETextureType.STAIRS)
            .SetTexture(EBlockSide.ALL, "smooth_pink_quartz_block")
            .SetMineableWith(EMineableWith.PICKAXE)
            .AddRecipe(EBlockCraftingRecipeType.STAIRS_4, List.of(SMOOTH_PINK_QUARTZ_BLOCK::get))
            .Register();
    
    public static final RegistryObject<Block> STANNIN_ORE = new BlockDataCreator("stannin_ore",
            () -> new StanninOreBlock(UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetSpecialTag(ECustomTag.ORE)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_RARE, () -> getItem(EItemCategory.Ingot, EMaterialType.TIN))
            .Register();

    public static final RegistryObject<Block> DEEPSLATE_STANNIN_ORE = new BlockDataCreator("deepslate_stannin_ore",
            () -> new StanninOreBlock(UniformInt.of(0, 2)))
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetRequireTier(ERequireTier.IRON)
            .SetSpecialTag(ECustomTag.ORE)
            .AddSmeltingRecipe(ESmeltingRecipeType.SMELT_ORE_RARE, () -> getItem(EItemCategory.Ingot, EMaterialType.TIN))
            .Register();

    public static final RegistryObject<Block> BETTER_BLAST_FURNACE = new BlockDataCreator("blast_furnace",
            () -> new BetterBlastFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).lightLevel(litBlockEmission(13))))
            .SetMineableWith(EMineableWith.PICKAXE)
            .RegisterVanilla();

    public static final RegistryObject<Block> BRAKE_RAIL = new BlockDataCreator("brake_rail",
            () -> new BrakeRail())
            .SetMineableWith(EMineableWith.PICKAXE)
            .SetTextureType(ETextureType.CUSTOM)
            .SetSpecialTag(ECustomTag.RAIL)
            .AddRecipe(EBlockCraftingRecipeType.RAIL_16, List.of(() -> Items.IRON_INGOT))
            .Register();
    
    public static final RegistryObject<Block> CHARGED_SOUL_SAND = new BlockDataCreator("charged_soul_sand",
            () -> new ChargedSoulSandBlock(BlockBehaviour.Properties.copy(SOUL_SAND)))
            .SetCustomDrop(() -> SOUL_SAND)
            .SetMineableWith(EMineableWith.SHOVEL)
            .SetTextureType(ETextureType.CUBE_ALL_ONLY_ITEM)
            .SetSpecialTag(ECustomTag.SOUL_FIRE_BASE_BLOCK)
            .Register(); 

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (p_50763_) -> p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        VANILLA_BLOCKS.register(eventBus);
    }
}
