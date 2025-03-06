package net.yuhi.better_progression.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import net.yuhi.better_progression.item.interfaces.ReachItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    /*
    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void onReadData(CompoundTag pCompound, CallbackInfo ci) {
        int additionalHealth = 0;
        if ((Object) this instanceof Player player) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            for (var s : player.getArmorSlots()) {
                if (s.isEmpty()) continue;
                if (!(s.getItem() instanceof ArmorItem armorItem)) continue;
                if (!(armorItem.getMaterial() instanceof BetterArmorMaterial material)) continue;
                additionalHealth += material.getLifeMod(armorItem.getType());
            }

            maxHealth.setBaseValue((float)(6 + additionalHealth));
            if (player.getHealth() <= player.getMaxHealth()) return;
            player.setHealth(player.getMaxHealth());
        }
    }*/
}
