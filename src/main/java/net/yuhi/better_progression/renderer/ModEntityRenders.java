package net.yuhi.better_progression.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.entity.ModEntityTypes;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenders {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.THROWN_WEAPON.get(), ThrownWeaponRenderer::new);
    }
}
 