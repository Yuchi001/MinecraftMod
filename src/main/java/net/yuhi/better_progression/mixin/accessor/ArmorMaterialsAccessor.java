package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsAccessor {
    
    @Final
    @Mutable
    @Accessor("knockbackResistance")
    void setKnockbackResistance(float knockbackResistance);
    //void setRepairIngredient(java.util.function.Supplier<Ingredient> ingredient);

    @Final
    @Mutable
    @Accessor("toughness")
    void setToughness(float toughness);
    
    @Final 
    @Mutable
    @Accessor("repairIngredient")
    void setRepairIngredient(LazyLoadedValue<Ingredient> repairIngredient);
}
