package net.yuhi.better_progression.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.BetterBlastFurnace.BetterBlastFurnaceBlock;
import net.yuhi.better_progression.block.custom.BrakeRail;
import net.yuhi.better_progression.block.custom.StanninOreBlock;
import net.yuhi.better_progression.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = 
            DeferredRegister.create(ForgeRegistries.BLOCKS, BetterProgression.MOD_ID);
    
    public static final DeferredRegister<Block> VANILLA_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    
    public static final List<BlockRegistryPair<Block>> BLOCKS_DATA = new ArrayList<>();
    
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", 
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(4.0F, 6.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6.0F, 6.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).strength(6.0F, 6.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> ENDERITE_BLOCK = registerBlock("enderite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(50.0F, 1200.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            () -> new Block(
                    BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)
                            .strength(4.0F, 5.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> DRAGON_DEBRIS = registerBlock("dragon_debris",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(30.0F, 1200.0F),
                    UniformInt.of(0, 2)),
            EBlockType.Debris);
    
    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F), 
                    UniformInt.of(0, 2)),
                    EBlockType.Simple);

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F),
                    UniformInt.of(0, 2)),
            EBlockType.Simple);

    public static final RegistryObject<Block> END_TIN_ORE = registerBlock("end_tin_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F),
                    UniformInt.of(0, 2)),
            EBlockType.Simple);

    public static final RegistryObject<Block> PINK_QUARTZ_ORE = registerBlock("pink_quartz_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F),
                    UniformInt.of(0, 2)),
            EBlockType.Simple);
    
    public static final RegistryObject<Block> STANNIN_ORE = registerBlock("stannin_ore",
            () -> new StanninOreBlock(UniformInt.of(0, 2)), EBlockType.Simple);

    public static final RegistryObject<Block> DEEPSLATE_STANNIN_ORE = registerBlock("deepslate_stannin_ore",
            () -> new StanninOreBlock(UniformInt.of(0, 2)), EBlockType.Simple);

    public static final RegistryObject<Block> BETTER_BLAST_FURNACE = registerVanillaBlock("blast_furnace",
            () -> new BetterBlastFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).lightLevel(litBlockEmission(13))),
            EBlockType.Vanilla);

    public static final RegistryObject<Block> BRAKE_RAIL = registerBlock("brake_rail",
            BrakeRail::new,
            EBlockType.Rail);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, EBlockType blockType) {
        RegistryObject<T> blockObj = BLOCKS.register(name, block);
        registerBlockItem(name, blockObj);
        BLOCKS_DATA.add(new BlockRegistryPair(blockObj, blockType));
        return blockObj;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (p_50763_) -> p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
    }

    private static <T extends Block> RegistryObject<T> registerVanillaBlock(String name, Supplier<T> block, EBlockType blockType) {
        RegistryObject<T> blockObj = VANILLA_BLOCKS.register(name, block);
        registerBlockItem(name, blockObj);
        return blockObj;
    }
    
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        VANILLA_BLOCKS.register(eventBus);
    }

    public static class BlockRegistryPair<T> {
        public final RegistryObject<T> block;
        public final EBlockType blockType;

        public BlockRegistryPair(RegistryObject<T> block, EBlockType blockType) {
            this.block = block;
            this.blockType = blockType;
        }
    }
    
    public enum EBlockType {
        Simple,
        Debris,
        Vanilla,
        Rail,
        Custom
    }
}
