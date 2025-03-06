package net.yuhi.better_progression.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventsHandler {
    private static final String HUNGER_TICK_COUNTER = "hungerTickCounter";

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.player instanceof ServerPlayer player)) return;

        if(event.phase != TickEvent.Phase.END || 
                player.isCreative() || 
                player.isSpectator()) return;

        CompoundTag playerData = player.getPersistentData();
        
        manageHunger(playerData, player);
        initHealth(playerData, player);
    }
    
    private static void manageHunger(CompoundTag playerData, Player player) {
        int tickCounter = playerData.getInt(HUNGER_TICK_COUNTER);
        if (!player.isSprinting()) return;

        tickCounter++;
        if (tickCounter >= 20) {
            FoodData foodData = player.getFoodData();

            if (foodData.getSaturationLevel() > 0) {
                foodData.setSaturation(foodData.getSaturationLevel() - 5);
            } else if (foodData.getFoodLevel() > 0) {
                foodData.setFoodLevel(foodData.getFoodLevel() - 1);
            }

            tickCounter = 0;
        }

        playerData.putInt(HUNGER_TICK_COUNTER, tickCounter);
    }
    
    private static void initHealth(CompoundTag playerData, ServerPlayer player){
        if (playerData.getBoolean("INIT")) return;
        var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) return;

        playerData.putBoolean("INIT", true);
        if (playerData.contains("HEALTH")) player.setHealth(playerData.getInt("HEALTH"));
        else maxHealth.setBaseValue(6);
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

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getPersistentData();
            data.putBoolean("INIT", false);

            var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth == null) return;
            
            if (!data.contains("HEALTH")) {
                player.setHealth(6);
                maxHealth.setBaseValue(6);
            }
        }
    }
}
