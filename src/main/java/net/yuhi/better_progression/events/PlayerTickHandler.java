package net.yuhi.better_progression.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
    private static final String HUNGER_TICK_COUNTER = "hungerTickCounter";

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            
            if(player.isCreative() || player.isSpectator()) return;

            CompoundTag playerData = player.getPersistentData();

            int tickCounter = playerData.getInt(HUNGER_TICK_COUNTER);
            if (player.isSprinting()) {
                tickCounter++;

                if (tickCounter >= 20) {
                    FoodData foodData = player.getFoodData();

                    if (foodData.getFoodLevel() > 0) {
                        foodData.setFoodLevel(foodData.getFoodLevel() - 1);
                    }

                    tickCounter = 0;
                }

                playerData.putInt(HUNGER_TICK_COUNTER, tickCounter);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        if (player instanceof ServerPlayer) {
            player.setHealth(3.0F);

            FoodData foodData = player.getFoodData();
            foodData.setFoodLevel(10);

            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2));
        }
    }
}