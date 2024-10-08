package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.world.level.BaseSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseSpawner.class)
public interface BaseSpawnerAccessor {
    
    @Mutable
    @Accessor("oSpin")
    void setOSpin(double oSpin);
    
    @Accessor("spin")
    double getSpin();
    
    @Mutable
    @Accessor("minSpawnDelay")
    void setMinSpawnDelay(int minSpawnDelay);

    @Mutable
    @Accessor("maxSpawnDelay")
    void setMaxSpawnDelay(int maxSpawnDelay);
}
