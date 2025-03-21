package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingStandBlockEntity.class)
public interface BrewingStandBlockEntityAccessor {
    
    @Final
    @Accessor("items")
    public NonNullList<ItemStack> getItems();
    
    @Mutable
    @Accessor("lastPotionCount")
    void setLastPotionCount(boolean[] lastPotionCount);

    @Mutable
    @Accessor("items")
    public void setItems(NonNullList<ItemStack> items);
}
