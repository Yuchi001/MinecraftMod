package net.yuhi.better_progression.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.yuhi.better_progression.item.interfaces.ReachItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin implements IForgePlayer {
    @Override
    public double getEntityReach() {
        var player = (Player)(Object)this;

        var item = player.getMainHandItem().getItem();
        var reachItem = item instanceof ReachItem ? (ReachItem) item : null;
        
        double range = player.getAttributeValue(ForgeMod.ENTITY_REACH.get());
        range += reachItem != null ? reachItem.getReach(null) : 0;
        return range == 0 ? 0 : range + (player.isCreative() ? 3 : 0);
    }
}
