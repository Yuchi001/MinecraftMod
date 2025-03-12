package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Tiers.class)
public interface TiersAccessor {
    
    @Final
    @Mutable
    @Accessor("speed") 
    void setSpeed(float speed);

    @Final
    @Mutable
    @Accessor("uses")
    void setUses(int uses);
}
