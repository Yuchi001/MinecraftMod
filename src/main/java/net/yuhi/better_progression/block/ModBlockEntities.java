package net.yuhi.better_progression.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.entity.BetterBlastFurnaceBlockEntity;
import net.yuhi.better_progression.block.entity.ChargedSoulSandBlockEntity;
import net.yuhi.better_progression.block.entity.EssenceSpawnerBlockEntity;
import net.yuhi.better_progression.block.entity.ModSignBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterProgression.MOD_ID);
    
    public static final DeferredRegister<BlockEntityType<?>> VANILLA_BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "minecraft");

    public static final RegistryObject<BlockEntityType<BetterBlastFurnaceBlockEntity>> BETTER_BLAST_FURNACE =
            VANILLA_BLOCK_ENTITIES.register("blast_furnace", () ->
                    BlockEntityType.Builder.of(BetterBlastFurnaceBlockEntity::new,
                            ModBlocks.BETTER_BLAST_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<EssenceSpawnerBlockEntity>> ESSENCE_SPAWNER =
            BLOCK_ENTITIES.register("essence_spawner", () ->
                    BlockEntityType.Builder.of(EssenceSpawnerBlockEntity::new,
                            ModBlocks.ESSENCE_SPAWNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register("mod_sign", () ->
                    BlockEntityType.Builder.of(ModSignBlockEntity::new,
                            ModBlocks.END_OAK_WALL_SIGN.get(),
                            ModBlocks.END_OAK_SIGN.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChargedSoulSandBlockEntity>> CHARGED_SOUL_SAND_BLOCK_ENTITY = BLOCK_ENTITIES.register("charged_soul_sand_block_entity",
            () -> BlockEntityType.Builder.of(ChargedSoulSandBlockEntity::new, ModBlocks.CHARGED_SOUL_SAND.get()).build(null));
    
    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        VANILLA_BLOCK_ENTITIES.register(bus);
    }
}
