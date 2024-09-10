package net.yuhi.better_progression.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HungerBarRenderHandler {
    private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    private static final int MAX_HUNGER_ICONS = 8;

    @SubscribeEvent
    public static void onRenderHungerBar(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().toString().contains("food_level")) {
            Player player = (Player) Minecraft.getInstance().getCameraEntity();
            
            if (player != null && !player.isCreative() && !player.isSpectator()) {
                event.setCanceled(true);
                
                int hungerLevel = player.getFoodData().getFoodLevel();
                renderCustomHungerBar(event.getPoseStack(), hungerLevel);
            }
        }
    }
    

    private static void renderCustomHungerBar(PoseStack poseStack, int hungerLevel) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        Gui gui = mc.gui;

        int x = screenWidth / 2 + 82;
        int y = screenHeight - 39;

        RenderSystem.setShaderTexture(0, ICONS);

        for (int i = 0; i < MAX_HUNGER_ICONS; i++) {
            gui.blit(poseStack, x - i * 8, y, 16, 27, 9, 9);
            if (hungerLevel > i * 2 + 1) {
                gui.blit(poseStack, x - i * 8, y, 52, 27, 9, 9);
            } else if (hungerLevel == i * 2 + 1) {
                gui.blit(poseStack, x - i * 8, y, 61, 27, 9, 9);
            }
        }
    }
}