package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPoolSingletonContainer.class)
public interface LootPoolSingletonContainerAccessor {
    
    @Final
    @Accessor("functions")
    LootItemFunction[] getFunctions();
    
    @Mutable
    @Accessor("functions")
    void setFunctions(LootItemFunction[] functions);
}
