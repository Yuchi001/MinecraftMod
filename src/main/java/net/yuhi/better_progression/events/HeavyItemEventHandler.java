package net.yuhi.better_progression.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.TwoHandedItem;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HeavyItemEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof TwoHandedItem) {
            player.getAbilities().mayBuild = player.getOffhandItem().isEmpty();
        } else {
            player.getAbilities().mayBuild = true;
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (event.getItemStack().getItem() instanceof TwoHandedItem) {
            System.out.println("dupa1");

            if (event.getHand() == InteractionHand.OFF_HAND) {
                event.setCanceled(true);
                System.out.println("dupa2");

            }
        }
    }
}