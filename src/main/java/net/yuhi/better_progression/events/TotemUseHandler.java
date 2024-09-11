package net.yuhi.better_progression.events;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TotemUseHandler {

    @SubscribeEvent
    public static void onLivingUseTotem(LivingUseTotemEvent event) {
        var entity = event.getEntity();

        if (entity instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();
            ItemStack itemToAdd = new ItemStack(ModItems.BROKEN_TOTEM_OF_UNDYING.get());

            if (mainHandItem.getItem() == Items.TOTEM_OF_UNDYING) {
                player.setItemInHand(InteractionHand.MAIN_HAND, itemToAdd);
            }
            else if (offHandItem.getItem() == Items.TOTEM_OF_UNDYING) {
                player.setItemInHand(InteractionHand.OFF_HAND, itemToAdd);
            }
        }
    }
}