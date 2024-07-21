package net.yuhi.better_progression.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
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
            () -> new Block(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                            .strength(4.0F, 5.0F)),
            EBlockType.Simple);

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            () -> new Block(
                    BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)
                            .strength(4.0F, 5.0F)),
            EBlockType.Simple);
    
    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F), 
                    UniformInt.of(0, 2)),
                    EBlockType.Simple);

    public static final RegistryObject<Block> BETTER_BLAST_FURNACE = registerVanillaBlock("blast_furnace",
            () -> new BetterBlastFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).lightLevel(litBlockEmission(13))),
            EBlockType.Simple);

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
        Simple
    }
}
