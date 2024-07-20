package net.yuhi.better_progression.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.BetterFurnace.BetterFurnaceBlockEntity;
import net.yuhi.better_progression.block.BetterBlastFurnace.BetterBlastFurnaceBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterProgression.MOD_ID);
    
    public static final DeferredRegister<BlockEntityType<?>> VANILLA_BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "minecraft");
    
    public static final RegistryObject<BlockEntityType<BetterFurnaceBlockEntity>> BETTER_FURNACE =
            VANILLA_BLOCK_ENTITIES.register("furnace", () ->
                    BlockEntityType.Builder.of(BetterFurnaceBlockEntity::new,
                            ModBlocks.BETTER_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<BetterBlastFurnaceBlockEntity>> BETTER_BLAST_FURNACE =
            VANILLA_BLOCK_ENTITIES.register("blast_furnace", () ->
                    BlockEntityType.Builder.of(BetterBlastFurnaceBlockEntity::new,
                            ModBlocks.BETTER_BLAST_FURNACE.get()).build(null));
    
    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        VANILLA_BLOCK_ENTITIES.register(bus);
    }
    
    /*public static final class BlockEntityTuple<T extends BlockEntityType> {
        public final RegistryObject<T> blockEntity;
        public final EBlockEntityType blockEntityType;

        public BlockEntityTuple(RegistryObject<T> blockEntity, EBlockEntityType blockEntityType) {
            this.blockEntity = blockEntity;
            this.blockEntityType = blockEntityType;
        }
    }
    
    public enum EBlockEntityType {
        Simple
    }*/
}
