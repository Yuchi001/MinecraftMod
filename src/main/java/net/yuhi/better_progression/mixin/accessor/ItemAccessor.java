package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(Item.class)
public interface ItemAccessor {
    @Accessor("BASE_ATTACK_DAMAGE_UUID")
    static UUID BASE_ATTACK_DAMAGE_UUID() {
        return UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    }

    @Accessor("BASE_ATTACK_SPEED_UUID")
    static UUID BASE_ATTACH_SPEED_UUID() {
        return UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    }
}
