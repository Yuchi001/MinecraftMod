package net.yuhi.better_progression.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.custom.TinedItem;
import net.yuhi.better_progression.item.custom.TwoHandedItem;

import java.util.Arrays;

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
    
    private static boolean hasHeavyWeapon = false;

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (event.getHand() == InteractionHand.OFF_HAND) {
            if (hasHeavyWeapon) event.setCanceled(true);
            return;
        }
        hasHeavyWeapon = event.getItemStack().getItem() instanceof TwoHandedItem;
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack heldItem = entity.getMainHandItem();

        if (heldItem.getItem() instanceof TinedItem) {
            TinedItem.addTinCount(heldItem, -1);
            if (TinedItem.getTinCount(heldItem) <= 0) return;
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent event) {
        if(!(event.getEntity().getMainHandItem().getItem() instanceof  TwoHandedItem)) return;
        event.setCanceled(true);
    }
}